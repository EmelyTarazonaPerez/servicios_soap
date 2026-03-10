package com.webservice.soap.agent.documentation;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.XsdSchema;
import org.springframework.xml.xsd.XsdSchemaCollection;
import jakarta.annotation.PostConstruct;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.util.*;
import java.util.stream.Collectors;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class SoapAgentDocumentation {

    @Autowired
    private ResourceLoader resourceLoader;

    private final List<ServiceDefinition> services = new ArrayList<>();

    @PostConstruct
    public void init() {
        registerService("mensaje", "http://www.ejemplo.com/mensaje", 
            "Servicio de mensajes", "GetMensajeRequest", "GetMensajeResponse");
        
        registerService("saludo", "http://www.ejemplo.com/saludo", 
            "Servicio de saludos", "GetSaludoRequest", "GetSaludoResponse");
        
        registerService("pagosFondos", "http://www.ejemplo.com/fondos", 
            "Servicio de transferencia de fondos", "RequestPay", "ResponsePay");
    }

    public void registerService(String name, String namespace, String description, 
                                 String requestElement, String responseElement) {
        ServiceDefinition service = new ServiceDefinition();
        service.setName(name);
        service.setNamespace(namespace);
        service.setDescription(description);
        service.setRequestElement(requestElement);
        service.setResponseElement(responseElement);
        service.setWsdlUrl("/ws/" + name + ".wsdl");
        service.setSchemaUrl("/ws/" + name + ".xsd");
        
        List<Operation> operations = new ArrayList<>();
        Operation op = new Operation();
        op.setName(requestElement.replace("Request", ""));
        op.setRequestElement(requestElement);
        op.setResponseElement(responseElement);
        operations.add(op);
        service.setOperations(operations);
        
        services.add(service);
    }

    public List<ServiceDefinition> getAllServices() {
        return new ArrayList<>(services);
    }

    public ServiceDefinition getServiceByName(String name) {
        return services.stream()
            .filter(s -> s.getName().equals(name))
            .findFirst()
            .orElse(null);
    }

    public String generateHtmlDocumentation() {
        StringBuilder html = new StringBuilder();
        html.append("""
            <!DOCTYPE html>
            <html>
            <head>
                <title>SOAP Services Documentation</title>
                <style>
                    body { font-family: Arial, sans-serif; margin: 40px; background: #f5f5f5; }
                    h1 { color: #333; }
                    .service { background: white; padding: 20px; margin: 20px 0; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
                    .service h2 { color: #0066cc; margin-top: 0; }
                    .endpoint { background: #f9f9f9; padding: 10px; margin: 10px 0; border-left: 3px solid #0066cc; }
                    .badge { display: inline-block; padding: 3px 8px; border-radius: 3px; font-size: 12px; margin-right: 5px; }
                    .badge-req { background: #e3f2fd; color: #1565c0; }
                    .badge-res { background: #e8f5e9; color: #2e7d32; }
                    code { background: #f5f5f5; padding: 2px 5px; border-radius: 3px; }
                    .nav { margin-bottom: 20px; }
                    .nav a { margin-right: 15px; color: #0066cc; }
                </style>
            </head>
            <body>
                <h1>SOAP Web Services Documentation</h1>
                <div class="nav">
                    <a href="#overview">Overview</a>
                    <a href="#endpoints">Endpoints</a>
                    <a href="#security">Security</a>
                </div>
                
                <h2 id="overview">Service Overview</h2>
                <p>Total Services: %d</p>
                <p>Base URL: <code>/ws</code></p>
                
                <h2 id="endpoints">Available Services</h2>
            """.formatted(services.size()));

        for (ServiceDefinition service : services) {
            html.append("""
                <div class="service">
                    <h2>%s</h2>
                    <p>%s</p>
                    <p><strong>Namespace:</strong> <code>%s</code></p>
                    <p><strong>WSDL:</strong> <a href="%s">%s</a></p>
                    
                    <h3>Operations:</h3>
                """.formatted(
                    service.getName(),
                    service.getDescription(),
                    service.getNamespace(),
                    service.getWsdlUrl(),
                    service.getWsdlUrl()
                ));

            for (Operation op : service.getOperations()) {
                html.append("""
                    <div class="endpoint">
                        <strong>%s</strong><br>
                        <span class="badge badge-req">Request:</span> <code>%s</code><br>
                        <span class="badge badge-res">Response:</span> <code>%s</code>
                    </div>
                """.formatted(op.getName(), op.getRequestElement(), op.getResponseElement()));
            }

            html.append("</div>");
        }

        html.append("""
            <h2 id="security">Security</h2>
            <ul>
                <li>WS-Security X.509 Authentication supported</li>
                <li>Username Token Authentication</li>
                <li>SSL/TLS encryption</li>
                <li>Request/Response signing and encryption</li>
            </ul>
            
            <h2>Example Request</h2>
            <pre><code>&lt;soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                              xmlns:msg="http://www.ejemplo.com/mensaje"&gt;
               &lt;soapenv:Header/&gt;
               &lt;soapenv:Body&gt;
                  &lt;msg:GetMensajeRequest&gt;
                     &lt;msg:nombre&gt;Juan&lt;/msg:nombre&gt;
                  &lt;/msg:GetMensajeRequest&gt;
               &lt;/soapenv:Body&gt;
            &lt;/soapenv:Envelope&gt;</code></pre>
            
            </body>
            </html>
            """);

        return html.toString();
    }

    @Data
    public static class ServiceDefinition {
        private String name;
        private String namespace;
        private String description;
        private String wsdlUrl;
        private String schemaUrl;
        private String requestElement;
        private String responseElement;
        private List<Operation> operations = new ArrayList<>();
    }

    @Data
    public static class Operation {
        private String name;
        private String requestElement;
        private String responseElement;
        private String description;
    }
}
