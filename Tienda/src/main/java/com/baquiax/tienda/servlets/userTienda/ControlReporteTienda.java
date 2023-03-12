/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.servlets.userTienda;

import com.baquiax.tienda.db.modelo.PedidoDB;
import com.baquiax.tienda.db.modelo.ProductoDB;
import com.baquiax.tienda.db.modelo.UsuarioTiendaDB;
import com.baquiax.tienda.entidad.EstadosTipos.ListaEstadosPedido;
import com.baquiax.tienda.entidad.Pedido;
import com.baquiax.tienda.entidad.Producto;
import com.baquiax.tienda.entidad.Tienda;
import com.baquiax.tienda.entidad.Usuario;
import com.baquiax.tienda.entidad.UsuarioTienda;
import com.baquiax.tienda.servlets.administracion.ControlUsuariosTienda;
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
@WebServlet(name = "ControlReporteTienda", urlPatterns = {"/ControlReporteTienda"})
public class ControlReporteTienda extends HttpServlet {

    //base 
    private ProductoDB productoDB;
    private PedidoDB pedidoDB;
    private UsuarioTiendaDB usuarioTiendaDB;
    //servlet
    private ControlUsuariosTienda controlUsuariosTienda;
    //entidad
    private List<Producto> productos;
    private List<Pedido> pedidos;
    private ListaEstadosPedido listaEstadosPedido;

    public ControlReporteTienda() {
        //base 
        this.productoDB = new ProductoDB();
        this.pedidoDB = new PedidoDB();
        this.usuarioTiendaDB = new UsuarioTiendaDB();
        //servlet
        this.controlUsuariosTienda = new ControlUsuariosTienda();
        //entidad
        this.productos = new ArrayList<>();
        this.pedidos = new ArrayList<>();
        this.listaEstadosPedido = new ListaEstadosPedido();
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
            switch (tarea) {
                case "reporteProductos":
                    reportarProductos(request, response);
                    break;
                case "reportePedidos":
                    reportarPedidos(request, response);
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
                case "reporteProductosExistencia":
                    reportarProductosExistencia(request, response);
                    break;
                case "filtrarPedidos":
                    filtrarPedidos(request, response);
                    break;
            }
        }
    }

    private void reportarProductos(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Tienda tienda = (Tienda) request.getSession().getAttribute("tienda");
        this.productos = this.productoDB.getProductoByTienda(tienda);
        request.getSession().setAttribute("catalogo", this.productos);
        response.sendRedirect(request.getContextPath() + "/JSP/usuarioTienda/reporteProductos.jsp");
    }

    private void reportarProductosExistencia(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Tienda tienda = (Tienda) request.getSession().getAttribute("tienda");
            int existencia = Integer.parseInt(request.getParameter("existencia"));
            this.productos = this.productoDB.getProductoByTienda(tienda, existencia);
            request.getSession().setAttribute("catalogo", this.productos);
            response.sendRedirect(request.getContextPath() + "/JSP/usuarioTienda/reporteProductos.jsp");
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }

    }

    private void reportarPedidos(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Tienda tienda = (Tienda) request.getSession().getAttribute("tienda");
        this.pedidos = this.pedidoDB.getPedidos(tienda.getCodigo());
        //estados
        request.getSession().setAttribute("estados", this.listaEstadosPedido.getEstadoPedidos());
        request.getSession().setAttribute("pedidos", this.pedidos);
        response.sendRedirect(request.getContextPath() + "/JSP/usuarioTienda/reportePedidos.jsp");
    }

    private void filtrarPedidos(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Tienda tienda = (Tienda) request.getSession().getAttribute("tienda");
        String estado = request.getParameter("estado");
        String fecha1 = request.getParameter("fecha1");
        String fecha2 = request.getParameter("fecha2");
        this.pedidos = this.pedidoDB.getPedidos(tienda.getCodigo(), estado, fecha1, fecha2);
        //estados
        request.getSession().setAttribute("estados", this.listaEstadosPedido.getEstadoPedidos());
        request.getSession().setAttribute("pedidos", this.pedidos);
        response.sendRedirect(request.getContextPath() + "/JSP/usuarioTienda/reportePedidos.jsp");
    }

}
