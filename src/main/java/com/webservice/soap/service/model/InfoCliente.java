package com.webservice.soap.service.model;

public class InfoCliente {
    String primerNombre;
    String segundoNombre;
    String primerApellido;
    String segundoApellido;
    Cuenta[] cuentasBancarias;
    String  pais;
    String ciudad;
    String telefono;

    public InfoCliente(String telefono, String ciudad, String pais, Cuenta[] cuentasBancarias, String segundoApellido, String primerApellido, String segundoNombre, String primerNombre) {
        this.telefono = telefono;
        this.ciudad = ciudad;
        this.pais = pais;
        this.cuentasBancarias = cuentasBancarias;
        this.segundoApellido = segundoApellido;
        this.primerApellido = primerApellido;
        this.segundoNombre = segundoNombre;
        this.primerNombre = primerNombre;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public Cuenta[] getCuentasBancarias() {
        return cuentasBancarias;
    }

    public void setCuentasBancarias(Cuenta[] cuentasBancarias) {
        this.cuentasBancarias = cuentasBancarias;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
