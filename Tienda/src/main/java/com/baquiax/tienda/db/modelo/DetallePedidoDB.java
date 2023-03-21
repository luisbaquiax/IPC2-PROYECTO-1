/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.db.modelo;

import com.baquiax.tienda.db.coneccion.ConeccionDB;
import com.baquiax.tienda.entidad.DetallePedido;
import com.baquiax.tienda.entidad.Pedido;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luis
 */
public class DetallePedidoDB {

    private final static String INSERT = "INSERT INTO detalle_pedido(id_pedido,codigo_producto,cantidad,precio_unitario) VALUES(?,?,?,?)";
    private final static String UPDATE = "UPDATE detalle_pedido SET cantidad = ? WHERE id_pedido = ? AND codigo_producto = ?";
    private final static String DELETE = "DELETE FROM detalle_pedido WHERE id_pedido = ? AND codigo_producto = ?";
    private final static String SELECT = "SELECT * FROM detalle_pedido WHERE id_pedido = ?";

    private ResultSet resultSet;

    /**
     *
     * @param detallePedido
     * @return
     */
    public boolean insert(DetallePedido detallePedido) {
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(INSERT)) {
            statement.setInt(1, detallePedido.getIdPedido());
            statement.setString(2, detallePedido.getCodigoProducto());
            statement.setInt(3, detallePedido.getCantidad());
            statement.setDouble(4, detallePedido.getPrecioUnitario());
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Modifica un detalle de un pedido
     *
     * @param detallePedido
     * @return
     */
    public boolean update(DetallePedido detallePedido) {
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(UPDATE)) {
            statement.setInt(1, detallePedido.getCantidad());
            statement.setInt(2, detallePedido.getIdPedido());
            statement.setString(3, detallePedido.getCodigoProducto());
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Borra un detalle un pedido
     *
     * @param detallePedido
     * @return
     */
    public boolean delete(DetallePedido detallePedido) {
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(DELETE)) {
            statement.setInt(1, detallePedido.getIdPedido());
            statement.setString(2, detallePedido.getCodigoProducto());
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * LISTA EL DETALLE DE UN PEDIDO
     *
     * @param pedido
     * @return
     */
    public List<DetallePedido> getDetallePedidoByIdPedido(Pedido pedido) {
        List<DetallePedido> detalle = new ArrayList<>();
        try (PreparedStatement statemente = ConeccionDB.getConnection().prepareStatement(SELECT)) {
            statemente.setInt(1, pedido.getId());
            resultSet = statemente.executeQuery();
            while (resultSet.next()) {
                detalle.add(getDetalle(resultSet));
            }
            resultSet.close();
            statemente.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return detalle;
    }

    private DetallePedido getDetalle(ResultSet resultSet) throws SQLException {
        return new DetallePedido(
                resultSet.getInt("id_pedido"),
                resultSet.getString("codigo_producto"),
                resultSet.getInt("cantidad"),
                resultSet.getDouble("precio_unitario"),
                (resultSet.getInt("cantidad") * resultSet.getDouble("precio_unitario")));
    }
}
