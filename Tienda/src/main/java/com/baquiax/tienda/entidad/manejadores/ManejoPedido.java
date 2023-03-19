/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.entidad.manejadores;

import com.baquiax.tienda.entidad.Pedido;
import java.util.List;

/**
 *
 * @author luis
 */
public class ManejoPedido {

    public ManejoPedido() {
    }

    public Pedido getPedido(int id, List<Pedido> pedidos) {
        for (Pedido pedido : pedidos) {
            if (pedido.getId() == id) {
                return pedido;
            }
        }
        return null;
    }

}
