/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.entidad;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author luis
 */
public class Envio implements Serializable {

    private int id;
    private String fechaSalida;
    private String fechaLlegada;
    private double total;
    private String estado;
    private String codigoTienda;
    private String usuarioBodega;
    private ArrayList<DetalleEnvio> detalle;

    /**
     *
     * @param id
     * @param fechaSalida
     * @param fechaLlegada
     * @param total
     * @param estado
     * @param codigoTienda
     * @param usuarioBodega
     */
    public Envio(int id, String fechaSalida, String fechaLlegada, double total, String estado, String codigoTienda, String usuarioBodega) {
        this.id = id;
        this.fechaSalida = fechaSalida;
        this.fechaLlegada = fechaLlegada;
        this.total = total;
        this.estado = estado;
        this.codigoTienda = codigoTienda;
        this.usuarioBodega = usuarioBodega;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the fechaSalida
     */
    public String getFechaSalida() {
        return fechaSalida;
    }

    /**
     * @param fechaSalida the fechaSalida to set
     */
    public void setFechaSalida(String fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    /**
     * @return the fechaLlegada
     */
    public String getFechaLlegada() {
        return fechaLlegada;
    }

    /**
     * @param fechaLlegada the fechaLlegada to set
     */
    public void setFechaLlegada(String fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
    }

    /**
     * @return the total
     */
    public double getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the codigoTienda
     */
    public String getCodigoTienda() {
        return codigoTienda;
    }

    /**
     * @param codigoTienda the codigoTienda to set
     */
    public void setCodigoTienda(String codigoTienda) {
        this.codigoTienda = codigoTienda;
    }

    /**
     * @return the usuarioBodega
     */
    public String getUsuarioBodega() {
        return usuarioBodega;
    }

    /**
     * @param usuarioBodega the usuarioBodega to set
     */
    public void setUsuarioBodega(String usuarioBodega) {
        this.usuarioBodega = usuarioBodega;
    }

    /**
     * @return the detalle
     */
    public ArrayList<DetalleEnvio> getDetalle() {
        return detalle;
    }

    /**
     * @param detalle the detalle to set
     */
    public void setDetalle(ArrayList<DetalleEnvio> detalle) {
        this.detalle = detalle;
    }

}
