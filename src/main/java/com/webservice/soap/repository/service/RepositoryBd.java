package com.webservice.soap.repository.service;

import com.webservice.soap.service.model.InfoCliente;

import java.math.BigDecimal;

public interface RepositoryBd {
    InfoCliente buscarDatosCliente(String tipoDocumento, String numeroDocumento);
    InfoCliente buscarCuentaBancariaTercero(String destinationaccount);
}
