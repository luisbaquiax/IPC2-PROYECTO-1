/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.db.modelo;

import com.baquiax.tienda.db.coneccion.ConeccionDB;
import com.baquiax.tienda.entidad.UsuarioTienda;
import java.sql.*;

/**
 *
 * @author luis
 */
public class UsuarioTiendaDB {

    private final static String INSERT = "INSERT INTO usuario_tienda(codigo,codigo_tienda,email) VALUES(?,?,?)";

    public UsuarioTiendaDB() {
    }

    /**
     *
     * @param usuarioTienda
     * @return
     */
    public boolean insert(UsuarioTienda usuarioTienda) {
        try (PreparedStatement stamtment = ConeccionDB.getConnection().prepareStatement(INSERT)) {
            stamtment.setString(1, usuarioTienda.getCodigo());
            stamtment.setString(2, usuarioTienda.getCodigoTienda());
            stamtment.setString(3, usuarioTienda.getEmail());
            stamtment.executeUpdate();

            stamtment.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

}
