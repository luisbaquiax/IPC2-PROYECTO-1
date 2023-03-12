/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.db.modelo;

import com.baquiax.tienda.db.coneccion.ConeccionDB;
import com.baquiax.tienda.entidad.Envio;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luis
 */
public class EnvioDB {

    private final static String INSERT = "INSERT INTO envio(fecha_salida,fecha_llegada,total,estado,codigo_tienda,usuario_bodega) VALUES(?,?,?,?,?,?)";
    private final static String INSERT_FROM_FILE = "INSERT INTO envio(id,fecha_salida,fecha_llegada,total,estado,codigo_tienda,usuario_bodega) VALUES(?,?,?,?,?,?,?)";
    private final static String UPDATE = "UPDATE envio SET fecha_salida = ?,fecha_llegada = ?, total = ?, estado = ? WHERE id = ?";
    private final static String SELECT_BY_TIENDA_BY_ESTADO = "SELECT * FROM envio WHERE codigo_tienda = ? AND estado = ?";

    private final static String SELECT_BY_TIENDA_BY_ID = "SELECT * FROM envio WHERE codigo_tienda = ? AND id = ?";
    private final static String ULTIMO
            = "SELECT id FROM envio ORDER BY id DESC LIMIT 1";

    private ResultSet resultSet;

    public EnvioDB() {
    }

    /**
     *
     * @param envio
     * @return
     */
    public boolean inset(Envio envio) {
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(INSERT)) {
            statement.setString(1, envio.getFechaSalida());
            statement.setString(2, envio.getFechaLlegada());
            statement.setDouble(3, envio.getTotal());
            statement.setString(4, envio.getEstado());
            statement.setString(5, envio.getCodigoTienda());
            statement.setString(6, envio.getUsuarioBodega());
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     *
     * @param envio
     * @return
     */
    public boolean insetFromFile(Envio envio) {
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(INSERT_FROM_FILE)) {
            statement.setInt(1, envio.getId());
            statement.setString(2, envio.getFechaSalida());
            statement.setString(3, envio.getFechaLlegada());
            statement.setDouble(4, envio.getTotal());
            statement.setString(5, envio.getEstado());
            statement.setString(6, envio.getCodigoTienda());
            statement.setString(7, envio.getUsuarioBodega());
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     *
     * @param envio
     * @return
     */
    public boolean update(Envio envio) {
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(UPDATE)) {
            statement.setString(1, envio.getFechaSalida());
            statement.setString(2, envio.getFechaLlegada());
            statement.setDouble(3, envio.getTotal());
            statement.setString(4, envio.getEstado());
            statement.setInt(5, envio.getId());
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * LISTA LOS ENVIOS QUE LLEGARAN A UNA TIENDA EN ESPECIFICO
     *
     * @param codigoTienda
     * @param estado
     * @return
     */
    public List<Envio> getEnvios(String codigoTienda, String estado) {
        List<Envio> envios = new ArrayList<>();
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(SELECT_BY_TIENDA_BY_ESTADO)) {
            statement.setString(1, codigoTienda);
            statement.setString(2, estado);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                envios.add(getEnvio(resultSet));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return envios;
    }

    /**
     * Servirá para encontrar un envio mediante su id
     *
     * @param codigoTienda
     * @param idEnvio
     * @return
     */
    public List<Envio> getEnvios(String codigoTienda, int idEnvio) {
        List<Envio> envios = new ArrayList<>();
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(SELECT_BY_TIENDA_BY_ID)) {
            statement.setString(1, codigoTienda);
            statement.setInt(2, idEnvio);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                envios.add(getEnvio(resultSet));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return envios;
    }

    public int getUltimoID() {
        int u = 0;
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(ULTIMO)) {
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                u = resultSet.getInt("id");
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return u;
    }

    private Envio getEnvio(ResultSet resultSet) throws SQLException {
        return new Envio(
                resultSet.getInt("id"),
                resultSet.getString("fecha_salida"),
                resultSet.getString("fecha_llegada"),
                resultSet.getDouble("total"),
                resultSet.getString("estado"),
                resultSet.getString("codigo_tienda"),
                resultSet.getString("usuario_bodega"));
    }
}
