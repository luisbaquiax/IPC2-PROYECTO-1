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
public enum MotivoIncidenciaEnum {
    PRODUCTO_EQUIVOCADO,
    PRODUCTO_DAÑADO,
    PRODUCTO_NO_SOLICITADO,
    FALTANTE_DE_PRODUCTO,
    SOBREANTE_PRODUCTO;

    public String getMotivo() {
        switch (this) {
            case PRODUCTO_EQUIVOCADO:
                return "PRODUCTO EQUIVOCADO";
            case PRODUCTO_DAÑADO:
                return "PRODUCTO DAÑADO";
            case PRODUCTO_NO_SOLICITADO:
                return "PRODUCTO NO SOLICITADO";
            case FALTANTE_DE_PRODUCTO:
                return "FALTANTE DE PRODUCTO";
            case SOBREANTE_PRODUCTO:
                return "SOBRANTE DE PRODUCTO";
            default:
                return null;
        }
    }
}
