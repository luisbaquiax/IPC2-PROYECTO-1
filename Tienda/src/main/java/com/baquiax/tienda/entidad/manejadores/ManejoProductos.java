/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.entidad.manejadores;

import com.baquiax.tienda.entidad.Producto;
import java.util.List;

/**
 *
 * @author luis
 */
public class ManejoProductos {

    public ManejoProductos() {
    }

    public Producto getProducto(String codigo, List<Producto> productos) {
        for (Producto p : productos) {
            if (p.getCodigo().equals(codigo)) {
                return p;
            }
        }
        return null;
    }

    public void removerProducto(String codigo, List<Producto> productos) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo().equals(codigo)) {
                productos.remove(i);
                break;
            }
        }
    }

    public double getTotalPedido(List<Producto> productos) {
        double total = 0;
        for (Producto producto : productos) {
            total += producto.getCosto() * producto.getExistencia();
        }
        return total;
    }
}
