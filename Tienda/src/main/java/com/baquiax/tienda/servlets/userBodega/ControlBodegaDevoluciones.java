/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.servlets.userBodega;

import com.baquiax.tienda.db.modelo.DetalleDevolucionDB;
import com.baquiax.tienda.db.modelo.DevolucionDB;
import com.baquiax.tienda.db.modelo.TiendaDB;
import com.baquiax.tienda.entidad.DetalleDevolucion;
import com.baquiax.tienda.entidad.Devolucion;
import com.baquiax.tienda.entidad.Usuario;
import com.baquiax.tienda.entidad.UsuarioBodega;
import com.baquiax.tienda.entidad.enumEntidad.EstadoDevolucionEnum;
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
@WebServlet(name = "ControlBodegaDevoluciones", urlPatterns = {"/ControlBodegaDevoluciones"})
public class ControlBodegaDevoluciones extends HttpServlet {

    //base
    private DevolucionDB devolucionDB;
    private TiendaDB tiendaDB;
    private DetalleDevolucionDB detalleDevolucionDB;
    //entidad
    private List<Devolucion> listaDevolucions;
    private String msjDevolucion;
    private List<DetalleDevolucion> produtosDevolucion;

    public ControlBodegaDevoluciones() {
        //BASE
        this.devolucionDB = new DevolucionDB();
        this.tiendaDB = new TiendaDB();
        this.detalleDevolucionDB = new DetalleDevolucionDB();
        //entidad
        this.listaDevolucions = new ArrayList<>();
        this.produtosDevolucion = new ArrayList<>();
        this.msjDevolucion = "msjDevolucion";
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
            case "openModalDetalleDevolucion":
                openModalDetalleDevolucion(request, response);
                break;
            case "aprobar":
                aprobar(request, response);
                break;
            case "openModalRechazar":
                openModalRechazar(request, response);
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
            case "listarDevolucionesPorTienda":
                listarDevolucionesPorTienda(request, response);
                break;
            case "rechazar":
                rechazar(request, response);
                break;
        }
    }

    private void listarDevoluciones(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Usuario usuario = (Usuario) request.getSession().getAttribute("user");
        UsuarioBodega usuarioBodega = new UsuarioBodega();
        usuarioBodega.setCodigo(usuario.getCodigo());

        this.listaDevolucions = this.devolucionDB.getDevoluciones(usuario.getCodigo(), EstadoDevolucionEnum.ACTIVA.toString());

        request.getSession().setAttribute("listadoDevoluciones", this.listaDevolucions);
        request.getSession().setAttribute("tiendasBodega", this.tiendaDB.getTiendasByUsuarioBodega(usuarioBodega));
        response.sendRedirect(request.getContextPath() + "/JSP/bodega/listaDevoluciones.jsp");
    }

    private void listarDevolucionesPorTienda(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Usuario usuario = (Usuario) request.getSession().getAttribute("user");

        String tienda = request.getParameter("tienda");

        this.listaDevolucions = this.devolucionDB.getDevoluciones(usuario.getCodigo(), EstadoDevolucionEnum.ACTIVA.toString(), tienda);

        request.getSession().setAttribute("listadoDevoluciones", this.listaDevolucions);
        response.sendRedirect(request.getContextPath() + "/JSP/bodega/listaDevoluciones.jsp");
    }

    private void openModalDetalleDevolucion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        mostrarDetalle(request, response);

        response.sendRedirect(request.getContextPath() + "/JPS/bodega/modalDetalleDevolucion.jsp");
    }

    private void aprobar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idDevolucion = Integer.parseInt(request.getParameter("id"));
        Devolucion devolucion = new Devolucion();
        devolucion.setId(idDevolucion);
        devolucion.setEstado(EstadoDevolucionEnum.ACEPTADA.toString());
        if (devolucionDB.update(devolucion)) {
            msjDevolucion = "Se ha aprobado la devolución con éxito.";
        } else {
            msjDevolucion = "No se ha podido realizar la aprobación.";
        }
        request.getSession().setAttribute("msjDevolucion", msjDevolucion);
        listarDevoluciones(request, response);
    }

    private void openModalRechazar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        mostrarDetalle(request, response);

        response.sendRedirect(request.getContextPath() + "/JSP/bodega/modalRechazarDevolucion.jsp");
    }

    private void rechazar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idDevolucion = Integer.parseInt(request.getParameter("id"));
        String motivo = request.getParameter("motivoDev");
        Devolucion devolucion = new Devolucion();
        devolucion.setId(idDevolucion);
        devolucion.setEstado(EstadoDevolucionEnum.RECHAZADA.toString());

        //se agrega el motivo del rechazo de la devolucion
        //se actualiza la devolucion
        this.devolucionDB.update(devolucion);

        listarDevoluciones(request, response);
    }

    private void mostrarDetalle(HttpServletRequest request, HttpServletResponse response) {
        int idDevolucion = Integer.parseInt(request.getParameter("id"));
        Devolucion devolucion = new Devolucion();
        devolucion.setId(idDevolucion);
        this.produtosDevolucion = this.detalleDevolucionDB.getDetalleDevolucion(devolucion);

        request.getSession().setAttribute("productosDevolucion", this.produtosDevolucion);
    }

}
