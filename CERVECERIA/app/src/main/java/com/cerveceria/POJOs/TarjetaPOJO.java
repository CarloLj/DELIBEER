package com.cerveceria.POJOs;

public class TarjetaPOJO {
    private int iid, idcliente;
    private String nombretarjeta, numerotarjeta, fechavencimiento, codigoseguridad;

    public int getIid() {
        return iid;
    }

    public void setIid(int iid) {
        this.iid = iid;
    }

    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public String getNombretarjeta() {
        return nombretarjeta;
    }

    public void setNombretarjeta(String nombretarjeta) {
        this.nombretarjeta = nombretarjeta;
    }

    public String getNumerotarjeta() {
        return numerotarjeta;
    }

    public void setNumerotarjeta(String numerotarjeta) {
        this.numerotarjeta = numerotarjeta;
    }

    public String getFechavencimiento() {
        return fechavencimiento;
    }

    public void setFechavencimiento(String fechavencimiento) {
        this.fechavencimiento = fechavencimiento;
    }

    public String getCodigoseguridad() {
        return codigoseguridad;
    }

    public void setCodigoseguridad(String codigoseguridad) {
        this.codigoseguridad = codigoseguridad;
    }
}
