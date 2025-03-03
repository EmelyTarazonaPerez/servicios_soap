package com.webservice.soap.repository.service;

import com.webservice.soap.model.InfoCliente;

public interface RepositoryBd {
    InfoCliente buscarDatosCliente(String tipoDocumento, String numeroDocumento);
    InfoCliente buscarCuentaBancariaTercero(String destinationaccount);
}
