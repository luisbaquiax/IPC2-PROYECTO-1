/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.db.modelo;

import com.baquiax.tienda.db.coneccion.ConeccionDB;
import com.baquiax.tienda.entidad.Catalogo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luis
 */
public class CatalogoDB {

    private final static String INSERT = "INSERT INTO catalogo(codigo_tienda,codigo_producto,existencia) VALUES(?,?,?)";
    private final static String UPDATE = "UPDATE catalogo SET existencia = ? WHERE codigo_tienda = ? AND codigo_producto = ?";
    private final static String SELECT_BY_TIENDA = "SELECT * FROM catalogo WHERE codigo_tienda = ?";

    private ResultSet resultSet;

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
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Lista el catalogo por tienda
     *
     * @param codigoTienda
     * @return
     */
    public List<Catalogo> getCatalog(String codigoTienda) {
        List<Catalogo> catalogos = new ArrayList<>();
        try (PreparedStatement statemment = ConeccionDB.getConnection().prepareStatement(SELECT_BY_TIENDA)) {
            statemment.setString(1, codigoTienda);
            resultSet = statemment.executeQuery();
            while (resultSet.next()) {
                catalogos.add(
                        new Catalogo(
                                resultSet.getString("codigo_tienda"),
                                resultSet.getString("codigo_producto"),
                                resultSet.getInt("existencia")));
            }
            resultSet.close();
            statemment.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return catalogos;
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
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
        }
    }
}
