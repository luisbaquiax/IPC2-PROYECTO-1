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

    private final static String INSERT = "INSERT INTO incidencia(fecha,estado,usuario_tienda,codigo_tienda,id_envio) VALUES(?,?,?,?,?)";
    private final static String INSERT_FROM_FILE = "INSERT INTO incidencia(id,fecha,estado,usuario_tienda,codigo_tienda,id_envio) VALUES(?,?,?,?,?,?)";
    private final static String UPDATE = "UPDATE incidencia SET estado = ? WHERE id = ?";
    /**
     * Lista las incidencias por usuario_bodega y por estado
     */
    private final static String SELECT_BY_USUARIO_BODEGA_BY_ESTADO
            = "SELECT p.id, p.fecha, p.estado, p.usuario_tienda, p.codigo_tienda, p.id_envio \n"
            + "FROM incidencia p\n"
            + "RIGHT JOIN bodega_tienda b\n"
            + "ON  p.codigo_tienda = b.codigo_tienda WHERE b.codigo_usuario_bodega = ? AND p.estado = ?";
    /**
     * Lista las incidencias por usuario_bodega, por tienda y en un intervalo de
     * tiempo
     */
    private final static String SELECT_BY_USUARIO_BODEGA_BY_ESTADO_BY_TIENDA_BY_FECHA
            = "SELECT p.id, p.fecha, p.estado, p.usuario_tienda, p.codigo_tienda, p.id_envio \n"
            + "FROM incidencia p\n"
            + "RIGHT JOIN bodega_tienda b\n"
            + "ON  p.codigo_tienda = b.codigo_tienda "
            + "WHERE b.codigo_usuario_bodega = ? "
            + "AND p.estado = ? "
            + "AND p.codigo_tienda = ? "
            + "AND fecha BETWEEN ? AND ?";

    private final static String SELECT_BY_USUARIO_BODEGA
            = "SELECT p.id, p.fecha, p.estado, p.usuario_tienda, p.codigo_tienda, p.id_envio \n"
            + "FROM incidencia p\n"
            + "RIGHT JOIN bodega_tienda b\n"
            + "ON  p.codigo_tienda = b.codigo_tienda WHERE b.codigo_usuario_bodega = ?";
    /**
     *
     */
    private final static String SELECT_BY_USUARIO_BODEGA_BY_TIENDA
            = "SELECT p.id, p.fecha, p.estado, p.usuario_tienda, p.codigo_tienda, p.id_envio \n"
            + "FROM incidencia p\n"
            + "RIGHT JOIN bodega_tienda b\n"
            + "ON  p.codigo_tienda = b.codigo_tienda \n"
            + "WHERE b.codigo_usuario_bodega = ? AND p.codigo_tienda = ?";

    private final static String INCIDENCIAS_BY_TIENDA_BY_ESTADO_FECHA
            = "SELECT * FROM incidencia WHERE estado = ? AND codigo_tienda = ? AND fecha BETWEEN ? AND ?";

    private final static String INCIDENCIAS_BY_TIENDA = "SELECT * FROM incidencia WHERE codigo_tienda = ?";

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
            statement.setInt(5, incidendcia.getIdEnvio());
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
            statement.setInt(6, incidendcia.getIdEnvio());
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
     * LISTA LAS INCIDENCIAS A CARGO DE UN USUARIO_BODEGA POR ESTADO
     *
     * @param usuarioBodega
     * @param estado
     * @return
     */
    public ArrayList<Incidencia> getIncidencia(String usuarioBodega, String estado) {
        List<Incidencia> incidencias = new ArrayList<>();
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(SELECT_BY_USUARIO_BODEGA_BY_ESTADO)) {
            statement.setString(1, usuarioBodega);
            statement.setString(2, estado);
            resultSet = statement.executeQuery();
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
     * Lista las incidencias por usuario_bodega, estado, tienda y en un
     * intervalo de tiempo
     *
     * @param usuarioBodega
     * @param estado
     * @param codigoTienda
     * @param fecha1
     * @param fecha2
     * @return
     */
    public ArrayList<Incidencia> getIncidencias(String usuarioBodega, String estado, String codigoTienda, String fecha1, String fecha2) {
        List<Incidencia> incidencias = new ArrayList<>();
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(SELECT_BY_USUARIO_BODEGA_BY_ESTADO_BY_TIENDA_BY_FECHA)) {
            statement.setString(1, usuarioBodega);
            statement.setString(2, estado);
            statement.setString(3, codigoTienda);
            statement.setString(4, fecha1);
            statement.setString(5, fecha2);

            resultSet = statement.executeQuery();
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
            statement.setString(1, usuarioBodega);
            resultSet = statement.executeQuery();
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
            statement.setString(1, usuarioBodega);
            statement.setString(2, codigoTienda);
            resultSet = statement.executeQuery();
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
     * Lista las incidencias por tienda
     *
     * @param codigoTienda
     * @return lista las incidencias generadas por tiendas
     */
    public ArrayList<Incidencia> getIncidenciasByTienda(String codigoTienda) {
        List<Incidencia> incidencias = new ArrayList<>();
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(INCIDENCIAS_BY_TIENDA)) {
            statement.setString(1, codigoTienda);
            resultSet = statement.executeQuery();
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
     * Lista las incidencias por tienda, estado y en un intervalo de fechas.
     *
     * @param estado
     * @param codigoTienda
     * @param fecha1
     * @param fecha2
     * @return
     */
    public ArrayList<Incidencia> getIncidenciasByTienda(String estado, String codigoTienda, String fecha1, String fecha2) {
        List<Incidencia> incidencias = new ArrayList<>();
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(INCIDENCIAS_BY_TIENDA_BY_ESTADO_FECHA)) {
            statement.setString(1, estado);
            statement.setString(2, codigoTienda);
            statement.setString(3, fecha1);
            statement.setString(4, fecha2);
            resultSet = statement.executeQuery();
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
                resultSet.getString("codigo_tienda"),
                resultSet.getInt("id_envio"));
    }
}
