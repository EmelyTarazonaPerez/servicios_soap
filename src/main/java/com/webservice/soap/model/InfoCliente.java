package com.webservice.soap.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name="cliente")
@AllArgsConstructor
@NoArgsConstructor
public class InfoCliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name="tipo_identificacion")
    String tipoDocumento;
    @Column(name="documento")
    String numeroDocumento;
    @Column(name="primer_nombre")
    String primerNombre;
    @Column(name="segundo_nombre")
    String segundoNombre;
    @Column(name="primer_apellido")
    String primerApellido;
    @Column(name="segundo_apellido")
    String segundoApellido;
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Cuenta> cuentasBancarias;
    String  pais;
    String ciudad;
    String telefono;
}
