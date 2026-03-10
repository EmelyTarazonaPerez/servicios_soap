package com.webservice.soap.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="banco")
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int idCuenta;
    @Column(name="cuenta_bancaria")
    String cuenta;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    private InfoCliente cliente;
    @ManyToOne
    @JoinColumn(name = "id_tipo_cuenta", referencedColumnName = "id_tipo_cuenta")
    TipoCuenta tipoCuenta;
    Float cupo;
    Boolean activa;
    @Column(name="fecha_apertura")
    Date fechaApertura;
}
