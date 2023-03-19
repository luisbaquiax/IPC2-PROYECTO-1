/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.db.modelo.reporteAdministracion;

import com.baquiax.tienda.db.coneccion.ConeccionDB;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luis
 */
public class ReporteAdministracionDB {

    public final static String NUMERO_PEDIDOS = "numero_pedidos";
    public final static String CODIGO_TIENDA = "codigo_tienda";

    /**
     * Reporte de las tiendas con más pedidos
     */
    public final static String REPORTE_TIENDAS_PEDIDOS
            = "SELECT COUNT(id) AS numero_pedidos , codigo_tienda\n"
            + "FROM pedido\n"
            + "GROUP BY codigo_tienda\n"
            + "ORDER BY COUNT(id) DESC Limit 5";
    /**
     * Reporte de las 5 tiendas con más pedidos en un intervalo de tiempo por
     * estado.
     */
    public final static String REPORTE_TIENDAS_PEDIDOS_BY_ESTADO_BETWEEN_FECHA
            = "SELECT COUNT(id) AS numero_pedidos, codigo_tienda\n"
            + "FROM pedido p\n"
            + "WHERE p.estado = ? AND fecha BETWEEN ? AND ?\n"
            + "GROUP BY codigo_tienda\n"
            + "HAVING COUNT(id) LIMIT 5";

    public final static String NUMERO_ENVIOS = "numero_envios";
    public final static String USUARIO_BODEGA = "usuario_bodega";

    /**
     * Reporte de los 5 usuarios con más envíos generados
     */
    public final static String REPORTE_USUARIOS_ENVIOS_GENERADOS
            = "SELECT COUNT(id) AS numero_envios , usuario_bodega\n"
            + "FROM envio\n"
            + "GROUP BY usuario_bodega\n"
            + "ORDER BY COUNT(id) DESC Limit 5";
    /**
     * Reporte de los 5 usuarios con más envíos generados en un intervalo de
     * tiempo.
     */
    public final static String REPORTE_USUARIOS_ENVIOS_GENERADOS_BETWEEN_FEHCA
            = "SELECT COUNT(id) AS numero_envios , usuario_bodega\n"
            + "FROM envio e\n"
            + "WHERE e.fecha_salida BETWEEN ? AND ?\n"
            + "GROUP BY usuario_bodega\n"
            + "ORDER BY COUNT(id) DESC Limit 5;";

    public final static String USUARIO_TIENDA = "usuario_tienda";

    /**
     * Reporte de los 5 usuarios con más pedidos generadoS
     */
    public final static String REPORTE_USUARIOS_PEDIDOS_GENERADOS
            = "SELECT COUNT(id) AS numero_pedidos , usuario_tienda\n"
            + "FROM pedido\n"
            + "GROUP BY usuario_tienda\n"
            + "ORDER BY COUNT(id) DESC Limit 5;";
    /**
     * Reporte de los 5 usuarios con más pedidos generados en un intervalo de
     * tiempo.
     */
    public final static String REPORTE_USUARIOS_PEDIDOS_GENERADO_BETWEEN_FECHA
            = "SELECT COUNT(id) AS numero_pedidos , usuario_tienda\n"
            + "FROM pedido p\n"
            + "WHERE p.fecha BETWEEN ? AND ?\n"
            + "GROUP BY usuario_tienda\n"
            + "ORDER BY COUNT(id) DESC Limit 5;";

    /**
     * Reporte de productos más solicitados por las tiendas
     */
    public final String REPORTE_PRODUCTOS_MAS_PEDIDOS = "";
    /**
     *
     * Reporte de productos más solicitados por las tiendas en un intervalo de
     * tiempo.
     */
    private final String REPORTE_PRODUCTOS_MAS_PEDIDOS_BETWEEN_FECHA = "";

    private ResultSet resultSet;

    public ReporteAdministracionDB() {
    }

    public List<String[]> getReporteAdministracion(String query, String nameColumna1, String nameColumna2) {
        List<String[]> datos = new ArrayList<>();

        try (PreparedStatement statement = ConeccionDB.getConnection().prepareCall(query)) {
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                datos.add(getReporte(resultSet, nameColumna1, nameColumna2));
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Ocurio un error: " + e.getMessage());
        }
        return datos;
    }

    public List<String[]> getReporteAdministracion(String query, String nameColumna1, String nameColumna2, String fecha1, String fecha2) {
        List<String[]> datos = new ArrayList<>();

        try (PreparedStatement statement = ConeccionDB.getConnection().prepareCall(query)) {
            statement.setString(1, fecha1);
            statement.setString(2, fecha2);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                datos.add(getReporte(resultSet, nameColumna1, nameColumna2));
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Ocurio un error: " + e.getMessage());
        }
        return datos;
    }

    public List<String[]> getReporteAdministracion(String query, String nameColumna1, String nameColumna2, String fecha1, String fecha2, String estado) {
        List<String[]> datos = new ArrayList<>();

        try (PreparedStatement statement = ConeccionDB.getConnection().prepareCall(query)) {
            statement.setString(1, estado);
            statement.setString(2, fecha1);
            statement.setString(3, fecha2);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                datos.add(getReporte(resultSet, nameColumna1, nameColumna2));
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Ocurio un error: " + e.getMessage());
        }
        return datos;
    }

    private String[] getReporte(ResultSet resultSet, String parametro1, String parametro2) throws SQLException {
        String[] datos = new String[2];
        datos[0] = resultSet.getString(parametro1);
        datos[1] = resultSet.getString(parametro2);
        return datos;
    }
}
