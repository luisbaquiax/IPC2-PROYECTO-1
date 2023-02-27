/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.db.modelo;

import com.baquiax.tienda.db.coneccion.ConeccionDB;
import com.baquiax.tienda.entidad.UsuarioBodega;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author luis
 */
public class UsuarioBodegaDB {

    private final static String INSERT = "INSERT INTO usuario_bodega(codigo,email) VALUES(?,?)";

    public UsuarioBodegaDB() {
    }

    /**
     *
     * @param usuarioBodega
     * @return
     */
    public boolean insert(UsuarioBodega usuarioBodega) {
        try (PreparedStatement stamtment = ConeccionDB.getConnection().prepareStatement(INSERT)) {
            stamtment.setString(1, usuarioBodega.getCodigo());
            stamtment.setString(2, usuarioBodega.getEmail());
            stamtment.executeUpdate();

            stamtment.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

}
