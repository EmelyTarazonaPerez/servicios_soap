package com.webservice.soap.repository.springJPA;

import com.webservice.soap.model.Cuenta;
import com.webservice.soap.model.InfoCliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepositoryJPACuenta extends JpaRepository<Cuenta, Integer> {
    Optional<InfoCliente> findByCuenta(String cuenta);

}
