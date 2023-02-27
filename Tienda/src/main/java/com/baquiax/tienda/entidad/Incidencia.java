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
public class Incidencia implements Serializable {

    private int id;
    private String fecha;
    private String estado;
    private String usuarioTienda;
    private String codigoTienda;
    private ArrayList<DetalleIncidencia> detalle;

    /**
     *
     * @param id
     * @param fecha
     * @param estado
     * @param usuarioTienda
     * @param codigoTienda
     */
    public Incidencia(int id, String fecha, String estado, String usuarioTienda, String codigoTienda) {
        this.id = id;
        this.fecha = fecha;
        this.estado = estado;
        this.usuarioTienda = usuarioTienda;
        this.codigoTienda = codigoTienda;
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
    public ArrayList<DetalleIncidencia> getDetalle() {
        return detalle;
    }

    /**
     * @param detalle the detalle to set
     */
    public void setDetalle(ArrayList<DetalleIncidencia> detalle) {
        this.detalle = detalle;
    }

}
