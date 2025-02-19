package com.webservice.soap.endpoint;

import com.webservice.soap.generated.com.ejemplo.fondos.RequestPay;
import com.webservice.soap.generated.com.ejemplo.fondos.ResponsePay;
import com.webservice.soap.service.PaymentOperations;
import com.webservice.soap.service.impl.FundTransferImpl;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;

@Endpoint
public class coreRest {

    private PaymentOperations paymentOperations;
    private static final String NAMESPACE_URI = "http://www.ejemplo.com/fondos";

    @PayloadRoot(namespace = "NAMESPACE_URI", localPart = "RequestPay")
    public ResponsePay processPay (@RequestPayload RequestPay requestPay) {
        return paymentOperations.FundTransfer(requestPay);
    }
}
