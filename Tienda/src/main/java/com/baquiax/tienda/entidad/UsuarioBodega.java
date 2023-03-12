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
public class UsuarioBodega extends Usuario implements Serializable {

    private ArrayList<Tienda> tiendas;

    public UsuarioBodega() {
    }

    public UsuarioBodega(String codigo, String tipo, String nombreUsuario, String nombre, String password, boolean estado, String email) {
        super(codigo, tipo, nombreUsuario, nombre, password, estado, email);
    }

    /**
     * @return the tiendas
     */
    public ArrayList<Tienda> getTiendas() {
        return tiendas;
    }

    /**
     * @param tiendas the tiendas to set
     */
    public void setTiendas(ArrayList<Tienda> tiendas) {
        this.tiendas = tiendas;
    }

}
