package com.webservice.soap.agent.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import jakarta.xml.soap.SOAPHeader;
import jakarta.xml.soap.SOAPElement;
import java.util.regex.Pattern;

@Slf4j
public class SoapSecurityInterceptor implements EndpointInterceptor {

    private static final String USERNAME_TOKEN = "UsernameToken";
    private static final Pattern BASIC_AUTH_PATTERN = Pattern.compile("Basic .*");
    
    private String validUsername = "admin";
    private String validPassword = "admin123";
    private boolean enabled = true;
    
    @Override
    public boolean handleRequest(MessageContext messageContext, Object endpoint) throws Exception {
        if (!enabled) {
            return true;
        }

        if (!(messageContext.getRequest() instanceof SaajSoapMessage saajSoapMessage)) {
            return true;
        }

        String transactionId = (String) messageContext.getProperty("transactionId");
        
        try {
            SOAPHeader header = saajSoapMessage.getSaajMessage().getSOAPHeader();
            if (header != null) {
                var iterator = header.getChildElements();

                while (iterator.hasNext()) {
                    Object element = iterator.next();
                    if (element instanceof SOAPElement soapElement) {
                        String name = soapElement.getLocalName();

                        if ("Security".equals(name) && !validateSecurityHeader(soapElement)) {
                            log.warn("[SECURITY] TransactionId: {} - Invalid security header", transactionId);
                            throw new RuntimeException("Invalid security header");
                        }

                        if ("UsernameToken".equals(name) && !validateUsernameToken(soapElement)) {
                            log.warn("[SECURITY] TransactionId: {} - Invalid username token", transactionId);
                            throw new RuntimeException("Invalid credentials");
                        }
                    }
                }
            }
            
            log.debug("[SECURITY] TransactionId: {} - Request validated successfully", transactionId);
            
        } catch (Exception e) {
            log.error("[SECURITY] TransactionId: {} - Security validation error: {}", transactionId, e.getMessage());
            throw e;
        }

        return true;
    }

    private boolean validateSecurityHeader(SOAPElement securityHeader) {
        return securityHeader != null;
    }

    private boolean validateUsernameToken(SOAPElement usernameToken) {
        try {
            String username = usernameToken.getAttribute("username");
            String password = usernameToken.getAttribute("password");

            if (username == null || password == null) {
                return false;
            }
            
            return validUsername.equals(username) && validPassword.equals(password);
            
        } catch (Exception e) {
            log.error("Error validating username token: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public boolean handleResponse(MessageContext messageContext, Object endpoint) {
        return true;
    }

    @Override
    public boolean handleFault(MessageContext messageContext, Object endpoint) {
        return true;
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Object endpoint, Exception ex) {}

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setCredentials(String username, String password) {
        this.validUsername = username;
        this.validPassword = password;
    }
}
