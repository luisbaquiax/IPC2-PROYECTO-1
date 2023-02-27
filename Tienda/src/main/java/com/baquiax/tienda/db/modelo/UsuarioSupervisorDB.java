/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.db.modelo;

import com.baquiax.tienda.db.coneccion.ConeccionDB;
import com.baquiax.tienda.entidad.UsuarioSupervisor;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author luis
 */
public class UsuarioSupervisorDB {

    private final static String INSERT = "INSERT INTO usuario_supervisor(codigo,email) VALUES(?,?)";

    public UsuarioSupervisorDB() {
    }

    /**
     *
     * @param usuarioSupervisor
     * @return
     */
    public boolean insert(UsuarioSupervisor usuarioSupervisor) {
        try (PreparedStatement stamtment = ConeccionDB.getConnection().prepareStatement(INSERT)) {
            stamtment.setString(1, usuarioSupervisor.getCodigo());
            stamtment.setString(2, usuarioSupervisor.getEmail());
            stamtment.executeUpdate();

            stamtment.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
}
