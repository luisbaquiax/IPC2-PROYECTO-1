/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.entidad.manejadores;

import com.baquiax.tienda.entidad.DetalleIncidencia;
import com.baquiax.tienda.entidad.Incidencia;
import java.util.List;

/**
 *
 * @author luis
 */
public class ManejoIncidencias {

    public ManejoIncidencias() {
    }

    public Incidencia getIncidencia(int id, List<Incidencia> incidencias) {
        for (Incidencia incidencia : incidencias) {
            if (incidencia.getId() == id) {
                return incidencia;
            }
        }
        return null;
    }

    public DetalleIncidencia getDetalleIncidencia(String codigo, List<DetalleIncidencia> detalle) {
        for (DetalleIncidencia detalleIncidencia : detalle) {
            if (detalleIncidencia.getCodigoProducto().equals(codigo)) {
                return detalleIncidencia;
            }
        }
        return null;
    }

    /**
     * Serivrá para validar un producto si ya esta contenida en la lista de
     * productos de la incidencia
     *
     * @param codigoProducto
     * @param motivo
     * @param lista
     * @return
     */
    public DetalleIncidencia validarDetalleIncidencia(String codigoProducto, String motivo, List<DetalleIncidencia> lista) {
        for (DetalleIncidencia detalle : lista) {
            if ((detalle.getCodigoProducto().equals(codigoProducto)) & (detalle.getMotivo().equals(motivo))) {
                return detalle;
            }
        }
        return null;
    }

    /**
     * Remueve un producto en la incidencia
     *
     * @param codigoProducto
     * @param motivo
     * @param lista
     */
    public void removerDetalleIncidencia(String codigoProducto, String motivo, List<DetalleIncidencia> lista) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getCodigoProducto().equals(codigoProducto) & lista.get(i).getMotivo().equals(motivo)) {
                lista.remove(i);
                break;
            }
        }
    }

    public int cantidadProductos(String codigo, List<DetalleIncidencia> lista) {
        int cantidad = 0;
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getCodigoProducto().equals(codigo)) {
                cantidad += lista.get(i).getCantidad();
            }
        }
        return cantidad;
    }
}
