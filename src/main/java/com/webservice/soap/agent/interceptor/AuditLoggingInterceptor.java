package com.webservice.soap.agent.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.WebServiceMessage;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class AuditLoggingInterceptor implements EndpointInterceptor {

    private final Map<String, AuditRecord> auditCache = new ConcurrentHashMap<>();
    private static final int MAX_CACHE_SIZE = 1000;

    @Override
    public boolean handleRequest(MessageContext messageContext, Object endpoint) {
        String transactionId = UUID.randomUUID().toString().substring(0, 8);
        messageContext.setProperty("transactionId", transactionId);
        
        AuditRecord record = new AuditRecord();
        record.setTransactionId(transactionId);
        record.setTimestamp(LocalDateTime.now());
         
        
        try {
            WebServiceMessage message = messageContext.getRequest();
            if (message instanceof SoapMessage soapMessage) {
                record.setSoapAction(soapMessage.getSoapAction());

                // Corrección: Obtener el tipo de contenido desde el mensaje subyacente
                String contentType = message.getClass().getSimpleName();
                record.setContentType(contentType);

                record.setPayload(getPayload(message));
            }
        } catch (Exception e) {
            log.error("Error extracting request details: {}", e.getMessage());
        }
        
        addToCache(transactionId, record);
        log.info("[AUDIT] TransactionId: {} - REQUEST received", transactionId);
        
        return true;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext, Object endpoint) {
        String transactionId = (String) messageContext.getProperty("transactionId");
        AuditRecord record = getFromCache(transactionId);
        
        if (record != null) {
            record.setPhase("RESPONSE");
            record.setResponseTime(LocalDateTime.now());
            
            try {
                WebServiceMessage message = messageContext.getResponse();
                if (message instanceof SoapMessage soapMessage) {
                    record.setResponsePayload(getPayload(message));
                    record.setStatus("SUCCESS");
                }
            } catch (Exception e) {
                log.error("Error extracting response details: {}", e.getMessage());
                record.setStatus("ERROR");
            }
            
            log.info("[AUDIT] TransactionId: {} - RESPONSE sent - Status: {}", 
                transactionId, record.getStatus());
        }
        
        return true;
    }

    @Override
    public boolean handleFault(MessageContext messageContext, Object endpoint) {
        String transactionId = (String) messageContext.getProperty("transactionId");
        log.error("[AUDIT] TransactionId: {} - FAULT occurred", transactionId);
        
        AuditRecord record = getFromCache(transactionId);
        if (record != null) {
            record.setPhase("FAULT");
            record.setStatus("FAULT");
            try {
                record.setErrorMessage(getPayload(messageContext.getResponse()));
            } catch (Exception e) {
                record.setErrorMessage("Error extracting fault details");
            }
        }
        
        return true;
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Object endpoint, Exception ex) {
        String transactionId = (String) messageContext.getProperty("transactionId");
        if (ex != null) {
            log.error("[AUDIT] TransactionId: {} - EXCEPTION: {}", transactionId, ex.getMessage());
            AuditRecord record = getFromCache(transactionId);
            if (record != null) {
                record.setStatus("EXCEPTION");
                record.setErrorMessage(ex.getMessage());
            }
        }
    }

    private String getPayload(WebServiceMessage message) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            message.writeTo(baos);
            String payload = baos.toString();
            return payload.length() > 1000 ? payload.substring(0, 1000) + "..." : payload;
        } catch (Exception e) {
            return "Unable to extract payload";
        }
    }

    private void addToCache(String key, AuditRecord record) {
        if (auditCache.size() >= MAX_CACHE_SIZE) {
            auditCache.clear();
        }
        auditCache.put(key, record);
    }

    private AuditRecord getFromCache(String key) {
        return auditCache.get(key);
    }

    public Map<String, AuditRecord> getAuditHistory() {
        return new HashMap<>(auditCache);
    }

    public static class AuditRecord {
        private String transactionId;
        private LocalDateTime timestamp;
        private LocalDateTime responseTime;
        private String phase;
        private String soapAction;
        private String contentType;
        private String payload;
        private String responsePayload;
        private String status;
        private String errorMessage;

        public String getTransactionId() { return transactionId; }
        public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
        public LocalDateTime getTimestamp() { return timestamp; }
        public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
        public LocalDateTime getResponseTime() { return responseTime; }
        public void setResponseTime(LocalDateTime responseTime) { this.responseTime = responseTime; }
        public String getPhase() { return phase; }
        public void setPhase(String phase) { this.phase = phase; }
        public String getSoapAction() { return soapAction; }
        public void setSoapAction(String soapAction) { this.soapAction = soapAction; }
        public String getContentType() { return contentType; }
        public void setContentType(String contentType) { this.contentType = contentType; }
        public String getPayload() { return payload; }
        public void setPayload(String payload) { this.payload = payload; }
        public String getResponsePayload() { return responsePayload; }
        public void setResponsePayload(String responsePayload) { this.responsePayload = responsePayload; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public String getErrorMessage() { return errorMessage; }
        public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
    }
}
