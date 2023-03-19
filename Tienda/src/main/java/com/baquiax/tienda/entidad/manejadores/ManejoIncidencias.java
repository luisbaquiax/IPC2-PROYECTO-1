/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.entidad.manejadores;

import com.baquiax.tienda.entidad.Incidencia;
import java.util.List;

/**
 *
 * @author luis
 */
public class ManejoIncidencias {

    public ManejoIncidencias() {
    }

    public Incidencia getIncidencia(int id, List<Incidencia> incidencias) {
        for (Incidencia incidencia : incidencias) {
            if (incidencia.getId() == id) {
                return incidencia;
            }
        }
        return null;
    }
}
