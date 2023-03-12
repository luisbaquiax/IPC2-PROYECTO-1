/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.db.modelo;

import com.baquiax.tienda.db.coneccion.ConeccionDB;
import com.baquiax.tienda.entidad.UsuarioTienda;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luis
 */
public class UsuarioTiendaDB {

    private final static String INSERT = "INSERT INTO usuario_tienda(codigo,codigo_tienda) VALUES(?,?)";
    private final static String UPDATE = "UPDATE usuario_tienda SET codigo_tienda = ? WHERE codigo = ?";
    private final static String SELECT
            = "SELECT *\n"
            + "FROM usuario p\n"
            + "RIGHT JOIN usuario_tienda u\n"
            + "ON  p.codigo = u.codigo;";

    private ResultSet resultSet;

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
            stamtment.executeUpdate();

            stamtment.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    /**
     *
     * @param usuarioTienda
     * @return
     */
    public boolean update(UsuarioTienda usuarioTienda) {
        try (PreparedStatement stamtment = ConeccionDB.getConnection().prepareStatement(UPDATE)) {
            stamtment.setString(1, usuarioTienda.getCodigoTienda());
            stamtment.setString(2, usuarioTienda.getCodigo());
            stamtment.executeUpdate();

            stamtment.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Lista los usuarios con tienda
     *
     * @return
     */
    public List<UsuarioTienda> getUsers() {
        List<UsuarioTienda> usuarios = new ArrayList<>();
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(SELECT)) {
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                usuarios.add(getUser(resultSet));
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
        }
        return usuarios;
    }

    private UsuarioTienda getUser(ResultSet resultSet) throws SQLException {
        return new UsuarioTienda(
                resultSet.getString("codigo_tienda"),
                resultSet.getString("codigo"),
                resultSet.getString("tipo"),
                resultSet.getString("nombre_usuario"),
                resultSet.getString("nombre"),
                resultSet.getString("password"),
                resultSet.getBoolean("estado"),
                resultSet.getString("email"));
    }
}
