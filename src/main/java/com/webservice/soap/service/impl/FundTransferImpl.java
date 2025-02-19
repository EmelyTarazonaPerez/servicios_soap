package com.webservice.soap.service.impl;

import com.webservice.soap.generated.com.ejemplo.fondos.RequestPay;
import com.webservice.soap.generated.com.ejemplo.fondos.ResponsePay;
import com.webservice.soap.repository.service.RepositoryBd;
import com.webservice.soap.service.PaymentOperations;
import com.webservice.soap.service.model.Cuenta;
import com.webservice.soap.service.model.InfoCliente;

import java.math.BigDecimal;
import java.util.Arrays;

public class FundTransferImpl implements PaymentOperations {

    private RepositoryBd repositorioClients;

    @Override
    public ResponsePay FundTransfer(RequestPay requestPay) {
        ResponsePay response = new ResponsePay();
       // Encontrar el usuario con numero de documento extraido del jwt
        String tipoDocumento =   "";
        String numeroDocumento = "";


        InfoCliente infoCliente = repositorioClients.buscarDatosCliente(tipoDocumento, numeroDocumento);
        InfoCliente infoDestinario = repositorioClients.buscarCuentaBancariaTercero(requestPay.getDestinationaccount());

        validarCuentaBancaria(infoCliente, requestPay.getSourceaccount());
        validarCuentaBancaria(infoDestinario, requestPay.getDestinationaccount());
        validarCuentaActiva(infoDestinario, requestPay.getDestinationaccount());


       // llamar a la base de datos dbCliente para conseguir datos bancarios ---
       // llamar a la bse de datos dbCliente y conseguir la cuenta bancaria a depositar retornar usuario gmail

        //logica


        return null;
    }

    public void validarCuentaBancaria(InfoCliente infoCuentas, String numerouenta){
        boolean cuentaOrigenExiste = Arrays.stream(infoCuentas.getCuentasBancarias())
                .anyMatch(x -> x.getCuenta().equals(numerouenta));

        if (!cuentaOrigenExiste) {
            throw new RuntimeException("La cuenta origen no existe");
        }
    }

    public void validarCuentaActiva(InfoCliente infoCuentas, String numerouenta){
        Cuenta cuentaOrigenExiste = (Cuenta) Arrays.stream(infoCuentas.getCuentasBancarias())
                .filter(x -> x.getCuenta().equals(numerouenta));

        if (!cuentaOrigenExiste.getActiva()){
            throw new RuntimeException("La cuenta a depositar no esta activa");
        }
    }

    public void

}
