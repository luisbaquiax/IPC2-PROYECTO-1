/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.db.modelo;

import com.baquiax.tienda.db.coneccion.ConeccionDB;
import com.baquiax.tienda.entidad.Pedido;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luis
 */
public class PedidoDB {

    private final static String INSERT = "INSERT INTO pedido(fecha,total,estado,usuario_tienda,codigo_tienda) VALUES(?,?,?,?,?)";
    private final static String INSERT_FROM_FILE = "INSERT INTO pedido(fecha,total,estado,usuario_tienda,codigo_tienda, id) VALUES(?,?,?,?,?,?)";
    private final static String UPDATE = "UPDATE pedido SET estado = ? WHERE id = ?";

    private final static String PEDIDO_BY_USUARIO_BODEGA_BY_ESTADO
            = "SELECT p.id, p.fecha, p.total, p.estado, p.usuario_tienda, p.codigo_tienda \n"
            + "FROM pedido p\n"
            + "RIGHT JOIN bodega_tienda b\n"
            + "ON  p.codigo_tienda = b.codigo_tienda WHERE b.codigo_usuario_bodega = ? AND p.estado = ?";
    private final static String PEDIDO_BY_USUARIO_BODEGA
            = "SELECT p.id, p.fecha, p.total, p.estado, p.usuario_tienda, p.codigo_tienda \n"
            + "FROM pedido p\n"
            + "RIGHT JOIN bodega_tienda b\n"
            + "ON  p.codigo_tienda = b.codigo_tienda WHERE b.codigo_usuario_bodega = ?";
    private final static String PEDIDO_BY_USUARIO_BODEGA_BY_TIENDA
            = "SELECT p.id, p.fecha, p.total, p.estado, p.usuario_tienda, p.codigo_tienda \n"
            + "FROM pedido p\n"
            + "RIGHT JOIN bodega_tienda b\n"
            + "ON  p.codigo_tienda = b.codigo_tienda WHERE b.codigo_usuario_bodega = ? AND p.codigo_tienda = ?";

    private ResultSet resultSet;

    public PedidoDB() {
    }

    /**
     * Insert a new Pedido
     *
     * @param pedido
     * @return
     */
    public boolean insert(Pedido pedido) {
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(INSERT)) {
            statement.setString(1, pedido.getFecha());
            statement.setDouble(2, pedido.getTotal());
            statement.setString(3, pedido.getEstado());
            statement.setString(4, pedido.getUsuarioTienda());
            statement.setString(5, pedido.getCodigoTienda());
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Insert a new Pedido from file
     *
     * @param pedido
     * @return
     */
    public boolean insertFromFile(Pedido pedido) {
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(INSERT_FROM_FILE)) {
            statement.setString(1, pedido.getFecha());
            statement.setDouble(2, pedido.getTotal());
            statement.setString(3, pedido.getEstado());
            statement.setString(4, pedido.getUsuarioTienda());
            statement.setString(5, pedido.getCodigoTienda());
            statement.setInt(6, pedido.getId());
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Update a pedido
     *
     * @param pedido
     * @return
     */
    public boolean update(Pedido pedido) {
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(UPDATE)) {
            statement.setString(1, pedido.getEstado());
            statement.setInt(2, pedido.getId());
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * LISTA LOS PEDIDOS QUE LE LLEAGARAN (A CARGO) A UN USUARIO BODEGA
     *
     * @param usuarioBodega
     * @return
     */
    public ArrayList<Pedido> getPedidosByUsuarioBodega(String usuarioBodega) {
        List<Pedido> pedidos = new ArrayList<>();
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(PEDIDO_BY_USUARIO_BODEGA)) {
            statement.setString(1, usuarioBodega);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                pedidos.add(getPedido(resultSet));
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return (ArrayList<Pedido>) pedidos;
    }

    /**
     * LISTA LOS PEDIDOS QUE LE LLEAGARAN (A CARGO) A UN USUARIO BODEGA
     *
     * @param usuarioBodega
     * @param estado
     * @return
     */
    public ArrayList<Pedido> getPedidosByUsuarioBodegaByEstado(String usuarioBodega, String estado) {
        List<Pedido> pedidos = new ArrayList<>();
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(PEDIDO_BY_USUARIO_BODEGA_BY_ESTADO)) {
            statement.setString(1, usuarioBodega);
            statement.setString(2, estado);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                pedidos.add(getPedido(resultSet));
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return (ArrayList<Pedido>) pedidos;
    }

    /**
     * LISTA LOS PEDIDOS QUE LE LLEAGARAN (A CARGO) A UN USUARIO BODEGA
     * FILTRANDO POR TIENDA
     *
     * @param usuarioBodega
     * @param codigoTienda
     * @return
     */
    public ArrayList<Pedido> getPedidosByTiendaAndUsuario(String usuarioBodega, String codigoTienda) {
        List<Pedido> pedidos = new ArrayList<>();
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(PEDIDO_BY_USUARIO_BODEGA_BY_TIENDA)) {
            statement.setString(1, usuarioBodega);
            statement.setString(2, codigoTienda);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                pedidos.add(getPedido(resultSet));
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return (ArrayList<Pedido>) pedidos;
    }

    private Pedido getPedido(ResultSet resultSet) throws SQLException {
        return new Pedido(
                resultSet.getInt("id"),
                resultSet.getString("fecha"),
                resultSet.getDouble("total"),
                resultSet.getString("estado"),
                resultSet.getString("usuario_tienda"),
                resultSet.getString("codigo_tienda"));
    }
}
