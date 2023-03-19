/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.entidad.EstadosTipos;

import com.baquiax.tienda.entidad.EstadosTipos.EstadoDevolucion;
import com.baquiax.tienda.entidad.enumEntidad.EstadoDevolucionEnum;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luis
 */
public class ListaEstadoDevolucion {

    private List<EstadoDevolucion> estadoDevolucions;

    public ListaEstadoDevolucion() {
        this.estadoDevolucions = new ArrayList<>();
        this.estadoDevolucions.add(new EstadoDevolucion(EstadoDevolucionEnum.ACEPTADA.toString()));
        this.estadoDevolucions.add(new EstadoDevolucion(EstadoDevolucionEnum.ACTIVA.toString()));
        this.estadoDevolucions.add(new EstadoDevolucion(EstadoDevolucionEnum.RECHAZADA.toString()));

    }

    public List<EstadoDevolucion> getEstadoDevolucions() {
        return estadoDevolucions;
    }

}
