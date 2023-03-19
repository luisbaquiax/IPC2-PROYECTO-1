/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.servlets.userBodega;

import com.baquiax.tienda.db.modelo.EnvioDB;
import com.baquiax.tienda.db.modelo.TiendaDB;
import com.baquiax.tienda.entidad.Envio;
import com.baquiax.tienda.entidad.Usuario;
import com.baquiax.tienda.entidad.UsuarioBodega;
import com.baquiax.tienda.entidad.enumEntidad.EstadoEnvioEnum;
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
@WebServlet(name = "ControlBodegaReporteEnvios", urlPatterns = {"/ControlBodegaReporteEnvios"})
public class ControlBodegaReporteEnvios extends HttpServlet {

    //base
    private EnvioDB envioDB;
    private TiendaDB tiendaDB;
    //servlet
    //entidad
    private List<Envio> listaEnvios;
    //var
    private String msjBodegaReporteEnvio;

    public ControlBodegaReporteEnvios() {
        //base
        this.envioDB = new EnvioDB();
        this.tiendaDB = new TiendaDB();
        //servlet
        //entidad
        this.listaEnvios = new ArrayList<>();
        //var
        this.msjBodegaReporteEnvio = "msjBodegaReporteEnvio";
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
            case "listarEnviosGenerados":
                listarEnviosGenerados(request, response);
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
            case "listarEnviosGeneradosPorTienda":
                listarEnviosGeneradosPorTienda(request, response);
                break;
        }
    }

    private void listarEnviosGenerados(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Usuario usuario = (Usuario) request.getSession().getAttribute("user");
        UsuarioBodega usuarioBodega = new UsuarioBodega();
        usuarioBodega.setCodigo(usuario.getCodigo());
        this.listaEnvios = this.envioDB.getEnviosByUsuarioBodega(EstadoEnvioEnum.DESPACHADO.toString(), usuario.getCodigo());

        request.getSession().setAttribute("enviosGenerados", this.listaEnvios);
        request.getSession().setAttribute("tiendasBodega", this.tiendaDB.getTiendasByUsuarioBodega(usuarioBodega));
        response.sendRedirect(request.getContextPath() + "/JSP/bodega/enviosGenerados.jsp");
    }

    private void listarEnviosGeneradosPorTienda(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String codigoTienda = request.getParameter("tienda");
            String fecha1 = request.getParameter("fecha1");
            String fecha2 = request.getParameter("fecha2");
            Usuario usuario = (Usuario) request.getSession().getAttribute("user");

            request.getSession().setAttribute("enviosGenerados", this.envioDB.getEnvios(codigoTienda, EstadoEnvioEnum.DESPACHADO.toString(), usuario.getCodigo(), fecha1, fecha2));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            request.getSession().setAttribute(msjBodegaReporteEnvio, "No se pudo realizar la búsqueda, lo sentimos.");
        }
        response.sendRedirect(request.getContextPath() + "/JSP/bodega/enviosGenerados.jsp");
    }

}
