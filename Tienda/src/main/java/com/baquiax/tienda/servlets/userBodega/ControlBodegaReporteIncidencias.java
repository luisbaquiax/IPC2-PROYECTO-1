/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.servlets.userBodega;

import com.baquiax.tienda.db.modelo.IncidenciaDB;
import com.baquiax.tienda.db.modelo.TiendaDB;
import com.baquiax.tienda.entidad.Usuario;
import com.baquiax.tienda.entidad.UsuarioBodega;
import com.baquiax.tienda.entidad.enumEntidad.EstadoIncidenciaEnum;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author luis
 */
@WebServlet(name = "ControlBodegaReporteIncidencias", urlPatterns = {"/ControlBodegaReporteIncidencias"})
public class ControlBodegaReporteIncidencias extends HttpServlet {

    //base
    private TiendaDB tiendaDB;
    private IncidenciaDB incidenciaDB;

    public ControlBodegaReporteIncidencias() {
        //base
        this.tiendaDB = new TiendaDB();
        this.incidenciaDB = new IncidenciaDB();

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
            case "listarIncidenciasSolucionadas":
                listarIncidenciasSolucionadas(request, response);
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
            case "listarIncidenciasSolucionadasTienda":
                listarIncidenciasSolucionadasTienda(request, response);
                break;
        }
    }

    private void listarIncidenciasSolucionadas(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Usuario usuario = (Usuario) request.getSession().getAttribute("user");
        UsuarioBodega usuarioBodega = new UsuarioBodega();
        usuarioBodega.setCodigo(usuario.getCodigo());
        request.getSession().setAttribute("incidenciasSolucionadas", this.incidenciaDB.getIncidencia(usuario.getCodigo(), EstadoIncidenciaEnum.SOLUCIONADA.toString()));
        request.getSession().setAttribute("tiendasBodega", this.tiendaDB.getTiendasByUsuarioBodega(usuarioBodega));
        response.sendRedirect(request.getContextPath() + "/JSP/bodega/incidenciasSolucionadas.jsp");
    }

    private void listarIncidenciasSolucionadasTienda(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String tienda = request.getParameter("tienda");
        String fecha1 = request.getParameter("fecha1");
        String fecha2 = request.getParameter("fecha2");
        Usuario usuario = (Usuario) request.getSession().getAttribute("user");
        request.getSession().setAttribute("incidenciasSolucionadas",
                this.incidenciaDB.getIncidencias(usuario.getCodigo(), EstadoIncidenciaEnum.SOLUCIONADA.toString(), tienda, fecha1, fecha2));
        response.sendRedirect(request.getContextPath() + "/JSP/bodega/incidenciasSolucionadas.jsp");
    }

}
