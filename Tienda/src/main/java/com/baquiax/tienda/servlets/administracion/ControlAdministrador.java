/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.servlets.administracion;

import com.baquiax.tienda.db.modelo.TiendaDB;
import com.baquiax.tienda.db.modelo.UsuarioDB;
import com.baquiax.tienda.db.modelo.UsuarioTiendaDB;
import com.baquiax.tienda.entidad.Usuario;
import com.baquiax.tienda.entidad.enumEntidad.TipoUsuarioEnum;
import java.io.IOException;
import java.util.Arrays;
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
@WebServlet(name = "ControlAdministrador", urlPatterns = {"/ControlAdministrador"})
public class ControlAdministrador extends HttpServlet {

    //BASE
    private UsuarioDB usuarioDB;
    private TiendaDB tiendaDB;
    private UsuarioTiendaDB usuarioTiendaDB;

    public ControlAdministrador() {
        //base
        this.usuarioDB = new UsuarioDB();
        this.tiendaDB = new TiendaDB();
        this.usuarioTiendaDB = new UsuarioTiendaDB();
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
            if (request.getSession().getAttribute("msj") != null) {
                request.getSession().removeAttribute("msj");
            }
            switch (tarea) {
                case "mostrarMenu":
                    mostrarMenus(request, response, request.getContextPath() + "/JSP/administrador/usuariosTienda.jsp");
                    break;
                case "activarDesactivar":
                    activarDesactivar(request, response);
                    break;
            }
        }

    }

    public void mostrarMenus(HttpServletRequest request, HttpServletResponse response, String ruta) throws IOException {
        request.getSession().setAttribute("tiendas", this.tiendaDB.getTienda());
        request.getSession().setAttribute("usuariosTienda", this.usuarioTiendaDB.getUsers());
        request.getSession().setAttribute("usuariosBodega", this.usuarioDB.getUsers(TipoUsuarioEnum.USUARIO_BODEGA.getTipo()));
        request.getSession().setAttribute("usuariosSupervisor", this.usuarioDB.getUsers(TipoUsuarioEnum.USUARIO_SUPERVISOR.getTipo()));
        response.sendRedirect(ruta);
    }

    private void activarDesactivar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String msj = "";
        String codigo = request.getParameter("codigo");
        Usuario user = serchUser(codigo, usuarioDB.getUsers());
        if (user.isEstado()) {
            user.setEstado(false);
        } else {
            user.setEstado(true);
        }
        if (usuarioDB.update(user)) {
            msj = "La acción se realizó con éxito";
        } else {
            //mostrar mensaje que no se pudo realizar la acción (activar o desactivar)
            msj = "No se pudo realizar la acción";
        }
        request.getSession().setAttribute("msj", msj);
        if (user.getTipo().equalsIgnoreCase(TipoUsuarioEnum.USUARIO_BODEGA.getTipo())) {
            mostrarMenus(request, response, request.getContextPath() + "/JSP/administrador/usuariosBodega.jsp");
        } else if (user.getTipo().equalsIgnoreCase(TipoUsuarioEnum.USUARIO_TIENDA.getTipo())) {
            mostrarMenus(request, response, request.getContextPath() + "/JSP/administrador/usuariosTienda.jsp");
        } else if (user.getTipo().equalsIgnoreCase(TipoUsuarioEnum.USUARIO_SUPERVISOR.getTipo())) {
            mostrarMenus(request, response, request.getContextPath() + "/JSP/administrador/usuariosSupervisor.jsp");
        }
    }

    public Usuario serchUser(String codigo, List<Usuario> usuarios) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCodigo().equals(codigo)) {
                return usuario;
            }
        }
        return null;
    }

    public Usuario serchUserByUsername(String username, List<Usuario> usuarios) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNombreUsuario().equals(username)) {
                return usuario;
            }
        }
        return null;
    }
}
