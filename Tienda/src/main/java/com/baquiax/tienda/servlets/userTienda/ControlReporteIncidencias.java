/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.servlets.userTienda;

import com.baquiax.tienda.db.modelo.IncidenciaDB;
import com.baquiax.tienda.db.modelo.UsuarioTiendaDB;
import com.baquiax.tienda.entidad.EstadosTipos.ListaEstadosIncidencia;
import com.baquiax.tienda.entidad.Incidencia;
import com.baquiax.tienda.entidad.Tienda;
import com.baquiax.tienda.servlets.administracion.ControlUsuariosTienda;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author luis
 */
@WebServlet(name = "ControlReporteIncidencias", urlPatterns = {"/ControlReporteIncidencias"})
public class ControlReporteIncidencias extends HttpServlet {

    //base 
    private UsuarioTiendaDB usuarioTiendaDB;
    private IncidenciaDB incidenciaDB;
    //servlet
    private ControlUsuariosTienda controlUsuariosTienda;
    //entidad
    private List<Incidencia> incidencias;
    private ListaEstadosIncidencia listaEstadosIncidencia;

    public ControlReporteIncidencias() {
        //base
        this.usuarioTiendaDB = new UsuarioTiendaDB();
        this.incidenciaDB = new IncidenciaDB();
        //servlet
        this.controlUsuariosTienda = new ControlUsuariosTienda();
        //entidad
        this.incidencias = new ArrayList<>();
        this.listaEstadosIncidencia = new ListaEstadosIncidencia();
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tarea = request.getParameter("tarea");
        switch (tarea) {
            case "reporteIncidencias":
                reportarIncidencias(request, response);
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
                case "filtrarIncidencias":
                    filtrarIncidencias(request, response);
                    break;
            }
        }

    }

    private void reportarIncidencias(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Tienda tienda = (Tienda) request.getSession().getAttribute("tienda");

        this.incidencias = this.incidenciaDB.getIncidenciasByTienda(tienda.getCodigo());

        //estados
        request.getSession().setAttribute("estadosIncdencia", this.listaEstadosIncidencia.getEstadoIncidencias());
        request.getSession().setAttribute("incidencias", this.incidencias);
        response.sendRedirect(request.getContextPath() + "/JSP/usuarioTienda/reporteIncidencias.jsp");
    }

    private void filtrarIncidencias(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String estado = request.getParameter("estado");
        String fecha1 = request.getParameter("fecha1");
        String fecha2 = request.getParameter("fecha2");

        Tienda tienda = (Tienda) request.getSession().getAttribute("tienda");
        this.incidencias = this.incidenciaDB.getIncidenciasByTienda(estado, tienda.getCodigo(), fecha1, fecha2);
        request.getSession().setAttribute("estadosIncdencia", this.listaEstadosIncidencia.getEstadoIncidencias());
        request.getSession().setAttribute("incidencias", this.incidencias);
        response.sendRedirect(request.getContextPath() + "/JSP/usuarioTienda/reporteIncidencias.jsp");
    }

}
