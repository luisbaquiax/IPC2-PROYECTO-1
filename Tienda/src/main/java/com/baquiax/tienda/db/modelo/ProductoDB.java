/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.db.modelo;

import com.baquiax.tienda.db.coneccion.ConeccionDB;
import com.baquiax.tienda.entidad.Producto;
import com.baquiax.tienda.entidad.Tienda;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luis
 */
public class ProductoDB {

    private final static String INSERT = "INSERT INTO producto(codigo,nombre,costo,precio,existencia) VALUES(?,?,?,?,?)";
    private final static String UPDATE = "UPDATE producto SET nombre = ?, costo = ?, precio = ?, existencia = ? WHERE codigo = ?";
    private final static String SERCHA_BY_CODIGO = "SELECT * FROM producto WHERE codigo = ?";
    /**
     * Productos en bodega
     */
    private final static String SELECT = "SELECT * FROM producto";

    /**
     * Productos por tienda
     */
    private final static String PRODUCTO_BY_TIENDA
            = "SELECT p.codigo, p.nombre, p.precio, p.costo, c.existencia\n"
            + "FROM producto p\n"
            + "RIGHT JOIN catalogo c\n"
            + "ON  p.codigo = c.codigo_producto WHERE c.codigo_tienda = ?";

    /**
     * Productos que tienen existencia menores a una cantidad
     */
    private final static String PRODUCTOS_CON_EXISTENCIA_MENOR_A
            = "SELECT p.codigo, p.nombre, p.costo, p.precio, c.existencia FROM  producto p\n"
            + "RIGHT JOIN catalogo c\n"
            + "ON p.codigo = c.codigo_producto\n"
            + "WHERE  c.codigo_tienda = ? AND c.existencia < ?";
    private ResultSet resultSet;

    public ProductoDB() {

    }

    /**
     *
     * @param producto
     * @return
     */
    public boolean insert(Producto producto) {
        try (PreparedStatement stamtment = ConeccionDB.getConnection().prepareStatement(INSERT)) {

            stamtment.setString(1, producto.getCodigo());
            stamtment.setString(2, producto.getNombre());
            stamtment.setDouble(3, producto.getCosto());
            stamtment.setDouble(4, producto.getPrecio());
            stamtment.setInt(5, producto.getExistencia());
            stamtment.executeUpdate();

            stamtment.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    /**
     *
     * @param producto
     * @return
     */
    public boolean update(Producto producto) {
        try (PreparedStatement stamtment = ConeccionDB.getConnection().prepareStatement(UPDATE)) {

            stamtment.setString(1, producto.getNombre());
            stamtment.setDouble(2, producto.getCosto());
            stamtment.setDouble(3, producto.getPrecio());
            stamtment.setInt(4, producto.getExistencia());
            stamtment.setString(5, producto.getCodigo());
            stamtment.executeUpdate();

            stamtment.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    /**
     * LISTA DE PRODUCTOS EN BODEGA
     *
     * @return
     */
    public ArrayList<Producto> getProductos() {
        List<Producto> productos = new ArrayList<>();
        try (PreparedStatement stamtment = ConeccionDB.getConnection().prepareStatement(SELECT)) {
            resultSet = stamtment.executeQuery();

            while (resultSet.next()) {
                productos.add(getProducto(resultSet));
            }
            resultSet.close();
            stamtment.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return (ArrayList<Producto>) productos;
    }

    /**
     * query:
     * <br>SELECT p.codigo, p.nombre, p.precio, p.costo, c.existencia\n" +
     * <br>"FROM producto p\n RIGHT JOIN catalogo c\n" + "ON p.codigo =
     * <br>c.codigo_producto WHERE c.codigo_tienda = ?
     *
     * @param tienda
     * @return LISTA LOS PRODUCTOS DE UN TIENDA EN ESPECÍFICO (CATÁLOGO)
     */
    public ArrayList<Producto> getProductoByTienda(Tienda tienda) {
        List<Producto> productos = new ArrayList<>();
        try (PreparedStatement stamtment = ConeccionDB.getConnection().prepareStatement(PRODUCTO_BY_TIENDA)) {
            stamtment.setString(1, tienda.getCodigo());
            resultSet = stamtment.executeQuery();

            while (resultSet.next()) {
                productos.add(getProducto(resultSet));
            }
            resultSet.close();
            stamtment.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return (ArrayList<Producto>) productos;
    }

    /**
     *
     * @param tienda
     * @param existencia
     * @return Lista de productos de una tienda con existencia con menor a una
     * cantidad existencia
     */
    public ArrayList<Producto> getProductoByTienda(Tienda tienda, int existencia) {
        List<Producto> productos = new ArrayList<>();
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(PRODUCTOS_CON_EXISTENCIA_MENOR_A)) {
            statement.setString(1, tienda.getCodigo());
            statement.setInt(2, existencia);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                productos.add(getProducto(resultSet));
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return (ArrayList<Producto>) productos;
    }

    private Producto getProducto(ResultSet resultSet) throws SQLException {
        return new Producto(
                resultSet.getString("codigo"),
                resultSet.getString("nombre"),
                resultSet.getDouble("costo"),
                resultSet.getDouble("precio"),
                resultSet.getInt("existencia"));
    }

}
