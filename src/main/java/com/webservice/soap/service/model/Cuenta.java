package com.webservice.soap.service.model;

import java.util.Date;

public class Cuenta {
    String cuenta;
    TipoCuenta tipoCuenta;
    Float cupo;
    Boolean activa;
    Date fechaApertura;

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(TipoCuenta tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public Float getCupo() {
        return cupo;
    }

    public void setCupo(Float cupo) {
        this.cupo = cupo;
    }

    public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }

    public Date getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(Date fechaApertura) {
        this.fechaApertura = fechaApertura;
    }
}
