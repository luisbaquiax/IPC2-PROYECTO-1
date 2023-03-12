/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.db.modelo.reporteSupervisor;

import com.baquiax.tienda.db.coneccion.ConeccionDB;
import com.baquiax.tienda.db.modelo.PedidoDB;
import com.baquiax.tienda.entidad.Pedido;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luis
 */
public class ReportePedidosSupervisorDB {

    private PedidoDB pedidoDB;
    private final static String REPORTE_PEDISO_BY_TIENDA_SUPERVISADA
            = "SELECT p.id, p.fecha, p.total, p.estado, p.usuario_tienda, p.codigo_tienda, p.codigo_supervisor\n"
            + "FROM pedido p\n"
            + "LEFT JOIN tienda t\n"
            + "ON p.codigo_tienda = t.codigo WHERE t.tipo = ? AND t.codigo = ?";

    private final static String REPORTE_BY_TIENDA_FECHA
            = "SELECT p.id, p.fecha, p.total, p.estado, p.usuario_tienda, p.codigo_tienda, p.codigo_supervisor\n"
            + "FROM pedido p\n"
            + "LEFT JOIN tienda t\n"
            + "ON p.codigo_tienda = t.codigo WHERE t.tipo = AND t.codigo = ? AND fecha BETWEEN ? AND ?";

    private final static String REPORTE_BY_FECHA
            = "SELECT p.id, p.fecha, p.total, p.estado, p.usuario_tienda, p.codigo_tienda, p.codigo_supervisor\n"
            + "FROM pedido p\n"
            + "LEFT JOIN tienda t\n"
            + "ON p.codigo_tienda = t.codigo WHERE t.tipo = ? AND fecha BETWEEN ? AND ?";

    private final static String REPORTE_BY_TIENDA_FECHA_BY_ESTADO
            = "SELECT p.id, p.fecha, p.total, p.estado, p.usuario_tienda, p.codigo_tienda, p.codigo_supervisor\n"
            + "FROM pedido p\n"
            + "LEFT JOIN tienda t\n"
            + "ON p.codigo_tienda = t.codigo WHERE t.tipo = ? AND t.codigo = ? AND p.estado = ? AND fecha BETWEEN ? AND ?";

    private final static String REPORTE_BY_FECHA_BY_ESTADO
            = "SELECT p.id, p.fecha, p.total, p.estado, p.usuario_tienda, p.codigo_tienda, p.codigo_supervisor\n"
            + "FROM pedido p\n"
            + "LEFT JOIN tienda t\n"
            + "ON p.codigo_tienda = t.codigo WHERE t.tipo = ? AND p.estado = ? AND fecha BETWEEN ? AND ?";

    private final static String REPORTE_PEDIDOS
            = "SELECT p.id, p.fecha, p.total, p.estado, p.usuario_tienda, p.codigo_tienda, p.codigo_supervisor\n"
            + "FROM pedido p\n"
            + "LEFT JOIN tienda t\n"
            + "ON p.codigo_tienda = t.codigo WHERE t.tipo = ?";

    private ResultSet resultSet;

    public ReportePedidosSupervisorDB() {
        this.pedidoDB = new PedidoDB();
    }

    public ArrayList<Pedido> getPedidos(String tipoTienda) {
        List<Pedido> pedidos = new ArrayList<>();
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(REPORTE_PEDIDOS)) {
            statement.setString(1, tipoTienda);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                pedidos.add(this.pedidoDB.getPedido(resultSet));
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return (ArrayList<Pedido>) pedidos;
    }

    /**
     * Lista los pedidos de una tienda supervisada
     * @param tipoTienda
     * @param codigoTienda
     * @return
     */
    public ArrayList<Pedido> getPedidos(String tipoTienda, String codigoTienda) {
        List<Pedido> pedidos = new ArrayList<>();
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(REPORTE_PEDISO_BY_TIENDA_SUPERVISADA)) {
            statement.setString(1, tipoTienda);
            statement.setString(2, codigoTienda);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                pedidos.add(this.pedidoDB.getPedido(resultSet));
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return (ArrayList<Pedido>) pedidos;
    }

    public ArrayList<Pedido> getPedidos(String tipoTienda, String estado, String fecha1, String fecha2) {
        List<Pedido> pedidos = new ArrayList<>();
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(REPORTE_BY_FECHA_BY_ESTADO)) {
            statement.setString(1, tipoTienda);
            statement.setString(2, estado);
            statement.setString(3, fecha1);
            statement.setString(4, fecha2);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                pedidos.add(this.pedidoDB.getPedido(resultSet));
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return (ArrayList<Pedido>) pedidos;
    }

    public ArrayList<Pedido> getPedidos(String tipoTienda, String fecha1, String fecha2) {
        List<Pedido> pedidos = new ArrayList<>();
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(REPORTE_BY_FECHA)) {
            statement.setString(1, tipoTienda);
            statement.setString(2, fecha1);
            statement.setString(3, fecha2);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                pedidos.add(this.pedidoDB.getPedido(resultSet));
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return (ArrayList<Pedido>) pedidos;
    }

    public ArrayList<Pedido> getPedidosTienda(String tipoTienda, String tienda, String fecha1, String fecha2) {
        List<Pedido> pedidos = new ArrayList<>();
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(REPORTE_BY_TIENDA_FECHA)) {
            statement.setString(1, tipoTienda);
            statement.setString(2, tienda);
            statement.setString(3, fecha1);
            statement.setString(4, fecha2);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                pedidos.add(this.pedidoDB.getPedido(resultSet));
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return (ArrayList<Pedido>) pedidos;
    }

    public ArrayList<Pedido> getPedidosTienda(String tipoTienda, String tienda, String estado, String fecha1, String fecha2) {
        List<Pedido> pedidos = new ArrayList<>();
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(REPORTE_BY_TIENDA_FECHA_BY_ESTADO)) {
            statement.setString(1, tipoTienda);
            statement.setString(2, tienda);
            statement.setString(3, estado);
            statement.setString(4, fecha1);
            statement.setString(5, fecha2);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                pedidos.add(this.pedidoDB.getPedido(resultSet));
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
     * @param tipoTienda
     * @return
     */
    public ArrayList<Pedido> getPedidosTienda(String tipoTienda) {
        List<Pedido> pedidos = new ArrayList<>();
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(REPORTE_PEDIDOS)) {
            statement.setString(1, tipoTienda);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                pedidos.add(this.pedidoDB.getPedido(resultSet));
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return (ArrayList<Pedido>) pedidos;
    }

}
