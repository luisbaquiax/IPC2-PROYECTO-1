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
    private String email;

    public UsuarioTienda(String codigo, String tipo, String nombreUsuario, String nombre, String password) {
        super(codigo, tipo, nombreUsuario, nombre, password);
    }

    public UsuarioTienda(String codigoTienda, String email, String codigo, String tipo, String nombreUsuario, String nombre, String password) {
        super(codigo, tipo, nombreUsuario, nombre, password);
        this.codigoTienda = codigoTienda;
        this.email = email;
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
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

}
