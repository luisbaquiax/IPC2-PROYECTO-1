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
public class UsuarioSupervisor extends Usuario implements Serializable {

    private String email;

    public UsuarioSupervisor(String codigo, String tipo, String nombreUsuario, String nombre, String password) {
        super(codigo, tipo, nombreUsuario, nombre, password);
    }

    public UsuarioSupervisor(String email, String codigo, String tipo, String nombreUsuario, String nombre, String password) {
        super(codigo, tipo, nombreUsuario, nombre, password);
        this.email = email;
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
