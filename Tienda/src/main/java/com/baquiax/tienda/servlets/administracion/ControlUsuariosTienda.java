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
import com.baquiax.tienda.entidad.UsuarioTienda;
import com.baquiax.tienda.entidad.encripta.Encriptador;
import com.baquiax.tienda.entidad.enumEntidad.TipoUsuarioEnum;
import java.io.IOException;
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
@WebServlet(name = "ControlUsuariosTienda", urlPatterns = {"/ControlUsuariosTienda"})
public class ControlUsuariosTienda extends HttpServlet {

    //servlet
    private ControlAdministrador controlAdministrador;

    //base
    private UsuarioDB usuarioDB;
    private UsuarioTiendaDB usuarioTiendaDB;
    private TiendaDB tiendaDB;
    //entidad
    private Encriptador encriptador;

    public ControlUsuariosTienda() {
        //servlet
        this.controlAdministrador = new ControlAdministrador();
        this.usuarioTiendaDB = new UsuarioTiendaDB();
        this.tiendaDB = new TiendaDB();
        //base
        this.usuarioDB = new UsuarioDB();
        //entidad
        this.encriptador = new Encriptador();
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
            if (request.getSession().getAttribute("msj") != null) {
                request.getSession().removeAttribute("msj");
            }
            String tarea = request.getParameter("tarea");
            switch (tarea) {
                case "usuariosTienda":
                    listarUsuariosTienda(request, response);
                    break;
                case "openEdit":
                    abrirModalEditar(request, response);
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
            if (request.getSession().getAttribute("msj") != null) {
                request.getSession().removeAttribute("msj");
            }
            String tarea = request.getParameter("tarea");
            switch (tarea) {
                case "add":
                    agregarNuevo(request, response);
                    break;
                case "editar":
                    editar(request, response);
                    break;
            }
        }

    }

    public void listarUsuariosTienda(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.controlAdministrador.mostrarMenus(request, response, request.getContextPath() + "/JSP/administrador/usuariosTienda.jsp");
    }

    private void abrirModalEditar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String codigo = request.getParameter("codigo");
        UsuarioTienda usuarioTienda = getUserTienda(codigo, this.usuarioTiendaDB.getUsers());
        usuarioTienda.setPassword(encriptador.desencriptar(usuarioTienda.getPassword()));
        request.getSession().setAttribute("usuario", usuarioTienda);
        response.sendRedirect(request.getContextPath() + "/JSP/administrador/modalEditUserTienda.jsp");
    }

    private void editar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String msj = "";
        String codigoUsuario = request.getParameter("codigoUsuario");
        String nombre = request.getParameter("nombre");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String nuevaTienda = request.getParameter("tienda");
        //usuario
        Usuario usuario = this.controlAdministrador.serchUser(codigoUsuario, this.usuarioDB.getUsers());
        //validar username
        if (controlAdministrador.serchUserByUsername(username, this.usuarioDB.getUsers()) == null) {
            usuario.setNombreUsuario(username);
        }
        usuario.setEmail(email);
        usuario.setPassword(encriptador.encriptar(password));
        usuario.setNombre(nombre);
        //usuario tienda
        UsuarioTienda usuarioTienda = new UsuarioTienda();
        usuarioTienda.setCodigoTienda(nuevaTienda);
        if (usuarioDB.update(usuario)) {
            if (usuarioTiendaDB.update(usuarioTienda)) {
                msj = "Se ha actualizado el usuario exitosamente.";
            }
        } else {
            //falló la actualización de datos.
            msj = "No se pudo realizar la actualización.";
        }
        request.getSession().setAttribute("msj", msj);
        listarUsuariosTienda(request, response);
    }

    private void agregarNuevo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String msj = "";
        String codigo = request.getParameter("codigo");
        String nombre = request.getParameter("nombre");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String nuevaTienda = request.getParameter("tienda");

        Usuario usuario = new Usuario(codigo, TipoUsuarioEnum.USUARIO_TIENDA.getTipo(), username, nombre, encriptador.encriptar(password), true, email);
        //validar username
        if (controlAdministrador.serchUserByUsername(username, this.usuarioDB.getUsers()) == null) {
            //usuario tienda
            UsuarioTienda usuarioTienda = new UsuarioTienda();
            usuarioTienda.setCodigo(codigo);
            usuarioTienda.setCodigoTienda(nuevaTienda);

            if (usuarioDB.insert(usuario) && (usuarioTiendaDB.insert(usuarioTienda))) {
                msj = "Se ha guardado correctamente los datos.";
            } else {
                //fallo en guardar datos
                msj = "No so pudo guardar los datos.";
            }
        } else {
            msj = "El nombre de usuario no está disponible.";
        }

        request.getSession().setAttribute("msj", msj);
        listarUsuariosTienda(request, response);
    }

    public UsuarioTienda getUserTienda(String codigo, List<UsuarioTienda> usuarios) {
        for (UsuarioTienda usuario : usuarios) {
            if (usuario.getCodigo().equals(codigo)) {
                return usuario;
            }
        }
        return null;
    }

}
