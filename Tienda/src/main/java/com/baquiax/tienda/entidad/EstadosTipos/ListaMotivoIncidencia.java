/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.entidad.EstadosTipos;

import com.baquiax.tienda.entidad.enumEntidad.MotivoIncidenciaEnum;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luis
 */
public class ListaMotivoIncidencia {

    private List<Motivo> motivos;

    public ListaMotivoIncidencia() {
        this.motivos = new ArrayList<>();
        this.motivos.add(new Motivo(MotivoIncidenciaEnum.FALTANTE_DE_PRODUCTO.getMotivo()));
        this.motivos.add(new Motivo(MotivoIncidenciaEnum.PRODUCTO_DAÑADO.getMotivo()));
        this.motivos.add(new Motivo(MotivoIncidenciaEnum.PRODUCTO_EQUIVOCADO.getMotivo()));
        this.motivos.add(new Motivo(MotivoIncidenciaEnum.PRODUCTO_NO_SOLICITADO.getMotivo()));
        this.motivos.add(new Motivo(MotivoIncidenciaEnum.SOBREANTE_PRODUCTO.getMotivo()));
    }
    
    /**
     * @return the motivos
     */
    public List<Motivo> getMotivos() {
        return motivos;
    }

}
