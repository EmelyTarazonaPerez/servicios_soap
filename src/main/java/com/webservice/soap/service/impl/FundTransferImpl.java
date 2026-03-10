package com.webservice.soap.service.impl;

import com.webservice.soap.generated.com.ejemplo.fondos.*;
import com.webservice.soap.repository.service.RepositoryBd;
import com.webservice.soap.service.PaymentOperations;
import com.webservice.soap.model.Cuenta;
import com.webservice.soap.model.InfoCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.webservice.soap.utils.DataTest.id;
import static com.webservice.soap.utils.DataTest.user;

@Service
public class FundTransferImpl implements PaymentOperations {

    @Autowired
    private RepositoryBd repositorioClients;

    @Override
    @Transactional
    public ResponsePay FundTransfer(RequestPay requestPay) {
        ResponsePay response = new ResponsePay();
       // Encontrar el usuario con numero de documento extraido del jwt
        String tipoDocumento = id;
        String numeroDocumento =  user;

        InfoCliente infoCliente = repositorioClients.buscarDatosCliente(tipoDocumento, numeroDocumento);
        InfoCliente infoDestinario = repositorioClients.buscarCuentaBancariaTercero(requestPay.getDestinationaccount());

        Optional<Cuenta> cuentaOrigen =  validarCuentaBancaria(infoCliente, requestPay.getSourceaccount());
        Optional<Cuenta> cuentaDestino = validarCuentaBancaria(infoDestinario, requestPay.getDestinationaccount());

        Float monto = requestPay.getMount();

        if (cuentaOrigen.get().getCupo() < monto) {
            throw new RuntimeException("Saldo insuficiente");
        }
        cuentaOrigen.get().setCupo(cuentaOrigen.get().getCupo() - monto);
        cuentaDestino.get().setCupo(cuentaDestino.get().getCupo() + monto);

        repositorioClients.save(cuentaOrigen.get());
        repositorioClients.save(cuentaDestino.get());
        response.setState("Transaccion finalizada con exito");
        return response;
    }

    public Optional<Cuenta> validarCuentaBancaria(InfoCliente infoCuentas, String numeroCuenta){
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
        return cuentaEncontrada;
    }

}
