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
    /**
     * id_pedido del último ingresado
     */
    private final static String ULTIMO
            = "SELECT id FROM pedido ORDER BY id DESC LIMIT 1";
    /**
     * Pedidos por usuairo_bodega y por estado (SOLICITADO)
     */
    private final static String PEDIDO_BY_USUARIO_BODEGA_BY_ESTADO
            = "SELECT p.id, p.fecha, p.total, p.estado, p.usuario_tienda, p.codigo_tienda, p.codigo_supervisor \n"
            + "FROM pedido p\n"
            + "RIGHT JOIN bodega_tienda b\n"
            + "ON  p.codigo_tienda = b.codigo_tienda WHERE b.codigo_usuario_bodega = ? AND p.estado = ?";
    /**
     *
     */
    private final static String PEDIDO_BY_USUARIO_BODEGA
            = "SELECT p.id, p.fecha, p.total, p.estado, p.usuario_tienda, p.codigo_tienda, p.codigo_supervisor \n"
            + "FROM pedido p\n"
            + "RIGHT JOIN bodega_tienda b\n"
            + "ON  p.codigo_tienda = b.codigo_tienda WHERE b.codigo_usuario_bodega = ?";
    private final static String PEDIDO_BY_USUARIO_BODEGA_BY_TIENDA
            = "SELECT p.id, p.fecha, p.total, p.estado, p.usuario_tienda, p.codigo_tienda, p.codigo_supervisor \n"
            + "FROM pedido p\n"
            + "RIGHT JOIN bodega_tienda b\n"
            + "ON  p.codigo_tienda = b.codigo_tienda WHERE b.codigo_usuario_bodega = ? AND p.codigo_tienda = ? AND p.estado = ?";

    private final static String REPORTE_PEDIDO_TIENDA_FECHA
            = "SELECT * FROM pedido WHERE codigo_tienda = ? AND estado = ? AND fecha BETWEEN ? AND ?";
    private final static String REPORTE_PEDIDO_TIENDA
            = "SELECT * FROM pedido WHERE codigo_tienda = ?";

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
     * LISTA LOS PEDIDOS QUE LE LLEAGARAN (A CARGO) A UN USUARIO BODEGA por
     * estado
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
     * FILTRANDO POR TIENDA, y por estado del pedido.
     *
     * @param usuarioBodega
     * @param codigoTienda
     * @param estadoPedido
     * @return
     */
    public ArrayList<Pedido> getPedidosByTiendaAndUsuarioAndEstadoPedido(String usuarioBodega, String codigoTienda, String estadoPedido) {
        List<Pedido> pedidos = new ArrayList<>();
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(PEDIDO_BY_USUARIO_BODEGA_BY_TIENDA)) {
            statement.setString(1, usuarioBodega);
            statement.setString(2, codigoTienda);
            statement.setString(3, estadoPedido);
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
     *
     * @param codigoTienda
     * @param estado
     * @param fecha1
     * @param fecha2
     * @return Lista los pedidos por usuario tienda y por tienda, por estado y
     * en un intervalo de fecha
     */
    public ArrayList<Pedido> getPedidos(String codigoTienda, String estado, String fecha1, String fecha2) {
        List<Pedido> pedidos = new ArrayList<>();
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(REPORTE_PEDIDO_TIENDA_FECHA)) {
            statement.setString(1, codigoTienda);
            statement.setString(2, estado);
            statement.setString(3, fecha1);
            statement.setString(4, fecha2);
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
     *
     * @param codigoTienda
     * @return Lista los pedidos por por tienda
     */
    public ArrayList<Pedido> getPedidos(String codigoTienda) {
        List<Pedido> pedidos = new ArrayList<>();
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(REPORTE_PEDIDO_TIENDA)) {
            statement.setString(1, codigoTienda);
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

    public int getUltimo() {
        int i = 0;
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(ULTIMO)) {
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                i = resultSet.getInt("id");
            }
        } catch (Exception e) {
        }
        return i;
    }

    public Pedido getPedido(ResultSet resultSet) throws SQLException {
        return new Pedido(
                resultSet.getInt("id"),
                resultSet.getString("fecha"),
                resultSet.getDouble("total"),
                resultSet.getString("estado"),
                resultSet.getString("usuario_tienda"),
                resultSet.getString("codigo_tienda"),
                resultSet.getString("codigo_supervisor"));
    }
}
