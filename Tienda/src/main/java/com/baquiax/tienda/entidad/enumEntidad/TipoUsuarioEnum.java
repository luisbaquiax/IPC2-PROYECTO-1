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
public enum TipoUsuarioEnum {
    USUARIO_TIENDA,
    USUARIO_ADMIN,
    USUARIO_BODEGA,
    USUARIO_SUPERVISOR;

    /**
     * TIPO DE USUARIO
     *
     * @return
     */
    public String getTipo() {

        // restornamos la cadena según el tipo de usuario
        switch (this) {
            case USUARIO_TIENDA:
                return "USUARIOSTIENDA";

            case USUARIO_ADMIN:
                return "ADMINS";

            case USUARIO_BODEGA:
                return "ENCARGADOSBODEGA";

            case USUARIO_SUPERVISOR:
                return "SUPERVISORES";

            default:
                return null;
        }
    }
}
