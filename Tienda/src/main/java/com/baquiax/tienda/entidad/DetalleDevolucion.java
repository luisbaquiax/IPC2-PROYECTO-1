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
public class DetalleDevolucion implements Serializable {

    private int idDevolucion;
    private String codigoProducto;
    private double costoUnitario;
    private int cantidad;
    private String motivo;
    private double subTotal;

    /**
     *
     * @param idDevolucion
     * @param codigoProducto
     * @param costoUnitario
     * @param cantidad
     * @param motivo
     * @param sutotal
     */
    public DetalleDevolucion(int idDevolucion, String codigoProducto, double costoUnitario, int cantidad, String motivo, double sutotal) {
        this.idDevolucion = idDevolucion;
        this.codigoProducto = codigoProducto;
        this.costoUnitario = costoUnitario;
        this.cantidad = cantidad;
        this.motivo = motivo;
        this.subTotal = sutotal;
    }

    /**
     * @return the idDevolucion
     */
    public int getIdDevolucion() {
        return idDevolucion;
    }

    /**
     * @param idDevolucion the idDevolucion to set
     */
    public void setIdDevolucion(int idDevolucion) {
        this.idDevolucion = idDevolucion;
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
     * @return the costoUnitario
     */
    public double getCostoUnitario() {
        return costoUnitario;
    }

    /**
     * @param costoUnitario the costoUnitario to set
     */
    public void setCostoUnitario(double costoUnitario) {
        this.costoUnitario = costoUnitario;
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

    /**
     * @return the sutotal
     */
    public double getSubTotal() {
        return subTotal;
    }

    /**
     * @param subTotal the sutotal to set
     */
    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }
}
