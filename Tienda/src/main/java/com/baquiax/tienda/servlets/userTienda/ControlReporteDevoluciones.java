/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.servlets.userTienda;

import com.baquiax.tienda.db.modelo.DevolucionDB;
import com.baquiax.tienda.entidad.Devolucion;
import com.baquiax.tienda.entidad.Tienda;
import com.baquiax.tienda.entidad.enumEntidad.ListaEstadoDevolucion;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author luis
 */
@WebServlet(name = "ControlReporteDevoluciones", urlPatterns = {"/ControlReporteDevoluciones"})
public class ControlReporteDevoluciones extends HttpServlet {

    //base
    private DevolucionDB devolucionDB;
    //servlet
    //entidad
    private ListaEstadoDevolucion listaEstadoDevolucion;
    private List<Devolucion> devolucions;

    public ControlReporteDevoluciones() {
        //base
        this.devolucionDB = new DevolucionDB();
        //servlet
        //entidad
        this.listaEstadoDevolucion = new ListaEstadoDevolucion();
        this.devolucions = new ArrayList<>();
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
            case "reporteDevoluciones":
                reportarDevoluciones(request, response);
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
        String tarea = request.getParameter("tarea");
        switch (tarea) {
            case "filtrarDevoluciones":
                filtrarReporteDevoluciones(request, response);
                break;
        }
    }

    private void reportarDevoluciones(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Tienda tienda = (Tienda) request.getSession().getAttribute("tienda");
        //devolciones de la tienda en cestión
        this.devolucions = this.devolucionDB.getDevolucionesByTienda(tienda.getCodigo());

        request.getSession().setAttribute("estadosDevolucion", this.listaEstadoDevolucion.getEstadoDevolucions());
        request.getSession().setAttribute("listaDevoluciones", this.devolucions);
        response.sendRedirect(request.getContextPath() + "/JSP/usuarioTienda/reporteDevoluciones.jsp");
    }

    private void filtrarReporteDevoluciones(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String estado = request.getParameter("estado");
        String fecha1 = request.getParameter("fecha1");
        String fecha2 = request.getParameter("fecha2");
        Tienda tienda = (Tienda) request.getSession().getAttribute("tienda");
        if (estado != null && (fecha1 != null) && fecha2 != null && (tienda != null)) {
            if (!estado.isBlank() && !fecha1.isBlank() && !fecha2.isBlank()) {
                //devoluciones de la tienda en cuestión filtrado por estdo y en untervalo de fecha.
                this.devolucions = this.devolucionDB.getDevolucionesByTienda(estado, tienda.getCodigo(), fecha1, fecha2);
                request.getSession().setAttribute("estadosDevolucion", this.listaEstadoDevolucion.getEstadoDevolucions());
                request.getSession().setAttribute("listaDevoluciones", this.devolucions);
                response.sendRedirect(request.getContextPath() + "/JSP/usuarioTienda/reporteDevoluciones.jsp");
            } else {
                //alguien manipulo del DOM
                response.sendRedirect(request.getContextPath() + "/Close");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/Close");

        }

    }

}
