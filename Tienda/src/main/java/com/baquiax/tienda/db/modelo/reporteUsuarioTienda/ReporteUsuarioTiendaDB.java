/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.baquiax.tienda.db.modelo.reporteUsuarioTienda;

import com.baquiax.tienda.db.coneccion.ConeccionDB;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Luis
 */
public class ReporteUsuarioTiendaDB {

    /**
     * Numero de incidencias activas
     */
    public static final String CANTIDAD_DEVOLUCIONES_ACTIVAS
            = "SELECT COUNT(estado) AS cantidad\n"
            + "FROM devolucion\n"
            + "WHERE codigo_tienda = ? AND estado = ?";
    /**
     * Número de devoluciones activas
     */
    public static final String CANTIDAD_INCIDENCIAS_ACTIVAS
            = "SELECT COUNT(estado) AS cantidad\n"
            + "FROM incidencia\n"
            + "WHERE codigo_tienda = ? AND estado = ?";

    private ResultSet resultSet;

    public ReporteUsuarioTiendaDB() {
    }

    /**
     * Servirá para encontrar la cantidad de incidencias o devoluciones para una
     * tienda
     *
     * @param sql
     * @param codigoTienda
     * @param estado
     * @return
     */
    public int cantidad(String sql, String codigoTienda, String estado) {
        int nCantidad = -1;
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(sql)) {
            statement.setString(1, codigoTienda);
            statement.setString(2, estado);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                nCantidad = resultSet.getInt("cantidad");
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
        }
        return nCantidad;
    }
}
