/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.servlets.userBodega;

import com.baquiax.tienda.db.modelo.DevolucionDB;
import com.baquiax.tienda.db.modelo.TiendaDB;
import com.baquiax.tienda.entidad.Devolucion;
import com.baquiax.tienda.entidad.Usuario;
import com.baquiax.tienda.entidad.UsuarioBodega;
import com.baquiax.tienda.entidad.EstadosTipos.ListaEstadoDevolucion;
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
@WebServlet(name = "ControlBodegaReporteDevoluciones", urlPatterns = {"/ControlBodegaReporteDevoluciones"})
public class ControlBodegaReporteDevoluciones extends HttpServlet {

    //base
    private DevolucionDB devolucionDB;
    private TiendaDB tiendaDB;
    //entidad
    private List<Devolucion> listaDevolucions;
    private String msjBodegaReporteDev;
    private ListaEstadoDevolucion listaEstadoDevolucion;

    public ControlBodegaReporteDevoluciones() {
        //base
        this.devolucionDB = new DevolucionDB();
        this.tiendaDB = new TiendaDB();
        //entidad
        this.msjBodegaReporteDev = "msjBodegaReporteDev";
        this.listaDevolucions = new ArrayList<>();
        this.listaEstadoDevolucion = new ListaEstadoDevolucion();
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
            case "listarDevoluciones":
                listarDevoluciones(request, response);
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
            case "devolucionesPorFecha":
                listarDevolucionesPorFecha(request, response);
                break;
        }
    }

    private void listarDevoluciones(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Usuario usuario = (Usuario) request.getSession().getAttribute("user");
        UsuarioBodega usuarioBodega = new UsuarioBodega();
        usuarioBodega.setCodigo(usuario.getCodigo());

        this.listaDevolucions = this.devolucionDB.getListaDevolcionesPorTienda(usuario.getCodigo());

        request.getSession().setAttribute("listaTiendas", this.tiendaDB.getTiendasByUsuarioBodega(usuarioBodega));
        request.getSession().setAttribute("estadosDevolucion", this.listaEstadoDevolucion.getEstadoDevolucions());
        request.getSession().setAttribute("listaDevoluciones", this.listaDevolucions);
        response.sendRedirect(request.getContextPath() + "/JSP/bodega/reporteDevoluciones.jsp");
    }

    private void listarDevolucionesPorFecha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Usuario usuario = (Usuario) request.getSession().getAttribute("user");
        String tienda = request.getParameter("tienda");
        String fecha1 = request.getParameter("fecha1");
        String fecha2 = request.getParameter("fecha2");
        String estado = request.getParameter("estadoDevolucion");

        this.listaDevolucions = this.devolucionDB.getListaDevolcionesPorTienda(estado, usuario.getCodigo(), fecha1, fecha2, tienda);
        request.getSession().setAttribute("listaDevoluciones", this.listaDevolucions);
        response.sendRedirect(request.getContextPath() + "/JSP/bodega/reporteDevoluciones.jsp");
    }

}
