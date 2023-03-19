/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.entidad.manejadores;

import com.baquiax.tienda.entidad.Producto;
import com.baquiax.tienda.entidad.Usuario;
import java.util.List;

/**
 *
 * @author luis
 */
public class ManejoUsuario {

    public ManejoUsuario() {
    }

    public Usuario getUser(String codigo, List<Usuario> users) {
        for (Usuario u : users) {
            if (u.getCodigo().equals(codigo)) {
                return u;
            }
        }
        return null;
    }
}
