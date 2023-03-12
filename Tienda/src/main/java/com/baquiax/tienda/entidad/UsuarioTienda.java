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
public class UsuarioTienda extends Usuario implements Serializable {

    private String codigoTienda;

    public UsuarioTienda() {
    }

    public UsuarioTienda(String codigo, String tipo, String nombreUsuario, String nombre, String password, boolean estado, String emial) {
        super(codigo, tipo, nombreUsuario, nombre, password, estado, emial);
    }

    public UsuarioTienda(String codigoTienda, String codigo, String tipo, String nombreUsuario, String nombre, String password, boolean estado, String emial) {
        super(codigo, tipo, nombreUsuario, nombre, password, estado, emial);
        this.codigoTienda = codigoTienda;
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

}
