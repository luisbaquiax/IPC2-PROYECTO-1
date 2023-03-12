/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.db.modelo;

import com.baquiax.tienda.db.coneccion.ConeccionDB;
import com.baquiax.tienda.entidad.DetalleDevolucion;
import com.baquiax.tienda.entidad.Devolucion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luis
 */
public class DetalleDevolucionDB {

    private final static String INSERT = "INSERT INTO detalle_devolucion(id_devolucion,codigo_producto,costo_unitario,cantidad,motivo) VALUES(?,?,?,?,?)";
    private final static String UPDATE = "UPDATE detalle_devolucion SET costo_unitario = ?, cantidad = ? WHERE id_devolucion = ? AND codigo_producto = ?";
    private final static String SELECT_BY_ID_DEVOLUCION = "SELECT * FROM detalle_devolucion WHERE id_devolucion = ?";

    private ResultSet resultSet;

    public DetalleDevolucionDB() {
    }

    /**
     * Insert a new DetalleDevolcion
     *
     * @param detalle
     * @return
     */
    public boolean inset(DetalleDevolucion detalle) {
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(INSERT)) {
            statement.setInt(1, detalle.getIdDevolucion());
            statement.setString(2, detalle.getCodigoProducto());
            statement.setDouble(3, detalle.getCostoUnitario());
            statement.setInt(4, detalle.getCantidad());
            statement.setString(5, detalle.getMotivo());
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Update a detalleDevolucion
     *
     * @param detalle
     * @return
     */
    public boolean update(DetalleDevolucion detalle) {
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(UPDATE)) {
            statement.setDouble(1, detalle.getCostoUnitario());
            statement.setInt(2, detalle.getCantidad());
            statement.setInt(3, detalle.getIdDevolucion());
            statement.setString(4, detalle.getCodigoProducto());
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Lista el detalle de una devolución
     *
     * @param devolucion
     * @return
     */
    public ArrayList<DetalleDevolucion> getDetalleDevolucion(Devolucion devolucion) {
        List<DetalleDevolucion> devolucions = new ArrayList<>();
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(SELECT_BY_ID_DEVOLUCION)) {
            statement.setInt(1, devolucion.getId());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                devolucions.add(getDetalleDevolucion(resultSet));
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return (ArrayList<DetalleDevolucion>) devolucions;
    }

    private DetalleDevolucion getDetalleDevolucion(ResultSet resultSet) throws SQLException {
        return new DetalleDevolucion(
                resultSet.getInt("id_devolucion"),
                resultSet.getString("codigo_producto"),
                resultSet.getDouble("costo_unitario"),
                resultSet.getInt("cantidad"),
                resultSet.getString("motivo"),
                (resultSet.getInt("cantidad") * resultSet.getDouble("costo_unitario")));

    }
}
