/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.entidad.manejadores;

import com.baquiax.tienda.entidad.DetalleEnvio;
import com.baquiax.tienda.entidad.Envio;
import java.util.List;

/**
 *
 * @author luis
 */
public class ManejoEnvio {

    public ManejoEnvio() {
    }

    public Envio getEnvio(int id, List<Envio> envios) {
        for (Envio envio : envios) {
            if (envio.getId() == id) {
                return envio;
            }
        }
        return null;
    }

    public DetalleEnvio getDetalleEnvio(String codigoProducto, List<DetalleEnvio> detalleEnvio) {
        for (DetalleEnvio detalle : detalleEnvio) {
            if (detalle.getCodigoProducto().equals(codigoProducto)) {
                return detalle;
            }
        }
        return null;
    }

}
