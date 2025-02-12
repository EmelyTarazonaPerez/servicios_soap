package com.webservice.soap.service;

import com.webservice.soap.generated.com.ejemplo.fondos.ResponsePay;

public interface PaymentOperations {

    default ResponsePay FundTransfer() {
        throw new UnsupportedOperationException("Operación no soportada");
    }

    default ResponsePay PaymentProcessing() {
        throw new UnsupportedOperationException("Operación no soportada");
    }

}

