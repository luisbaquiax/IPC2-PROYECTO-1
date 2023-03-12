/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.entidad.EstadosTipos;

import com.baquiax.tienda.entidad.enumEntidad.EstadoPedidoEnum;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luis
 */
public class ListaEstadosPedido {

    private List<EstadoPedido> estadoPedidos;

    public ListaEstadosPedido() {
        this.estadoPedidos = new ArrayList<>();
        this.estadoPedidos.add(new EstadoPedido(EstadoPedidoEnum.COMPLETADO.toString()));
        this.estadoPedidos.add(new EstadoPedido(EstadoPedidoEnum.ENVIADO.toString()));
        this.estadoPedidos.add(new EstadoPedido(EstadoPedidoEnum.PENDIENTE.toString()));
        this.estadoPedidos.add(new EstadoPedido(EstadoPedidoEnum.RECHAZADO.toString()));
        this.estadoPedidos.add(new EstadoPedido(EstadoPedidoEnum.SOLICITADO.toString()));
    }

    public List<EstadoPedido> getEstadoPedidos() {
        return estadoPedidos;
    }

}
