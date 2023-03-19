/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.servlets.supervisor;

import com.baquiax.tienda.db.modelo.DetallePedidoDB;
import com.baquiax.tienda.db.modelo.PedidoDB;
import com.baquiax.tienda.db.modelo.TiendaDB;
import com.baquiax.tienda.db.modelo.reporteSupervisor.ReportePedidosSupervisorDB;
import com.baquiax.tienda.entidad.DetallePedido;
import com.baquiax.tienda.entidad.Pedido;
import com.baquiax.tienda.entidad.enumEntidad.EstadoPedidoEnum;
import com.baquiax.tienda.entidad.enumEntidad.TipoTiendaEnum;
import java.io.IOException;
import java.util.ArrayList;
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
@WebServlet(name = "ControlReportePedidosSupervisor", urlPatterns = {"/ControlReportePedidosSupervisor"})
public class ControlReportePedidosSupervisor extends HttpServlet {

    //base
    private ReportePedidosSupervisorDB reportePedidosSupervisorDB;
    private TiendaDB tiendaDB;
    private DetallePedidoDB detallePedidoDB;
    private PedidoDB pedidoDB;
    //servlet
    //entidad
    private List<Pedido> listaPedidos;
    private List<DetallePedido> detallePedido;

    public ControlReportePedidosSupervisor() {
        //base
        this.reportePedidosSupervisorDB = new ReportePedidosSupervisorDB();
        this.tiendaDB = new TiendaDB();
        this.detallePedidoDB = new DetallePedidoDB();
        this.pedidoDB = new PedidoDB();
        //servlet
        //entidad
        this.listaPedidos = new ArrayList<>();
        this.detallePedido = new ArrayList<>();

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
            case "listarPedidos":
                listarPedidos(request, response);
                break;
            case "verDetalle":
                verDetallePedido(request, response);
                break;
            case "aprobar":
                aprobarPedido(request, response);
                break;
            case "openModalRechazar":
                openModalRechazar(request, response);
                break;
            case "reportePedidos":
                reportarPedidos(request, response);
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
            case "listaPedidoTienda":
                listarPedidoTienda(request, response);
                break;
            case "rechazarPedido":
                rechazarPedido(request, response);
                break;
        }
    }

    private void listarPedidos(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println(TipoTiendaEnum.SUPERVISADA.getTipo() + EstadoPedidoEnum.PENDIENTE.toString());
        this.listaPedidos = this.reportePedidosSupervisorDB.getPedidosPendientes(TipoTiendaEnum.SUPERVISADA.getTipo(), EstadoPedidoEnum.PENDIENTE.toString());

        request.getSession().setAttribute("stores", this.tiendaDB.getTienda(TipoTiendaEnum.SUPERVISADA.getTipo()));
        request.getSession().setAttribute("listaPedidos", this.listaPedidos);
        response.sendRedirect(request.getContextPath() + "/JSP/supervisor/listaPedidos.jsp");
    }

    private void listarPedidoTienda(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String codigoTienda = request.getParameter("tienda");
        this.listaPedidos = this.reportePedidosSupervisorDB.getPedidosPendientes(TipoTiendaEnum.SUPERVISADA.toString(), EstadoPedidoEnum.PENDIENTE.toString(), codigoTienda);
        request.getSession().setAttribute("stores", this.tiendaDB.getTienda(TipoTiendaEnum.SUPERVISADA.getTipo()));
        request.getSession().setAttribute("listaPedidos", this.listaPedidos);
        response.sendRedirect(request.getContextPath() + "/JSP/supervisor/listaPedidos.jsp");
    }

    private void verDetallePedido(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Pedido pedido = new Pedido();
        pedido.setId(id);
        request.getSession().setAttribute("detallePedido", this.detallePedidoDB.getDetallePedidoByIdPedido(pedido));
        response.sendRedirect(request.getContextPath() + "/JSP/supervisor/modalDetallePedido.jsp");
    }

    private void aprobarPedido(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String msj = "";
        int idPedido = Integer.parseInt(request.getParameter("id"));
        Pedido pedido = new Pedido();
        pedido.setId(idPedido);
        pedido.setEstado(EstadoPedidoEnum.SOLICITADO.toString());
        if (pedidoDB.update(pedido)) {
            msj = "Se ha hecho la acción con éxito.";
        } else {
            msj = "No se pudo realizar la acción.";
        }
        listarPedidos(request, response);

    }

    private void openModalRechazar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Pedido pedido = getPedido(id, listaPedidos);
        this.detallePedido = this.detallePedidoDB.getDetallePedidoByIdPedido(pedido);
        request.getSession().setAttribute("pedido", pedido);
        request.getSession().setAttribute("detallePedido", detallePedido);
        response.sendRedirect(request.getContextPath() + "/JSP/supervisor/modalRechazoPedido.jsp");
    }

    private void rechazarPedido(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String msj = "";
        //el motivo del rechazo del pedido
        String motivo = request.getParameter("motivoRechazo");
        System.out.println(motivo);
        Pedido pedido = (Pedido) request.getSession().getAttribute("pedido");
        if (pedido != null) {
            pedido.setEstado(EstadoPedidoEnum.RECHAZADO.toString());
            if (pedidoDB.update(pedido)) {
                msj = "Se ha hecho la acción con éxito.";
            } else {
                msj = "No se pudo realizar la acción.";
            }
        }
        request.getSession().setAttribute("msj", msj);
        listarPedidos(request, response);
    }

    private void reportarPedidos(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.listaPedidos = this.reportePedidosSupervisorDB.getPedidosTienda(TipoTiendaEnum.SUPERVISADA.getTipo());
        request.getSession().setAttribute("reportePedidos", listaPedidos);
        response.sendRedirect(request.getContextPath() + "/JSP/supervisor/reportePedidosSupervisor.jsp");
    }

    public Pedido getPedido(int id, List<Pedido> pedidos) {
        for (Pedido pedido : pedidos) {
            if (pedido.getId() == id) {
                return pedido;
            }
        }
        return null;
    }

}
