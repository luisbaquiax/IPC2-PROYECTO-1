/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.db.modelo;

import com.baquiax.tienda.db.coneccion.ConeccionDB;
import com.baquiax.tienda.entidad.Incidencia;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luis
 */
public class IncidenciaDB {

    private final static String INSERT = "INSERT INTO incidencia(fecha,estado,usuario_tienda,codigo_tienda) VALUES(?,?,?,?)";
    private final static String INSERT_FROM_FILE = "INSERT INTO incidencia(id,fecha,estado,usuario_tienda,codigo_tienda) VALUES(?,?,?,?,?)";
    private final static String UPDATE = "UPDATE incidencia SET estado = ? WHERE id = ?";

    private final static String SELECT_BY_USUARIO_BODEGA_BY_ESTADO
            = "SELECT p.id, p.fecha, p.estado, p.usuario_tienda, p.codigo_tienda \n"
            + "FROM incidencia p\n"
            + "RIGHT JOIN bodega_tienda b\n"
            + "ON  p.codigo_tienda = b.codigo_tienda WHERE b.codigo_usuario_bodega = ? AND p.estado = ?";

    private final static String SELECT_BY_USUARIO_BODEGA
            = "SELECT p.id, p.fecha, p.estado, p.usuario_tienda, p.codigo_tienda \n"
            + "FROM incidencia p\n"
            + "RIGHT JOIN bodega_tienda b\n"
            + "ON  p.codigo_tienda = b.codigo_tienda WHERE b.codigo_usuario_bodega = ?";

    private final static String SELECT_BY_USUARIO_BODEGA_BY_TIENDA
            = "SELECT p.id, p.fecha, p.estado, p.usuario_tienda, p.codigo_tienda \n"
            + "FROM incidencia p\n"
            + "RIGHT JOIN bodega_tienda b\n"
            + "ON  p.codigo_tienda = b.codigo_tienda \n"
            + "WHERE b.codigo_usuario_bodega = ? AND p.codigo_tienda = ?";
    private ResultSet resultSet;

    public IncidenciaDB() {
    }

    /**
     * Insert a new incidencia
     *
     * @param incidendcia
     * @return
     */
    public boolean insert(Incidencia incidendcia) {
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(INSERT)) {
            statement.setString(1, incidendcia.getFecha());
            statement.setString(2, incidendcia.getEstado());
            statement.setString(3, incidendcia.getUsuarioTienda());
            statement.setString(4, incidendcia.getCodigoTienda());
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Insert a new incidencia from file GSON
     *
     * @param incidendcia
     * @return
     */
    public boolean insertFromFile(Incidencia incidendcia) {
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(INSERT_FROM_FILE)) {
            statement.setInt(1, incidendcia.getId());
            statement.setString(2, incidendcia.getFecha());
            statement.setString(3, incidendcia.getEstado());
            statement.setString(4, incidendcia.getUsuarioTienda());
            statement.setString(5, incidendcia.getCodigoTienda());
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Update an incidencia
     *
     * @param incidendcia
     * @return
     */
    public boolean update(Incidencia incidendcia) {
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(UPDATE)) {
            statement.setString(1, incidendcia.getEstado());
            statement.setInt(2, incidendcia.getId());
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * LISTA LAS INCIDENCIAS A CARGO DE UN USUARIO_BODEGA
     *
     * @param usuarioBodega
     * @param estado
     * @return
     */
    public ArrayList<Incidencia> getIncidencia(String usuarioBodega, String estado) {
        List<Incidencia> incidencias = new ArrayList<>();
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(SELECT_BY_USUARIO_BODEGA_BY_ESTADO)) {
            while (resultSet.next()) {
                incidencias.add(getIncidencia(resultSet));
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return (ArrayList<Incidencia>) incidencias;
    }

    /**
     * Lista todas las incidencias a cargo de un usuario de bodega
     *
     * @param usuarioBodega
     * @return
     */
    public ArrayList<Incidencia> getIncidencia(String usuarioBodega) {
        List<Incidencia> incidencias = new ArrayList<>();
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(SELECT_BY_USUARIO_BODEGA)) {
            while (resultSet.next()) {
                incidencias.add(getIncidencia(resultSet));
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return (ArrayList<Incidencia>) incidencias;
    }

    /**
     * Lista todas las incidencias a cargo de un usuario de bodega filtrando por
     * tienda
     *
     * @param usuarioBodega
     * @param codigoTienda
     * @return
     */
    public ArrayList<Incidencia> getIncidenciaTiendaUsuario(String usuarioBodega, String codigoTienda) {
        List<Incidencia> incidencias = new ArrayList<>();
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(SELECT_BY_USUARIO_BODEGA_BY_TIENDA)) {
            while (resultSet.next()) {
                incidencias.add(getIncidencia(resultSet));
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return (ArrayList<Incidencia>) incidencias;
    }

    private Incidencia getIncidencia(ResultSet resultSet) throws SQLException {
        return new Incidencia(
                resultSet.getInt("id"),
                resultSet.getString("fecha"),
                resultSet.getString("estado"),
                resultSet.getString("usuario_tienda"),
                resultSet.getString("codigo_tienda"));
    }
}
