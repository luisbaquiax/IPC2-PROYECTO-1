/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.entidad.manejadores;

/**
 *
 * @author luis
 */
public class DatoError {

    private String tipo;
    private String informacion;

    public DatoError(String tipo, String informacion) {
        this.tipo = tipo;
        this.informacion = informacion;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @return the informacion
     */
    public String getInformacion() {
        return informacion;
    }

}
