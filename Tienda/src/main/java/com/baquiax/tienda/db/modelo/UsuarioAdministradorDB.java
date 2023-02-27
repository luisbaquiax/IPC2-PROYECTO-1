/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.db.modelo;

import com.baquiax.tienda.db.coneccion.ConeccionDB;
import com.baquiax.tienda.entidad.UsuarioAdministrador;
import java.sql.*;

/**
 *
 * @author luis
 */
public class UsuarioAdministradorDB {

    private final static String INSERT = "INSERT INTO administrador(codigo) VALUES(?)";

    public UsuarioAdministradorDB() {
    }

    /**
     *
     * @param administrador
     * @return
     */
    public boolean insert(UsuarioAdministrador administrador) {
        try (PreparedStatement stamtment = ConeccionDB.getConnection().prepareStatement(INSERT)) {

            stamtment.setString(1, administrador.getCodigo());
            stamtment.executeUpdate();

            stamtment.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

}
