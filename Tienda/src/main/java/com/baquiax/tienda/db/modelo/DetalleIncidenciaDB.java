/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.db.modelo;

import com.baquiax.tienda.db.coneccion.ConeccionDB;
import com.baquiax.tienda.entidad.DetalleIncidencia;
import com.baquiax.tienda.entidad.Incidencia;
import java.sql.*;
import java.util.*;

/**
 *
 * @author luis
 */
public class DetalleIncidenciaDB {

    private final static String INSERT = "INSERT INTO detalle_incidencia(id_incidencia,codigo_producto,cantidad,motivo) VALUES(?,?,?,?)";
    private final static String SELECT_BY_INCIDENCIA = "SELECT * FROM detalle_incidencia WHERE id_incidencia = ?";

    private ResultSet resultSet;

    public DetalleIncidenciaDB() {
    }

    /**
     * Insert new Detalle of Incidencia
     *
     * @param detalleIncidencia
     * @return
     */
    public boolean insert(DetalleIncidencia detalleIncidencia) {
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(INSERT)) {
            statement.setInt(1, detalleIncidencia.getIdIncidencia());
            statement.setString(2, detalleIncidencia.getCodigoProducto());
            statement.setInt(3, detalleIncidencia.getCantidad());
            statement.setString(4, detalleIncidencia.getMotivo());
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Lista el detalle de una incidencia
     *
     * @param incidencia
     * @return
     */
    public ArrayList<DetalleIncidencia> getDetalle(Incidencia incidencia) {
        List<DetalleIncidencia> detalle = new ArrayList<>();
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(SELECT_BY_INCIDENCIA)) {
            statement.setInt(1, incidencia.getId());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                detalle.add(getDetalle(resultSet));
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return (ArrayList<DetalleIncidencia>) detalle;
    }

    private DetalleIncidencia getDetalle(ResultSet resultSet) throws SQLException {
        return new DetalleIncidencia(
                resultSet.getInt("id_incidencia"),
                resultSet.getString("codigo_producto"),
                resultSet.getInt("cantidad"),
                resultSet.getString("motivo"));
    }
}
