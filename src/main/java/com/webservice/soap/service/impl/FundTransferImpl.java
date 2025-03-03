package com.webservice.soap.service.impl;

import com.webservice.soap.generated.com.ejemplo.fondos.*;
import com.webservice.soap.repository.service.RepositoryBd;
import com.webservice.soap.service.PaymentOperations;
import com.webservice.soap.model.Cuenta;
import com.webservice.soap.model.InfoCliente;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Optional;

public class FundTransferImpl implements PaymentOperations {

    @Autowired
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
       // llamar a la base de datos dbCliente para conseguir datos bancarios ---
       // llamar a la bse de datos dbCliente y conseguir la cuenta bancaria a depositar retornar usuario gmail



        return null;
    }

    public void validarCuentaBancaria(InfoCliente infoCuentas, String numeroCuenta){
        // Buscar la cuenta bancaria
        Optional<Cuenta> cuentaEncontrada = infoCuentas.getCuentasBancarias().stream()
                .filter(cuenta -> cuenta.getCuenta().equals(numeroCuenta))
                .findFirst();

        // Validar si la cuenta existe
        if (cuentaEncontrada.isEmpty()) {
            throw new RuntimeException("La cuenta origen no existe");
        }
        // Validar si la cuenta está activa
        if (!cuentaEncontrada.get().getActiva()) {
            throw new RuntimeException("La cuenta no está activa");
        }
    }

}
