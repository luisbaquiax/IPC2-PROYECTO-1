/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.entidad.enumEntidad;

/**
 *
 * @author luis
 */
public enum TipoTiendaEnum {
    SUPERVISADA,
    NORMAL;

    public String getTipo() {
        switch (this) {
            case SUPERVISADA:
                return "supervisada";
            case NORMAL:
                return "normal";
                
            default:
                return null;
        }
    }
}
