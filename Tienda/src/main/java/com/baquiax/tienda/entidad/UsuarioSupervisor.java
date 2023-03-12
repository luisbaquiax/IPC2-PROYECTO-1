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

    public UsuarioSupervisor() {
    }

    public UsuarioSupervisor(String codigo, String tipo, String nombreUsuario, String nombre, String password, boolean estado, String email) {
        super(codigo, tipo, nombreUsuario, nombre, password, estado, email);
    }
}
