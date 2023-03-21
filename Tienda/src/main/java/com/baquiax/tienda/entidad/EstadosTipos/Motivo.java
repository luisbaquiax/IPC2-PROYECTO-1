/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.entidad.EstadosTipos;

/**
 *
 * @author luis
 */
public class Motivo {

    private String motivo;

    public Motivo(String motivo) {
        this.motivo = motivo;
    }

    @Override
    public String toString() {
        return "Motivo{" + "motivo=" + motivo + '}';
    }

    /**
     * @return the motivo
     */
    public String getMotivo() {
        return motivo;
    }

}
