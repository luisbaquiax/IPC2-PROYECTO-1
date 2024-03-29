/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.servlets.administracion;

import com.baquiax.tienda.entidad.enumEntidad.TipoUsuarioEnum;
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
@WebServlet(name = "ControlUsuariosBodega", urlPatterns = {"/ControlUsuariosBodega"})
public class ControlUsuariosBodega extends HttpServlet {

    //servlet
    private ControlAdministrador controlAdministrador;

    public ControlUsuariosBodega() {
        this.controlAdministrador = new ControlAdministrador();
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
        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/Login");
        } else {
            String tarea = request.getParameter("tarea");
            switch (tarea) {
                case "":
                    mostrarUsuariosBodega(request, response);
                    break;
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
            switch (tarea) {
                case "":
                    break;
            }
        }

    }

    private void mostrarUsuariosBodega(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.controlAdministrador.mostrarMenus(request, response, request.getContextPath() + "/JSP/administrador/usuariosBodega.jsp");
    }

}
