/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.entidad.manejadores;

import com.baquiax.tienda.entidad.Tienda;
import java.util.List;

/**
 *
 * @author luis
 */
public class ManejoTiendas {

    public ManejoTiendas() {
    }

    /**
     *
     * @param codigo
     * @param tiendas
     * @return una tienda contenida en una lista de tienda
     */
    public Tienda getTienda(String codigo, List<Tienda> tiendas) {
        for (Tienda tienda : tiendas) {
            if (tienda.getCodigo().equals(codigo)) {
                return tienda;
            }
        }
        return null;
    }
}
