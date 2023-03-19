/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.db.modelo.reporteAdministracion;

import java.util.List;

/**
 *
 * @author luis
 */
public class ManejoReporteAdministracion {

    private List<String[]> tiendasMasPedidos;
    private List<String[]> tiendasMasPedidosFecha;
    private List<String[]> usuariosMasEnvios;
    private List<String[]> usuariosMasEnviosFecha;
    private List<String[]> usuariosMasPedidos;
    private List<String[]> usuariosMasPedidosFecha;

    private ReporteAdministracionDB reporteAdministracionDB;

    public ManejoReporteAdministracion() {
        this.reporteAdministracionDB = new ReporteAdministracionDB();
    }

    /**
     * @return the tiendasMasPedidos
     */
    public List<String[]> getTiendasMasPedidos() {
        return this.reporteAdministracionDB.getReporteAdministracion(
                ReporteAdministracionDB.REPORTE_TIENDAS_PEDIDOS,
                ReporteAdministracionDB.NUMERO_PEDIDOS,
                ReporteAdministracionDB.CODIGO_TIENDA);
    }

    /**
     *
     * @param fecha1
     * @param fecha2
     * @param estado
     * @return
     */
    public List<String[]> getTiendasMasPedidosFecha(String fecha1, String fecha2, String estado) {
        return reporteAdministracionDB.getReporteAdministracion(
                ReporteAdministracionDB.REPORTE_TIENDAS_PEDIDOS_BY_ESTADO_BETWEEN_FECHA,
                ReporteAdministracionDB.NUMERO_PEDIDOS,
                ReporteAdministracionDB.CODIGO_TIENDA,
                fecha1,
                fecha2,
                estado);
    }

    /**
     * @return the usuariosMasEnvios
     */
    public List<String[]> getUsuariosMasEnvios() {
        return reporteAdministracionDB.getReporteAdministracion(
                ReporteAdministracionDB.REPORTE_USUARIOS_ENVIOS_GENERADOS,
                ReporteAdministracionDB.NUMERO_ENVIOS,
                ReporteAdministracionDB.USUARIO_BODEGA);
    }

    /**
     * @param fecha1
     * @param fecha2
     * @return the usuariosMasEnviosFecha
     */
    public List<String[]> getUsuariosMasEnviosFecha(String fecha1, String fecha2) {
        return reporteAdministracionDB.getReporteAdministracion(
                ReporteAdministracionDB.REPORTE_USUARIOS_ENVIOS_GENERADOS,
                ReporteAdministracionDB.NUMERO_ENVIOS,
                ReporteAdministracionDB.USUARIO_BODEGA,
                fecha1,
                fecha2);
    }

    /**
     * @return the usuariosMasPedidos
     */
    public List<String[]> getUsuariosMasPedidos() {
        return reporteAdministracionDB.getReporteAdministracion(
                ReporteAdministracionDB.REPORTE_USUARIOS_PEDIDOS_GENERADOS,
                ReporteAdministracionDB.NUMERO_PEDIDOS,
                ReporteAdministracionDB.USUARIO_TIENDA);
    }

    /**
     *
     * @param fecha1
     * @param fecha2
     * @return the usuariosMasPedidosFecha
     *
     */
    public List<String[]> getUsuariosMasPedidosFecha(String fecha1, String fecha2) {
        return reporteAdministracionDB.getReporteAdministracion(
                ReporteAdministracionDB.REPORTE_USUARIOS_PEDIDOS_GENERADOS,
                ReporteAdministracionDB.NUMERO_PEDIDOS,
                ReporteAdministracionDB.USUARIO_TIENDA,
                fecha1,
                fecha2);
    }

}
