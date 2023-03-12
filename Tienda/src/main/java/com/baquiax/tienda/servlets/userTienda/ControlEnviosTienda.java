/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.servlets.userTienda;

import com.baquiax.tienda.db.modelo.EnvioDB;
import com.baquiax.tienda.entidad.Envio;
import com.baquiax.tienda.entidad.Tienda;
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
@WebServlet(name = "ControlEnviosTienda", urlPatterns = {"/ControlEnviosTienda"})
public class ControlEnviosTienda extends HttpServlet {

    //BASE
    private EnvioDB envioDB;
    //servlet
    private ControlPedidoTienda controlPedidoTienda;

    public ControlEnviosTienda() {
        //BASE
        this.envioDB = new EnvioDB();
        //SERVLET
        this.controlPedidoTienda = new ControlPedidoTienda();
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
        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/Login");
        } else {
            String tarea = request.getParameter("tarea");
            if (tarea != null) {
                switch (tarea) {
                    case "verTodos":
                        verTodos(request, response, request.getContextPath() + "/JSP/usuarioTienda/envios.jsp");
                        break;
                    case "buscarEnvio":
                        buscarEnvio(request, response, request.getContextPath() + "/JSP/usuarioTienda/envios.jsp");
                        break;
                    case "reiniciar":
                        break;
                    case "reealizar":
                        break;
                }
            }
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
            if (tarea != null) {
                switch (tarea) {
                    case "buscarEnvio":
                        buscarEnvio(request, response, request.getContextPath() + "/JSP/usuarioTienda/envios.jsp");
                        break;
                    case "reiniciar":
                        break;
                    case "reealizar":
                        break;
                }
            }
        }

    }

    private void verTodos(HttpServletRequest request, HttpServletResponse response, String ruta) throws IOException {
        request.getSession().setAttribute("msj", "");
        this.controlPedidoTienda.mostrarMendu(request, response, ruta);
    }

    private void buscarEnvio(HttpServletRequest request, HttpServletResponse response, String ruta) throws IOException {
        String msj = "";
        List<Envio> envios = new ArrayList<>();
        try {
            int idEnvio = Integer.parseInt(request.getParameter("idEnvio"));
            Tienda t = (Tienda) request.getSession().getAttribute("tienda");
            envios = this.envioDB.getEnvios(t.getCodigo(), idEnvio);
            if (envios.isEmpty()) {
                msj = "No existe envios";
            }
        } catch (Exception e) {
            msj = "No se pudo realizar la búsqueda";
        }
        request.getSession().setAttribute("msj", msj);
        request.getSession().setAttribute("envios", envios);
        response.sendRedirect(ruta);
    }

}
