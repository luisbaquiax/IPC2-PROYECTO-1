/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.db.modelo;

import com.baquiax.tienda.db.coneccion.ConeccionDB;
import com.baquiax.tienda.entidad.DetalleEnvio;
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
public class DetalleEnvioDB {

    private final static String INSERT = "INSERT INTO detalle_envio(id_envio,codigo_producto,cantidad,precio_unitario) VALUES(?,?,?,?)";
    private final static String SELECT_BY_ENVIO = "SELECT * FROM detalle_envio WHERE id_envio = ?";

    private ResultSet resultSet;

    public DetalleEnvioDB() {
    }

    /**
     *
     * @param detalle
     * @return
     */
    public boolean insert(DetalleEnvio detalle) {
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(INSERT)) {
            statement.setInt(1, detalle.getIdEnvio());
            statement.setString(2, detalle.getCodigoProducto());
            statement.setInt(3, detalle.getCantidad());
            statement.setDouble(4, detalle.getPrecioUnitario());
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Lista el detalle de un envio
     *
     * @param envio
     * @return
     */
    public ArrayList<DetalleEnvio> getDetalleByEnvio(Envio envio) {
        List<DetalleEnvio> detalle = new ArrayList<>();
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(SELECT_BY_ENVIO)) {
            statement.setInt(1, envio.getId());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                detalle.add(getDetalleEnvio(resultSet));
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return (ArrayList<DetalleEnvio>) detalle;
    }

    private DetalleEnvio getDetalleEnvio(ResultSet resultSet) throws SQLException {
        return new DetalleEnvio(
                resultSet.getInt("id_envio"),
                resultSet.getString("codigo_producto"),
                resultSet.getInt("cantidad"),
                resultSet.getDouble("precio_unitario"),
                (resultSet.getInt("cantidad") * resultSet.getDouble("precio_unitario")));
    }
}
