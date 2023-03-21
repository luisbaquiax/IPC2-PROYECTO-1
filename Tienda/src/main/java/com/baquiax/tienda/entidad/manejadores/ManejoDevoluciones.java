/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.entidad.manejadores;

import com.baquiax.tienda.entidad.DetalleDevolucion;
import com.baquiax.tienda.entidad.Devolucion;
import java.util.List;

/**
 *
 * @author luis
 */
public class ManejoDevoluciones {

    public ManejoDevoluciones() {
    }

    public Devolucion getDevolucion(int id, List<Devolucion> devolucions) {
        for (Devolucion devolucion : devolucions) {
            if (devolucion.getId() == id) {
                return devolucion;
            }
        }
        return null;
    }

    public DetalleDevolucion getDetalleDevolucion(String codigoProducto, String motivo, List<DetalleDevolucion> devolucionDetalles) {
        for (DetalleDevolucion detalleDevolucion : devolucionDetalles) {
            if (detalleDevolucion.getCodigoProducto().equals(codigoProducto)
                    && (detalleDevolucion.getMotivo().equals(motivo))) {
                return detalleDevolucion;
            }
        }
        return null;
    }

    public double getTotal(List<DetalleDevolucion> devolucionDetalles) {
        int total = 0;
        for (DetalleDevolucion devolucionDetalle : devolucionDetalles) {
            total += devolucionDetalle.getCantidad() * devolucionDetalle.getCostoUnitario();
        }
        return total;
    }
}
