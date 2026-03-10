package com.webservice.soap.endpoint;

import com.webservice.soap.generated.com.ejemplo.saludo.GetSaludoRequest;
import com.webservice.soap.generated.com.ejemplo.saludo.GetSaludoResponse;
import com.webservice.soap.service.impl.contruirMensajeSaludo;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class SaludoEndpoint {
    private static final String NAMESPACE_URI = "http://www.ejemplo.com/saludo";

    private contruirMensajeSaludo saludo;

    public SaludoEndpoint(contruirMensajeSaludo saludo) {
        this.saludo = saludo;
    }

    /**
     * <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
     *                   xmlns:sal="http://www.ejemplo.com/saludo">
     *    <soapenv:Header/>
     *    <soapenv:Body>
     *       <sal:getSaludoRequest>
     *          <sal:hora>5</sal:hora>
     *       </sal:getSaludoRequest>
     *    </soapenv:Body>
     * </soapenv:Envelope>
     * */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetSaludoRequest")
    @ResponsePayload
    public GetSaludoResponse obtenerSaludo(@RequestPayload GetSaludoRequest request) {
        GetSaludoResponse response = new GetSaludoResponse();
        response.setMensaje(saludo.obtenerSaludo(request.getHora()));
        return response;

    }
}
