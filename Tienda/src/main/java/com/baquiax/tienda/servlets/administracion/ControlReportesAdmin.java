/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.servlets.administracion;

import com.baquiax.tienda.db.modelo.reporteAdministracion.ManejoReporteAdministracion;
import com.baquiax.tienda.entidad.EstadosTipos.ListaEstadosEnvio;
import com.baquiax.tienda.entidad.EstadosTipos.ListaEstadosPedido;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author luis
 */
@WebServlet(name = "ControlReportesAdmin", urlPatterns = {"/ControlReportesAdmin"})
public class ControlReportesAdmin extends HttpServlet {

    private ManejoReporteAdministracion manejoReporteAdministracion;

    private ListaEstadosPedido listaEstadosPedido;
    private ListaEstadosEnvio listaEstadosEnvio;

    public ControlReportesAdmin() {
        this.manejoReporteAdministracion = new ManejoReporteAdministracion();
        this.listaEstadosEnvio = new ListaEstadosEnvio();
        this.listaEstadosPedido = new ListaEstadosPedido();
    }

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
            case "listarTiendasMasPedidos":
                listarTiendasMasPedidos(request, response);
                break;
            case "listarUsuarioMasEnvios":
                listarUsuarioMasEnvios(request, response);
                break;
            case "listarUsuariosMasPedidos":
                listarUsuariosMasPedidos(request, response);
                break;

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
        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/Login");
        } else {
            String tarea = request.getParameter("tarea");
            switch (tarea) {
                case "listarTiendasMasPedidosFiltrado":
                    listarTiendasMasPedidosFiltrado(request, response);
                    break;
                case "listarUsuarioMasEnviosFiltrado":
                    listarUsuarioMasEnviosFiltrado(request, response);
                    break;
                case "listarUsuariosMasPedidosFiltrado":
                    listarUsuariosMasPedidosFiltrado(request, response);
                    break;
            }
        }

    }

    private void listarTiendasMasPedidos(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().setAttribute("listaTiendasMasPedidos", this.manejoReporteAdministracion.getTiendasMasPedidos());
        request.getSession().setAttribute("reporte", "1");
        request.getSession().setAttribute("estadosPedido", this.listaEstadosPedido.getEstadoPedidos());
        response.sendRedirect(request.getContextPath() + "/JSP/administrador/reportes.jsp");
    }

    private void listarTiendasMasPedidosFiltrado(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fecha1 = request.getParameter("fecha1");
        String fecha2 = request.getParameter("fecha2");
        String estado = request.getParameter("estado");
        request.getSession().setAttribute("listaTiendasMasPedidos", this.manejoReporteAdministracion.getTiendasMasPedidosFecha(fecha1, fecha2, estado));
        request.getSession().setAttribute("reporte", "2");
        response.sendRedirect(request.getContextPath() + "/JSP/administrador/reportes.jsp");
    }

    private void listarUsuarioMasEnvios(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().setAttribute("listaUsuariosMasEnvios", this.manejoReporteAdministracion.getUsuariosMasEnvios());
        request.getSession().setAttribute("reporte", "3");
        response.sendRedirect(request.getContextPath() + "/JSP/administrador/reportes.jsp");
    }

    private void listarUsuarioMasEnviosFiltrado(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fecha1 = request.getParameter("fecha1");
        String fecha2 = request.getParameter("fecha2");
        request.getSession().setAttribute("listaUsuariosMasEnvios", this.manejoReporteAdministracion.getUsuariosMasEnviosFecha(fecha1, fecha2));
        request.getSession().setAttribute("reporte", "4");
        response.sendRedirect(request.getContextPath() + "/JSP/administrador/reportes.jsp");
    }

    private void listarUsuariosMasPedidos(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().setAttribute("listarUsuariosMasPedidos", this.manejoReporteAdministracion.getUsuariosMasPedidos());
        request.getSession().setAttribute("reporte", "5");
        response.sendRedirect(request.getContextPath() + "/JSP/administrador/reportes.jsp");
    }

    private void listarUsuariosMasPedidosFiltrado(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fecha1 = request.getParameter("fecha1");
        String fecha2 = request.getParameter("fecha2");
        request.getSession().setAttribute("listarUsuariosMasPedidos", this.manejoReporteAdministracion.getUsuariosMasPedidosFecha(fecha1, fecha2));
        request.getSession().setAttribute("reporte", "6");
        response.sendRedirect(request.getContextPath() + "/JSP/administrador/reportes.jsp");
    }

}
