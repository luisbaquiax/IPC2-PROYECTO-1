/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.entidad;

import java.io.Serializable;

/**
 *
 * @author luis
 */
public class BodegaTienda implements Serializable {

    private String codigoBedeguero;
    private String codigoTienda;

    public BodegaTienda(String codigoBedeguero, String codigoTienda) {
        this.codigoBedeguero = codigoBedeguero;
        this.codigoTienda = codigoTienda;
    }

    /**
     * @return the codigoBedeguero
     */
    public String getCodigoBedeguero() {
        return codigoBedeguero;
    }

    /**
     * @param codigoBedeguero the codigoBedeguero to set
     */
    public void setCodigoBedeguero(String codigoBedeguero) {
        this.codigoBedeguero = codigoBedeguero;
    }

    /**
     * @return the codigoTienda
     */
    public String getCodigoTienda() {
        return codigoTienda;
    }

    /**
     * @param codigoTienda the codigoTienda to set
     */
    public void setCodigoTienda(String codigoTienda) {
        this.codigoTienda = codigoTienda;
    }

}
