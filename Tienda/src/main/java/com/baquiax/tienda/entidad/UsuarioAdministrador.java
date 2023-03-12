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
public class UsuarioAdministrador extends Usuario implements Serializable {

    public UsuarioAdministrador() {
    }

    public UsuarioAdministrador(String codigo, String tipo, String nombreUsuario, String nombre, String password, boolean estado, String emial) {
        super(codigo, tipo, nombreUsuario, nombre, password, estado, emial);
    }

}
