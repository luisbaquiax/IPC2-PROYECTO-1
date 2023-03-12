/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.entidad.EstadosTipos;

import com.baquiax.tienda.entidad.enumEntidad.EstadoIncidenciaEnum;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luis
 */
public class ListaEstadosIncidencia {

    private List<EstadoIncidencia> estadoIncidencias;

    public ListaEstadosIncidencia() {
        this.estadoIncidencias = new ArrayList<>();
        this.estadoIncidencias.add(new EstadoIncidencia(EstadoIncidenciaEnum.ACTIVA.toString()));
        this.estadoIncidencias.add(new EstadoIncidencia(EstadoIncidenciaEnum.SOLUCIONADA.toString()));
    }

    public List<EstadoIncidencia> getEstadoIncidencias() {
        return estadoIncidencias;
    }

}
