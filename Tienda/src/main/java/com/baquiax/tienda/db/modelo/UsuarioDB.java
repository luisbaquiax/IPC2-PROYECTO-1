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

    private final static String INSERT = "INSERT INTO usuario(codigo,tipo,nombre_usuario,nombre,password,estado,email) VALUES(?,?,?,?,?,?,?)";
    private final static String UPDATE = "UPDATE usuario SET tipo = ?, nombre_usuario = ?, nombre = ? , password = ?, email = ?, estado = ? WHERE codigo = ?";
    private final static String SELECT_BY_TIPO = "SELECT * FROM usuario WHERE tipo = ?";
    private final static String SELECT_ALL = "SELECT * FROM usuario";

    private final static String SELECT_BY_USERNAME_BY_PASSWORD = "SELECT * FROM usuario WHERE nombre_usuario = ? AND password = ?";

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
            statement.setBoolean(6, usuario.isEstado());
            statement.setString(7, usuario.getEmail());
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
            statement.setString(5, usuario.getEmail());
            statement.setBoolean(6, usuario.isEstado());
            statement.setString(7, usuario.getCodigo());

            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * LISTA A TODOS LOS USUARIOS POR TIPO_USUARIO
     *
     * @param tipo
     * @return
     */
    public ArrayList<Usuario> getUsers(String tipo) {
        List<Usuario> usuarios = new ArrayList<>();
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(SELECT_BY_TIPO)) {
            statement.setString(1, tipo);
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

    /**
     * List of all users
     *
     * @return
     */
    public ArrayList<Usuario> getUsers() {
        List<Usuario> usuarios = new ArrayList<>();
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(SELECT_ALL)) {
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

    /**
     * Servirá validar el inicio de sesión de un usuario
     *
     * @param username
     * @param password
     * @return
     */
    public Usuario getUser(String username, String password) {
        Usuario usuario = null;
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(SELECT_BY_USERNAME_BY_PASSWORD)) {
            statement.setString(1, username);
            statement.setString(2, password);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                usuario = getUser(resultSet);
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return usuario;
    }

    private Usuario getUser(ResultSet resultSet) throws SQLException {
        return new Usuario(
                resultSet.getString("codigo"),
                resultSet.getString("tipo"),
                resultSet.getString("nombre_usuario"),
                resultSet.getString("nombre"),
                resultSet.getString("password"),
                resultSet.getBoolean("estado"),
                resultSet.getString("email"));
    }
}
