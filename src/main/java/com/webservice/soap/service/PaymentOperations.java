package com.webservice.soap.service;

import com.webservice.soap.generated.com.ejemplo.fondos.RequestPay;
import com.webservice.soap.generated.com.ejemplo.fondos.ResponsePay;

public interface PaymentOperations {

    default ResponsePay FundTransfer(RequestPay requestPay) {
        throw new UnsupportedOperationException("Operación no soportada");
    }

    default ResponsePay PaymentProcessing() {
        throw new UnsupportedOperationException("Operación no soportada");
    }

}

