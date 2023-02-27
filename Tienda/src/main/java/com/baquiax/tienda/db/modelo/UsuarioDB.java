/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.db.modelo;

import com.baquiax.tienda.db.coneccion.ConeccionDB;
import com.baquiax.tienda.entidad.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luis
 */
public class UsuarioDB {

    private final static String INSERT = "INSERT INTO usuario(codigo,tipo,nombre_usuario,nombre,password) VALUES(?,?,?,?,?)";
    private final static String UPDATE = "UPDATE usuario SET tipo = ?, nombre_usuario = ?, nombre = ? , password = ? WHERE codigo = ?";
    private final static String SELECT = "SELECT * FROM usuario";

    private ResultSet resultSet;

    public UsuarioDB() {
    }

    /**
     * INSER A NEW USER
     *
     * @param usuario
     * @return
     */
    public boolean insert(Usuario usuario) {
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(INSERT)) {
            statement.setString(1, usuario.getCodigo());
            statement.setString(2, usuario.getTipo());
            statement.setString(3, usuario.getNombreUsuario());
            statement.setString(4, usuario.getNombre());
            statement.setString(5, usuario.getPassword());
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * UPDATE A USER
     *
     * @param usuario
     * @return
     */
    public boolean update(Usuario usuario) {
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(UPDATE)) {
            statement.setString(1, usuario.getTipo());
            statement.setString(2, usuario.getNombreUsuario());
            statement.setString(3, usuario.getNombre());
            statement.setString(4, usuario.getPassword());
            statement.setString(5, usuario.getCodigo());

            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * LISTA A TODOS LOS USUARIOS
     *
     * @return
     */
    public ArrayList<Usuario> getUsers() {
        List<Usuario> usuarios = new ArrayList<>();
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(SELECT)) {
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                usuarios.add(getUser(resultSet));
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return (ArrayList<Usuario>) usuarios;
    }

    private Usuario getUser(ResultSet resultSet) throws SQLException {
        return new Usuario(
                resultSet.getString("codigo"),
                resultSet.getString("tipo"),
                resultSet.getString("nombre_usuario"),
                resultSet.getString("nombre"),
                resultSet.getString("password"));
    }
}
