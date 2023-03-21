/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.servlets.userTienda;

import com.baquiax.tienda.db.modelo.CatalogoDB;
import com.baquiax.tienda.db.modelo.DetalleEnvioDB;
import com.baquiax.tienda.db.modelo.EnvioDB;
import com.baquiax.tienda.db.modelo.ProductoDB;
import com.baquiax.tienda.entidad.Catalogo;
import com.baquiax.tienda.entidad.DetalleEnvio;
import com.baquiax.tienda.entidad.Envio;
import com.baquiax.tienda.entidad.Producto;
import com.baquiax.tienda.entidad.Tienda;
import com.baquiax.tienda.entidad.enumEntidad.EstadoEnvioEnum;
import com.baquiax.tienda.entidad.manejadores.ManejoEnvio;
import java.io.IOException;
import java.time.LocalDate;
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
    private DetalleEnvioDB detalleEnvioDB;
    private CatalogoDB catalogoDB;
    private ProductoDB productoDB;
    //servlet
    private ControlPedidoTienda controlPedidoTienda;
    //listas
    private List<DetalleEnvio> listaProductoEnvio;
    //entidad
    private ManejoEnvio manejoEnvio;

    public ControlEnviosTienda() {
        //BASE
        this.envioDB = new EnvioDB();
        this.detalleEnvioDB = new DetalleEnvioDB();
        this.catalogoDB = new CatalogoDB();
        this.productoDB = new ProductoDB();
        //SERVLET
        this.controlPedidoTienda = new ControlPedidoTienda();
        //lista
        this.listaProductoEnvio = new ArrayList<>();
        //entidad
        this.manejoEnvio = new ManejoEnvio();
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
                    case "openDetalleEnvio":
                        openDetalleEnvio(request, response);
                        break;
                    case "marcarEnvio":
                        marcarEnvio(request, response);
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
        this.controlPedidoTienda.mostrarMendu(request, response, ruta);
    }

    private void buscarEnvio(HttpServletRequest request, HttpServletResponse response, String ruta) throws IOException {
        String msj = "";
        List<Envio> envios = new ArrayList<>();
        try {
            int idEnvio = Integer.parseInt(request.getParameter("idEnvio"));
            Tienda t = (Tienda) request.getSession().getAttribute("tienda");
            envios = this.envioDB.getEnvios(t.getCodigo(), idEnvio, EstadoEnvioEnum.DESPACHADO.toString());
            request.getSession().setAttribute("envios", envios);
            if (envios.isEmpty()) {
                msj = "No existe envios";
            }
        } catch (Exception e) {
            msj = "No se pudo realizar la búsqueda";
        }
        request.getSession().setAttribute("msj", msj);
        response.sendRedirect(ruta);
    }

    private void openDetalleEnvio(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Envio envio = new Envio();
        envio.setId(id);
        this.listaProductoEnvio = this.detalleEnvioDB.getDetalleByEnvio(envio);
        request.getSession().setAttribute("detalleDelEnvio", this.listaProductoEnvio);
        response.sendRedirect(request.getContextPath() + "/JSP/usuarioTienda/modalDetalleEnvio.jsp");
    }

    private void marcarEnvio(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Tienda tienda = (Tienda) request.getSession().getAttribute("tienda");
        Envio envio = manejoEnvio.getEnvio(id, (List<Envio>) request.getSession().getAttribute("envios"));
        if (envio != null) {
            envio.setFechaLlegada(LocalDate.now().toString());
            envio.setEstado(EstadoEnvioEnum.RECIBIDO.toString());
            if (envioDB.update(envio)) {
                //actualizar catalogo de productos de esta tienda en específico
                List<Catalogo> catalogo = this.catalogoDB.getCatalog(tienda.getCodigo());
                List<DetalleEnvio> detalleEnvios = this.detalleEnvioDB.getDetalleByEnvio(envio);
                for (int i = 0; i < catalogo.size(); i++) {
                    for (int j = 0; j < detalleEnvios.size(); j++) {
                        if (catalogo.get(i).getCodigoProducto().equals(detalleEnvios.get(j).getCodigoProducto())) {
                            catalogo.get(i).setExistencia(catalogo.get(i).getExistencia() + detalleEnvios.get(j).getCantidad());
                            this.catalogoDB.update(catalogo.get(i));
                            break;
                        }
                    }
                }
                request.getSession().setAttribute("msj", "Se ha marcado como recibido el pedido");
            } else {
                request.getSession().setAttribute("msj", "No se pudo completar la acción.");
            }
        }
        response.sendRedirect(request.getContextPath() + "/ControlEnviosTienda?tarea=verTodos");
    }

}
