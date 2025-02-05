package com.webservice.soap.controller;

import com.webservice.soap.generated.com.ejemplo.mensaje.GetMensajeRequest;
import com.webservice.soap.generated.com.ejemplo.mensaje.GetMensajeResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class MensajeEndpoint {

    private static final String NAMESPACE_URI = "http://www.ejemplo.com/mensaje";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetMensajeRequest")
    @ResponsePayload
    public GetMensajeResponse obtenerMensaje(@RequestPayload GetMensajeRequest request) {
        GetMensajeResponse response = new GetMensajeResponse();
        response.setMensaje("Hola, " + request.getNombre() + "! Bienvenido a SOAP con Spring Boot.");
        return response;
    }
}
