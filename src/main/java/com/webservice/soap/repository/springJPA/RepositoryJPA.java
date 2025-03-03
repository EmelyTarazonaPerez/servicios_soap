package com.webservice.soap.repository.springJPA;
import com.webservice.soap.model.InfoCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface  RepositoryJPA extends JpaRepository<InfoCliente, Integer> {

    Optional<InfoCliente> findByTipoDocumentoAndNumeroDocumento(String tipoDocumento, String numeroDocumento);
}
