package com.webservice.soap.repository.service.impl;

import com.webservice.soap.model.InfoCliente;
import com.webservice.soap.repository.service.RepositoryBd;
import com.webservice.soap.repository.springJPA.RepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class RepositoryBdImpl  implements RepositoryBd {
    @Autowired
    public RepositoryJPA repositoryJPA;

    @Override
    public InfoCliente buscarDatosCliente(String tipoDocumento, String numeroDocumento) {
        Optional<InfoCliente> result = repositoryJPA.findByTipoDocumentoAndNumeroDocumento(tipoDocumento, numeroDocumento);
        return result.get();
    }

    @Override
    public InfoCliente buscarCuentaBancariaTercero(String destinationaccount) {
        return null;
    }
}
