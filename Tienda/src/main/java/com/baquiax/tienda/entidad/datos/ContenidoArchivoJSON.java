/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.entidad.datos;

import java.io.BufferedReader;
import java.io.IOException;

/**
 *
 * @author luis
 */
public class ContenidoArchivoJSON {

    public ContenidoArchivoJSON() {
    }

    public String getContenidioArchivoJSON(BufferedReader bufferedReader) throws IOException {
        String todaInformacion = "";
        String linea = "";
        while (bufferedReader.ready()) {
            linea = bufferedReader.readLine();
            todaInformacion += linea + "\n";
        }
        return todaInformacion;
    }
}
