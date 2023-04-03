/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.baquiax.tienda.servlets.userTienda;

import com.baquiax.tienda.db.modelo.EnvioDB;
import com.baquiax.tienda.db.modelo.reporteUsuarioTienda.ReporteUsuarioTiendaDB;
import com.baquiax.tienda.entidad.Tienda;
import com.baquiax.tienda.entidad.enumEntidad.EstadoDevolucionEnum;
import com.baquiax.tienda.entidad.enumEntidad.EstadoEnvioEnum;
import com.baquiax.tienda.entidad.enumEntidad.EstadoIncidenciaEnum;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Luis
 */
@WebServlet(name = "ControlReporteEnvios", urlPatterns = {"/ControlReporteEnvios"})
public class ControlReporteEnvios extends HttpServlet {

    private EnvioDB envioDB;
    private ReporteUsuarioTiendaDB reporteUsuarioTiendaDB;

    private String msjEnvioRecibidoReporte;

    public ControlReporteEnvios() {
        this.envioDB = new EnvioDB();
        this.reporteUsuarioTiendaDB = new ReporteUsuarioTiendaDB();

        this.msjEnvioRecibidoReporte = "msjEnvioRecibidoReporte";
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String tarea = request.getParameter("tarea");
        switch (tarea) {
            case "enviosRecibidos":
                enviosRecibidos(request, response);
                break;
            default:
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String tarea = request.getParameter("tarea");
        switch (tarea) {
            case "enviosRecibidosFecha":
                enviosRecibidosFecha(request, response);
                break;
            default:
        }
    }

    private void enviosRecibidos(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Tienda tienda = (Tienda) request.getSession().getAttribute("tienda");
        request.getSession().setAttribute("reporteEnviosRecibidos",
                this.envioDB.getEnvios(tienda.getCodigo(), EstadoEnvioEnum.RECIBIDO.toString()));

        setCantidad(request, response);
        response.sendRedirect(request.getContextPath() + "/JSP/usuarioTienda/reporteEnviosRecibidos.jsp");
    }

    private void enviosRecibidosFecha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fecha1 = request.getParameter("fecha1");
        String fecha2 = request.getParameter("fecha2");

        Tienda tienda = (Tienda) request.getSession().getAttribute("tienda");
        request.getSession().setAttribute("reporteEnviosRecibidos",
                this.envioDB.getEnvios(tienda.getCodigo(), EstadoEnvioEnum.RECIBIDO.toString(), fecha1, fecha2));

        setCantidad(request, response);
        response.sendRedirect(request.getContextPath() + "/JSP/usuarioTienda/reporteEnviosRecibidos.jsp");
    }

    private void setCantidad(HttpServletRequest request, HttpServletResponse response) {
        Tienda tienda = (Tienda) request.getSession().getAttribute("tienda");
        request.getSession().setAttribute("cantidadIncidenciasActivas",
                reporteUsuarioTiendaDB.cantidad(
                        ReporteUsuarioTiendaDB.CANTIDAD_INCIDENCIAS_ACTIVAS,
                        tienda.getCodigo(),
                        EstadoIncidenciaEnum.ACTIVA.toString()));
        request.getSession().setAttribute("cantidadDevolucionesActivas", reporteUsuarioTiendaDB.cantidad(
                ReporteUsuarioTiendaDB.CANTIDAD_DEVOLUCIONES_ACTIVAS,
                tienda.getCodigo(),
                EstadoIncidenciaEnum.ACTIVA.toString()));

    }
}
