/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.db.modelo;

import com.baquiax.tienda.db.coneccion.ConeccionDB;
import com.baquiax.tienda.entidad.BodegaTienda;
import java.sql.*;

/**
 *
 * @author luis
 */
public class BodegaTiendaDB {

    private final static String INSERT = "INSERT INTO bodega_tienda(codigo_usuario_bodega,codigo_tienda) VALUES(?,?)";
    private final static String SELECT = "SELECT * FROM bodega_tienda";

    public BodegaTiendaDB() {
    }

    /**
     *
     * @param bodegaTienda
     * @return
     */
    public boolean insert(BodegaTienda bodegaTienda) {
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(INSERT)) {
            statement.setString(1, bodegaTienda.getCodigoBedeguero());
            statement.setString(2, bodegaTienda.getCodigoTienda());
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
