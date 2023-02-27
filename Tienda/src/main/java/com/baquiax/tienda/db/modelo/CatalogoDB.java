/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.db.modelo;

import com.baquiax.tienda.db.coneccion.ConeccionDB;
import com.baquiax.tienda.entidad.Catalogo;
import java.sql.*;

/**
 *
 * @author luis
 */
public class CatalogoDB {

    private final static String INSERT = "INSERT INTO catalogo(codigo_tienda,codigo_producto,existencia) VALUES(?,?,?)";
    private final static String UPDATE = "UPDATE catalogo SET existencia = ? WHERE codigo_tienda = ? AND codigo_producto = ?";

    public CatalogoDB() {
    }

    /**
     *
     * @param productos
     * @return
     */
    public boolean insert(Catalogo productos) {

        try (PreparedStatement statemnt = ConeccionDB.getConnection().prepareStatement(INSERT)) {
            statemnt.setString(1, productos.getCodigoTienda());
            statemnt.setString(2, productos.getCodigoProducto());
            statemnt.setInt(3, productos.getExistencia());

            statemnt.executeUpdate();
            statemnt.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     *
     * @param productos
     * @return
     */
    public boolean update(Catalogo productos) {

        try (PreparedStatement statemnt = ConeccionDB.getConnection().prepareStatement(UPDATE)) {
            statemnt.setInt(1, productos.getExistencia());
            statemnt.setString(2, productos.getCodigoTienda());
            statemnt.setString(3, productos.getCodigoProducto());

            statemnt.executeUpdate();
            statemnt.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
