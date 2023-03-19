/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.entidad.EstadosTipos;

import com.baquiax.tienda.entidad.enumEntidad.EstadoEnvioEnum;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luis
 */
public class ListaEstadosEnvio {

    private List<String> estadosEnvio;

    public ListaEstadosEnvio() {
        this.estadosEnvio = new ArrayList<>();
        this.estadosEnvio.add(EstadoEnvioEnum.DESPACHADO.toString());
        this.estadosEnvio.add(EstadoEnvioEnum.RECIBIDO.toString());
    }

    public List<String> getEstadosEnvio() {
        return estadosEnvio;
    }

}
