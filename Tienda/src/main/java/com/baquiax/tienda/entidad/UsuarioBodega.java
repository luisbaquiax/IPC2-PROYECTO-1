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

    private String email;
    private ArrayList<Tienda> tiendas;

    public UsuarioBodega(String codigo, String tipo, String nombreUsuario, String nombre, String password) {
        super(codigo, tipo, nombreUsuario, nombre, password);
    }

    public UsuarioBodega(String email, ArrayList<Tienda> tiendas, String codigo, String tipo, String nombreUsuario, String nombre, String password) {
        super(codigo, tipo, nombreUsuario, nombre, password);
        this.email = email;
        this.tiendas = tiendas;
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
