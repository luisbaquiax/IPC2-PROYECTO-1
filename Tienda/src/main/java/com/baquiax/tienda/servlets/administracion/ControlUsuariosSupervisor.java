/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.servlets.administracion;

import com.baquiax.tienda.db.modelo.UsuarioDB;
import com.baquiax.tienda.entidad.Usuario;
import com.baquiax.tienda.entidad.encripta.Encriptador;
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
@WebServlet(name = "ControlUsuariosSupervisor", urlPatterns = {"/ControlUsuariosSupervisor"})
public class ControlUsuariosSupervisor extends HttpServlet {

    //base
    private UsuarioDB usuarioDB;

    //servlet
    private ControlAdministrador controlAdministrador;

    //entidad
    private Encriptador encriptador;

    public ControlUsuariosSupervisor() {
        //base
        this.usuarioDB = new UsuarioDB();
        //servlet
        this.controlAdministrador = new ControlAdministrador();
        //entidad
        this.encriptador = new Encriptador();
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
                case "openEdit":
                    abrirModal(request, response);
                    break;
                case "listar":
                    listar(request, response);
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
                case "editar":
                    editar(request, response);
                    break;
                case "add":
                    agregar(request, response);
                    break;

            }
        }

    }

    private void listar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.controlAdministrador.mostrarMenus(request, response, request.getContextPath() + "/JSP/administrador/usuariosSupervisor.jsp");
    }

    private void abrirModal(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String codigo = request.getParameter("codigo");
        Usuario usuario = this.controlAdministrador.serchUser(codigo, this.usuarioDB.getUsers());
        System.out.println(usuario.getCodigo());
        usuario.setPassword(encriptador.desencriptar(usuario.getPassword()));
        request.getSession().setAttribute("usuarioSupervisor", usuario);
        response.sendRedirect(request.getContextPath() + "/JSP/administrador/modalEditUserSupervisor.jsp");
    }

    private void editar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String msj = "";
        String msj2 = "";
        String codigoUsuario = request.getParameter("codigoUsuario");
        String nombre = request.getParameter("nombre");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        //usuario
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioSupervisor");
        //validar que no exista otro con mismo user-name
        if (controlAdministrador.serchUserByUsername(username, this.usuarioDB.getUsers()) == null) {
            usuario.setNombreUsuario(username);
        }
        usuario.setEmail(email);
        usuario.setNombre(nombre);
        usuario.setPassword(encriptador.encriptar(password));

        if (usuarioDB.update(usuario)) {
            msj = "Se ha hecho los cambios exitosamente.";
        } else {
            msj = "No se pudo realizar los cambios";
        }
        request.getSession().setAttribute("msj", msj);
        listar(request, response);
    }

    private void agregar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String msj = "";
        String codigo = request.getParameter("codigo");
        String nombre = request.getParameter("nombre");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        //validar que no exista otro con mismo user_name
        if (controlAdministrador.serchUserByUsername(username, this.usuarioDB.getUsers()) == null) {
            Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioSupervisor");
            usuario.setNombreUsuario(username);
            //usuario
            usuario.setCodigo(codigo);
            usuario.setEmail(email);
            usuario.setNombre(nombre);
            usuario.setPassword(encriptador.encriptar(password));
            if (usuarioDB.insert(usuario)) {
                msj = "Se guardado con éxito los datos";
            } else {
                msj = "No se pudo guardar los datos";
            }
        } else {
            msj = "El nombre de usuario no está disponible.";
        }

        request.getSession().setAttribute("msj", msj);
        listar(request, response);
    }

}
