package com.webservice.soap.agent.monitoring;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.EndpointInterceptor;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Slf4j
public class SoapMetricsInterceptor implements EndpointInterceptor {

    private final Map<String, ServiceMetrics> metricsMap = new ConcurrentHashMap<>();
    private final AtomicLong totalRequests = new AtomicLong(0);
    private final AtomicLong totalErrors = new AtomicLong(0);
    private final Map<String, Long> responseTimes = new ConcurrentHashMap<>();

    @Override
    public boolean handleRequest(MessageContext messageContext, Object endpoint) {
        String transactionId = (String) messageContext.getProperty("transactionId");
        long startTime = System.currentTimeMillis();
        responseTimes.put(transactionId, startTime);
        totalRequests.incrementAndGet();
        
        String serviceName = extractServiceName(messageContext);
        ServiceMetrics metrics = metricsMap.computeIfAbsent(serviceName, k -> new ServiceMetrics(serviceName));
        metrics.incrementRequestCount();
        
        log.debug("[METRICS] TransactionId: {} - Request started for service: {}", transactionId, serviceName);
        return true;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext, Object endpoint) {
        String transactionId = (String) messageContext.getProperty("transactionId");
        String serviceName = extractServiceName(messageContext);
        
        Long startTime = responseTimes.remove(transactionId);
        if (startTime != null) {
            long duration = System.currentTimeMillis() - startTime;
            
            ServiceMetrics metrics = metricsMap.get(serviceName);
            if (metrics != null) {
                metrics.addResponseTime(duration);
                metrics.incrementSuccessCount();
            }
            
            log.debug("[METRICS] TransactionId: {} - Response completed in {}ms", transactionId, duration);
        }
        
        return true;
    }

    @Override
    public boolean handleFault(MessageContext messageContext, Object endpoint) {
        String transactionId = (String) messageContext.getProperty("transactionId");
        String serviceName = extractServiceName(messageContext);
        
        totalErrors.incrementAndGet();
        ServiceMetrics metrics = metricsMap.get(serviceName);
        if (metrics != null) {
            metrics.incrementErrorCount();
        }
        
        log.warn("[METRICS] TransactionId: {} - Fault occurred for service: {}", transactionId, serviceName);
        return true;
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Object endpoint, Exception ex) {
        if (ex != null) {
            totalErrors.incrementAndGet();
            String serviceName = extractServiceName(messageContext);
            ServiceMetrics metrics = metricsMap.get(serviceName);
            if (metrics != null) {
                metrics.incrementErrorCount();
            }
        }
    }

    private String extractServiceName(MessageContext context) {
        try {
            Object endpoint = context.getProperty("javax.xml.ws.wsdl.service");
            if (endpoint != null) {
                return endpoint.toString();
            }
        } catch (Exception e) {
        }
        return "unknown";
    }

    public MetricsSummary getMetricsSummary() {
        MetricsSummary summary = new MetricsSummary();
        summary.setTotalRequests(totalRequests.get());
        summary.setTotalErrors(totalErrors.get());
        summary.setTotalSuccess(totalRequests.get() - totalErrors.get());
        
        List<ServiceMetrics> serviceMetrics = new ArrayList<>(metricsMap.values());
        summary.setServiceMetrics(serviceMetrics);
        
        double avgResponseTime = serviceMetrics.stream()
            .mapToDouble(ServiceMetrics::getAverageResponseTime) // Cambiado a mapToDouble para manejar double
            .average()
            .orElse(0.0);
        summary.setAverageResponseTime(avgResponseTime);
        
        return summary;
    }

    public Map<String, String> getPrometheusMetrics() {
        Map<String, String> prometheusMetrics = new HashMap<>();

        prometheusMetrics.put("soap_requests_total", String.valueOf(totalRequests.get()));
        prometheusMetrics.put("soap_errors_total", String.valueOf(totalErrors.get()));
        prometheusMetrics.put("soap_success_total", String.valueOf(totalRequests.get() - totalErrors.get()));

        metricsMap.forEach((service, metrics) -> {
            prometheusMetrics.put("soap_" + service + "_requests_total", String.valueOf(metrics.getRequestCount()));
            prometheusMetrics.put("soap_" + service + "_errors_total", String.valueOf(metrics.getErrorCount()));
            prometheusMetrics.put("soap_" + service + "_avg_response_time_ms", String.valueOf(metrics.getAverageResponseTime()));
        });
        
        return prometheusMetrics;
    }

    public void reset() {
        metricsMap.clear();
        totalRequests.set(0);
        totalErrors.set(0);
        responseTimes.clear();
    }

    @Data
    public static class ServiceMetrics {
        private final String serviceName;
        private long requestCount = 0;
        private long successCount = 0;
        private long errorCount = 0;
        private long totalResponseTime = 0;
        private long minResponseTime = Long.MAX_VALUE;
        private long maxResponseTime = 0;
        private final List<Long> responseTimes = new ArrayList<>();

        public ServiceMetrics(String serviceName) {
            this.serviceName = serviceName;
        }

        public void incrementRequestCount() {
            requestCount++;
        }

        public void incrementSuccessCount() {
            successCount++;
        }

        public void incrementErrorCount() {
            errorCount++;
        }

        public void addResponseTime(long time) {
            totalResponseTime += time;
            minResponseTime = Math.min(minResponseTime, time);
            maxResponseTime = Math.max(maxResponseTime, time);
            responseTimes.add(time);
            
            if (responseTimes.size() > 1000) {
                responseTimes.remove(0);
            }
        }

        public double getAverageResponseTime() {
            return requestCount > 0 ? (double) totalResponseTime / requestCount : 0;
        }
    }

    @Data
    public static class MetricsSummary {
        private long totalRequests;
        private long totalErrors;
        private long totalSuccess;
        private double averageResponseTime;
        private List<ServiceMetrics> serviceMetrics;
    }
}
