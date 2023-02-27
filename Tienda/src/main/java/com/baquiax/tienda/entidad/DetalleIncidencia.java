/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.entidad;

import java.io.Serializable;

/**
 *
 * @author luis
 */
public class DetalleIncidencia implements Serializable {

    private int idIncidencia;
    private String codigoProducto;
    private int cantidad;
    private String motivo;

    /**
     *
     * @param idIncidencia
     * @param codigoProducto
     * @param cantidad
     * @param motivo
     */
    public DetalleIncidencia(int idIncidencia, String codigoProducto, int cantidad, String motivo) {
        this.idIncidencia = idIncidencia;
        this.codigoProducto = codigoProducto;
        this.cantidad = cantidad;
        this.motivo = motivo;
    }

    /**
     * @return the idIncidencia
     */
    public int getIdIncidencia() {
        return idIncidencia;
    }

    /**
     * @param idIncidencia the idIncidencia to set
     */
    public void setIdIncidencia(int idIncidencia) {
        this.idIncidencia = idIncidencia;
    }

    /**
     * @return the codigoProducto
     */
    public String getCodigoProducto() {
        return codigoProducto;
    }

    /**
     * @param codigoProducto the codigoProducto to set
     */
    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    /**
     * @return the cantidad
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the motivo
     */
    public String getMotivo() {
        return motivo;
    }

    /**
     * @param motivo the motivo to set
     */
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

}
