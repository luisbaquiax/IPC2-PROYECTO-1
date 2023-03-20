/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.servlets.login;

import com.baquiax.tienda.db.modelo.TiendaDB;
import com.baquiax.tienda.db.modelo.UsuarioDB;
import com.baquiax.tienda.entidad.Usuario;
import com.baquiax.tienda.entidad.UsuarioTienda;
import com.baquiax.tienda.entidad.encripta.Encriptador;
import com.baquiax.tienda.entidad.enumEntidad.TipoUsuarioEnum;
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
@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {

    //base
    private UsuarioDB usuarioDB;
    private TiendaDB tiendaDB;
    //entidad
    private Encriptador encriptador;

    public Login() {
        this.usuarioDB = new UsuarioDB();
        this.tiendaDB = new TiendaDB();
        this.encriptador = new Encriptador();
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
        String username = request.getParameter("username");
        String password = request.getParameter("pass");
        System.out.println(username);
        System.out.println(password);
        Usuario usuario = this.usuarioDB.getUser(username, encriptador.encriptar(password));
        if (usuario != null) {
            System.out.println(usuario.toString());
            if (usuario.isEstado()) {
                request.getSession().setAttribute("user", usuario);
                if (usuario.getTipo().equalsIgnoreCase(TipoUsuarioEnum.USUARIO_ADMIN.getTipo())) {
                    response.sendRedirect(request.getContextPath() + "/ControlAdministrador?tarea=mostrarMenu");
                } else if (usuario.getTipo().equalsIgnoreCase(TipoUsuarioEnum.USUARIO_TIENDA.getTipo())) {
                    UsuarioTienda useTienda = new UsuarioTienda();
                    useTienda.setCodigo(usuario.getCodigo());
                    System.out.println(tiendaDB.getTiendaByUsuarioTienda(useTienda).toString());
                    request.getSession().setAttribute("tienda", this.tiendaDB.getTiendaByUsuarioTienda(useTienda));
                    response.sendRedirect(request.getContextPath() + "/ControlPedidoTienda?tarea=mostrarMenu");
                } else if (usuario.getTipo().equalsIgnoreCase(TipoUsuarioEnum.USUARIO_BODEGA.getTipo())) {
                    response.sendRedirect(request.getContextPath() + "/ControlBodegaEnvios?tarea=listarPedidosSolicitados");
                } else if (usuario.getTipo().equalsIgnoreCase(TipoUsuarioEnum.USUARIO_SUPERVISOR.getTipo())) {
                    response.sendRedirect(request.getContextPath() + "/ControlReportePedidosSupervisor?tarea=listarPedidos");
                }
            } else {
                // el usuario ha sido desactivado
                request.getSession().setAttribute("msj", "Usted ha sido desactivado.");
                response.sendRedirect("login.jsp");
            }

        } else {
            request.getSession().setAttribute("msj", "Nombre de usuario o contraseña incorrecta.");
            response.sendRedirect("login.jsp");
        }

    }

}
