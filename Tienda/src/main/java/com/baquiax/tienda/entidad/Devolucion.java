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
public class Devolucion implements Serializable {

    private int id;
    private String fecha;
    private String estado;
    private double total;
    private String usuarioTienda;
    private String codigoTienda;
    private ArrayList<DetalleDevolucion> detalle;

    public Devolucion() {
    }

    /**
     *
     * @param id
     * @param fecha
     * @param estado
     * @param total
     * @param usuarioTienda
     * @param codigoTienda
     */
    public Devolucion(int id, String fecha, String estado, double total, String usuarioTienda, String codigoTienda) {
        this.id = id;
        this.fecha = fecha;
        this.estado = estado;
        this.total = total;
        this.usuarioTienda = usuarioTienda;
        this.codigoTienda = codigoTienda;
    }

    @Override
    public String toString() {
        return "Devolucion{" + "id=" + id + ", fecha=" + fecha + ", estado=" + estado + ", "
                + "total=" + total + ", usuarioTienda=" + usuarioTienda + ", codigoTienda=" + codigoTienda + ", detalle=" + detalle.toString() + '}';
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
     * @return the fecha
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
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
     * @return the usuarioTienda
     */
    public String getUsuarioTienda() {
        return usuarioTienda;
    }

    /**
     * @param usuarioTienda the usuarioTienda to set
     */
    public void setUsuarioTienda(String usuarioTienda) {
        this.usuarioTienda = usuarioTienda;
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
     * @return the detalle
     */
    public ArrayList<DetalleDevolucion> getDetalle() {
        return detalle;
    }

    /**
     * @param detalle the detalle to set
     */
    public void setDetalle(ArrayList<DetalleDevolucion> detalle) {
        this.detalle = detalle;
    }

}
