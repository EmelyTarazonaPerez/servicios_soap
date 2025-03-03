package com.webservice.soap.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor  // Agregar constructor sin argumentos
@AllArgsConstructor // Constructor con todos los campos
@Entity
@Table(name = "tipo_de_cuenta")
public class TipoCuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_tipo_cuenta")
    int id;
    @Column(name="tipo_cuenta")
    String tipoCuenta;
    String intereses;
    @Column(name="limite_de_cupos")
    String limite;
}
