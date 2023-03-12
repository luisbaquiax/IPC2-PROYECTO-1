/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.db.modelo;

import com.baquiax.tienda.db.coneccion.ConeccionDB;
import com.baquiax.tienda.entidad.Devolucion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luis
 */
public class DevolucionDB {

    private final static String INSERT = "INSERT INTO devolucion(fecha,estado,total,usuario_tienda,codigo_tienda) VALUES(?,?,?,?,?)";
    private final static String INSERT_FROM_FILE = "INSERT INTO devolucion(id,fecha,estado,total,usuario_tienda,codigo_tienda) VALUES(?,?,?,?,?,?)";
    private final static String UPDATE = "UPDATE devolucion SET fecha = ?, estado = ? WHERE id = ?";

    private final static String SELECT_BY_USUARIO_AND_ESTADO
            = "SELECT p.id, p.fecha, p.estado, p.total, p.usuario_tienda, p.codigo_tienda \n"
            + "FROM devolucion p\n"
            + "RIGHT JOIN bodega_tienda b\n"
            + "ON  p.codigo_tienda = b.codigo_tienda \n"
            + "WHERE b.codigo_usuario_bodega = ? AND p.estado = ?";

    private final static String SELECT_BY_USUARIO_AND_ESTADO_AND_TIENDA
            = "SELECT p.id, p.fecha, p.estado, p.total, p.usuario_tienda, p.codigo_tienda \n"
            + "FROM devolucion p\n"
            + "RIGHT JOIN bodega_tienda b\n"
            + "ON  p.codigo_tienda = b.codigo_tienda \n"
            + "WHERE b.codigo_usuario_bodega = ? AND p.estado = ? AND p.codigo_tienda = ?";

    private final static String DEVOLUCIONES_BY_TIENDA
            = "SELECT * FROM devolucion WHERE codigo_tienda = ?";
    private final static String DEVOLUCIONES_BY_TIENDA_FECHA
            = "SELECT * FROM devolucion WHERE estado = ? AND codigo_tienda = ? AND fecha BETWEEN ? AND ?";

    private ResultSet resultSet;

    public DevolucionDB() {
    }

    /**
     *
     * @param devolucion
     * @return
     */
    public boolean inset(Devolucion devolucion) {
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(INSERT)) {
            statement.setString(1, devolucion.getFecha());
            statement.setString(2, devolucion.getEstado());
            statement.setDouble(3, devolucion.getTotal());
            statement.setString(4, devolucion.getUsuarioTienda());
            statement.setString(5, devolucion.getCodigoTienda());
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     *
     * @param devolucion
     * @return
     */
    public boolean insetFromFile(Devolucion devolucion) {
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(INSERT_FROM_FILE)) {
            statement.setInt(1, devolucion.getId());
            statement.setString(2, devolucion.getFecha());
            statement.setString(3, devolucion.getEstado());
            statement.setDouble(4, devolucion.getTotal());
            statement.setString(5, devolucion.getUsuarioTienda());
            statement.setString(6, devolucion.getCodigoTienda());
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     *
     * @param devolucion
     * @return
     */
    public boolean update(Devolucion devolucion) {
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(UPDATE)) {
            statement.setString(1, devolucion.getFecha());
            statement.setString(2, devolucion.getEstado());
            statement.setInt(3, devolucion.getId());
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Lista las devoluciones a cargo del usuario de bodega
     *
     * @param usuarioBodega
     * @param usuarioTienda
     * @param usuarioTienda
     * @param estado
     * @return
     */
    public ArrayList<Devolucion> getDevoluciones(String usuarioBodega, String estado) {
        List<Devolucion> devolucions = new ArrayList<>();
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(SELECT_BY_USUARIO_AND_ESTADO)) {
            statement.setString(1, usuarioBodega);
            statement.setString(2, estado);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                devolucions.add(getDevolucion(resultSet));
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return (ArrayList<Devolucion>) devolucions;
    }

    /**
     * Lista las devoluciones a cargo de un usuarioBedega por tienda
     *
     * @param usuarioBodega
     * @param estado
     * @param codigoTienda
     * @return
     */
    public ArrayList<Devolucion> getDevoluciones(String usuarioBodega, String estado, String codigoTienda) {
        List<Devolucion> devolucions = new ArrayList<>();
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(SELECT_BY_USUARIO_AND_ESTADO)) {
            statement.setString(1, usuarioBodega);
            statement.setString(2, estado);
            statement.setString(3, codigoTienda);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                devolucions.add(getDevolucion(resultSet));
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return (ArrayList<Devolucion>) devolucions;
    }

    /**
     * Lista las devoluciones a carga de un usuario_bodega
     *
     * @param usuarioBodega
     * @param estado
     * @param codigoTienda
     * @return
     */
    public ArrayList<Devolucion> getDevolucionesByTienda(String usuarioBodega, String estado, String codigoTienda) {
        List<Devolucion> devolucions = new ArrayList<>();
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(SELECT_BY_USUARIO_AND_ESTADO)) {
            statement.setString(1, usuarioBodega);
            statement.setString(2, estado);
            statement.setString(3, codigoTienda);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                devolucions.add(getDevolucion(resultSet));
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return (ArrayList<Devolucion>) devolucions;
    }

    /**
     * Lista de devoluciones generadas por tienda
     *
     * @param codigoTienda
     * @return
     */
    public ArrayList<Devolucion> getDevolucionesByTienda(String codigoTienda) {
        List<Devolucion> devolucions = new ArrayList<>();
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(DEVOLUCIONES_BY_TIENDA)) {
            statement.setString(1, codigoTienda);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                devolucions.add(getDevolucion(resultSet));
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return (ArrayList<Devolucion>) devolucions;
    }

    /**
     * Lista de devoluciones generadas por tienda, estado y en un intervalo de
     * tiempo
     *
     * @param estado
     * @param codigoTienda
     * @param fecha1
     * @param fecha2
     * @return
     */
    public ArrayList<Devolucion> getDevolucionesByTienda(String estado, String codigoTienda, String fecha1, String fecha2) {
        List<Devolucion> devolucions = new ArrayList<>();
        try (PreparedStatement statement = ConeccionDB.getConnection().prepareStatement(DEVOLUCIONES_BY_TIENDA_FECHA)) {
            statement.setString(1, estado);
            statement.setString(2, codigoTienda);
            statement.setString(3, fecha1);
            statement.setString(4, fecha2);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                devolucions.add(getDevolucion(resultSet));
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return (ArrayList<Devolucion>) devolucions;
    }

    private Devolucion getDevolucion(ResultSet resultSet) throws SQLException {
        return new Devolucion(
                resultSet.getInt("id"),
                resultSet.getString("fecha"),
                resultSet.getString("estado"),
                resultSet.getDouble("total"),
                resultSet.getString("usuario_tienda"),
                resultSet.getString("codigo_tienda"));
    }

}
