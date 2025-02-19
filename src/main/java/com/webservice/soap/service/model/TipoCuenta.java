package com.webservice.soap.service.model;

public class TipoCuenta {
    String tipoCuenta;
    String intereses;
    String limite;

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public void setIntereses(String intereses) {
        this.intereses = intereses;
    }

    public void setLimite(String limite) {
        this.limite = limite;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public String getIntereses() {
        return intereses;
    }

    public String getLimite() {
        return limite;
    }
}
