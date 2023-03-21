/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.entidad.EstadosTipos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luis
 */
public class ListaMotivoDevolucion {

    private List<Motivo> motivos;

    public ListaMotivoDevolucion() {
        this.motivos = new ArrayList<>();
        this.motivos.add(new Motivo("PRODUCTO DAÑADO"));
        this.motivos.add(new Motivo("PRODUCTO EQUIVOCADO"));
        this.motivos.add(new Motivo("PRODUCTO NO SOLICITADO"));
        this.motivos.add(new Motivo("PRODUCTO SOBRANTE DE PRODUCTO"));

    }

    /**
     * @return the motivos
     */
    public List<Motivo> getMotivos() {
        return motivos;
    }

}
