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
public class Pedido implements Serializable {

    private int id;
    private String fecha;
    private double total;
    private String estado;
    private String usuarioTienda;
    private String codigoTienda;
    private String codigoSupervisor;

    private ArrayList<DetallePedido> detalle;

    public Pedido() {
    }

    /**
     *
     * @param id
     * @param fecha
     * @param total
     * @param estado
     * @param usuarioTienda
     * @param codigoTienda
     * @param codigoSupervisor
     */
    public Pedido(int id, String fecha, double total, String estado, String usuarioTienda, String codigoTienda, String codigoSupervisor) {
        this.id = id;
        this.fecha = fecha;
        this.total = total;
        this.estado = estado;
        this.usuarioTienda = usuarioTienda;
        this.codigoTienda = codigoTienda;
        this.codigoSupervisor = codigoSupervisor;
    }

    /**
     * Insert new pedido
     *
     * @param fecha
     * @param total
     * @param estado
     * @param usuarioTienda
     * @param codigoTienda
     * @param codigoSupervisor
     * @param detalle
     */
    public Pedido(String fecha, double total, String estado, String usuarioTienda, String codigoTienda, String codigoSupervisor, ArrayList<DetallePedido> detalle) {
        this.fecha = fecha;
        this.total = total;
        this.estado = estado;
        this.usuarioTienda = usuarioTienda;
        this.codigoTienda = codigoTienda;
        this.codigoSupervisor = codigoSupervisor;
        this.detalle = detalle;
    }

    @Override
    public String toString() {
        return "Pedido{" + "id=" + id + ", fecha=" + fecha + ", total=" + total + ", estado=" + estado + ", usuarioTienda=" + usuarioTienda + ", codigoTienda=" + codigoTienda + ", codigoSupervisor=" + codigoSupervisor + ", detalle=" + detalle + '}';
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
    public ArrayList<DetallePedido> getDetalle() {
        return detalle;
    }

    /**
     * @param detalle the detalle to set
     */
    public void setDetalle(ArrayList<DetallePedido> detalle) {
        this.detalle = detalle;
    }

    /**
     * @return the codigoSupervisor
     */
    public String getCodigoSupervisor() {
        return codigoSupervisor;
    }

    /**
     * @param codigoSupervisor the codigoSupervisor to set
     */
    public void setCodigoSupervisor(String codigoSupervisor) {
        this.codigoSupervisor = codigoSupervisor;
    }

}
