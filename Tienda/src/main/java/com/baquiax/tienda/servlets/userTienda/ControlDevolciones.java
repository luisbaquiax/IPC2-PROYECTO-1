/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.servlets.userTienda;

import com.baquiax.tienda.db.modelo.DetalleDevolucionDB;
import com.baquiax.tienda.db.modelo.DetalleEnvioDB;
import com.baquiax.tienda.db.modelo.DevolucionDB;
import com.baquiax.tienda.db.modelo.EnvioDB;
import com.baquiax.tienda.db.modelo.IncidenciaDB;
import com.baquiax.tienda.entidad.DetalleDevolucion;
import com.baquiax.tienda.entidad.DetalleEnvio;
import com.baquiax.tienda.entidad.Devolucion;
import com.baquiax.tienda.entidad.Envio;
import com.baquiax.tienda.entidad.EstadosTipos.ListaMotivoDevolucion;
import com.baquiax.tienda.entidad.Tienda;
import com.baquiax.tienda.entidad.Usuario;
import com.baquiax.tienda.entidad.enumEntidad.EstadoDevolucionEnum;
import com.baquiax.tienda.entidad.enumEntidad.EstadoEnvioEnum;
import com.baquiax.tienda.entidad.manejadores.ManejoDevoluciones;
import com.baquiax.tienda.entidad.manejadores.ManejoEnvio;
import com.baquiax.tienda.entidad.manejadores.ManejoIncidencias;
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
@WebServlet(name = "ControlDevolciones", urlPatterns = {"/ControlDevolciones"})
public class ControlDevolciones extends HttpServlet {

    private EnvioDB envioDB;
    private DetalleEnvioDB detalleEnvioDB;
    private DevolucionDB devolucionDB;
    private IncidenciaDB incidenciaDB;
    private DetalleDevolucionDB detalleDevolucionDB;

    private List<Envio> listaEnviosRecibidos;
    private List<DetalleEnvio> listaDetalleEnvio;
    private List<DetalleDevolucion> listaDetalleDevolucion;

    private ListaMotivoDevolucion listaMotivoDevolucion;
    private ManejoIncidencias manejoIncidencias;
    private ManejoEnvio manejoEnvio;
    private ManejoDevoluciones manejoDevoluciones;

    private String msjDevoluciones;
    private String msje;

    public ControlDevolciones() {
        this.envioDB = new EnvioDB();
        this.detalleEnvioDB = new DetalleEnvioDB();
        this.devolucionDB = new DevolucionDB();
        this.incidenciaDB = new IncidenciaDB();
        this.detalleDevolucionDB = new DetalleDevolucionDB();

        this.listaMotivoDevolucion = new ListaMotivoDevolucion();
        this.listaEnviosRecibidos = new ArrayList<>();
        this.listaDetalleEnvio = new ArrayList<>();
        this.listaDetalleDevolucion = new ArrayList<>();

        this.msjDevoluciones = "msjDevoluciones";
        this.msje = "msje";
        this.manejoIncidencias = new ManejoIncidencias();
        this.manejoEnvio = new ManejoEnvio();
        this.manejoDevoluciones = new ManejoDevoluciones();

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

        String tarea = request.getParameter("tarea");
        switch (tarea) {
            case "listarEnviosRecibidos":
                listarEnviosRecibidos(request, response);
                break;
            case "generarEevolucion":
                generarEevolucion(request, response);
                break;
            case "quitarProducto":
                quitarProducto(request, response);
                break;
            default:
                listarEnviosRecibidos(request, response);
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
            case "listarEnviosPorID":
                listarEnviosPorID(request, response);
                break;
            case "agregarProducto":
                agregarProducto(request, response);
                break;
            case "realizarDevolucion":
                realizarDevolucion(request, response);
                break;
            default:
                listarEnviosRecibidos(request, response);
        }
    }

    /**
     * Lista los envios recibidos
     *
     * @param request
     * @param response
     * @throws IOException
     */
    private void listarEnviosRecibidos(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Tienda tienda = (Tienda) request.getSession().getAttribute("tienda");
        this.listaDetalleDevolucion = new ArrayList<>();
        this.listaEnviosRecibidos = this.envioDB.getEnvios(tienda.getCodigo(), EstadoEnvioEnum.RECIBIDO.toString());
        System.out.println(Arrays.toString(listaEnviosRecibidos.toArray()));
        request.getSession().setAttribute("listaEnviosRecibidos", this.listaEnviosRecibidos);
        response.sendRedirect(request.getContextPath() + "/JSP/usuarioTienda/enviosRecibidosDev.jsp");
    }

    /**
     * Lista envios recibidos por id
     *
     * @param request
     * @param response
     * @throws IOException
     */
    private void listarEnviosPorID(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Envio envio = manejoEnvio.getEnvio(Integer.parseInt(request.getParameter("idEnvio")), listaEnviosRecibidos);
        List<Envio> envios = new ArrayList<>();
        if (envio != null) {
            envios.add(envio);
        }
        request.getSession().setAttribute("listaEnviosRecibidos", envios);
        response.sendRedirect(request.getContextPath() + "/JSP/usuarioTienda/enviosRecibidosDev.jsp");
    }

    private void generarEevolucion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idEnvio = Integer.parseInt(request.getParameter("id"));
        Envio envio = manejoEnvio.getEnvio(idEnvio, this.listaEnviosRecibidos);
        this.listaDetalleEnvio = this.detalleEnvioDB.getDetalleByEnvio(envio);

        request.getSession().setAttribute("envio", envio);
        request.getSession().setAttribute("detalleEnvioDev", this.listaDetalleEnvio);
        request.getSession().setAttribute("motivosDevolucion", this.listaMotivoDevolucion.getMotivos());
        request.getSession().setAttribute("listaDetalleDev", this.listaDetalleDevolucion);
        response.sendRedirect(request.getContextPath() + "/JSP/usuarioTienda/crearDevolucion.jsp");

    }

    private void agregarProducto(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] codigoName = request.getParameter("codigoProducto").split(",");
        String codigo = codigoName[0];
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));
        String motivo = request.getParameter("motivo");

        DetalleEnvio detalleEnvio = manejoEnvio.getDetalleEnvio(codigo, listaDetalleEnvio);
        if (detalleEnvio != null) {
            if (detalleEnvio.getCantidad() >= cantidad) {
                if (manejoDevoluciones.getDetalleDevolucion(codigo, motivo, listaDetalleDevolucion) == null) {
                    listaDetalleDevolucion.add(
                            new DetalleDevolucion(0,
                                    codigo,
                                    detalleEnvio.getPrecioUnitario(),
                                    cantidad,
                                    motivo,
                                    cantidad * detalleEnvio.getPrecioUnitario()));
                    request.getSession().setAttribute(msjDevoluciones, "Se ha agreado con éxito el producto a la devolución.");
                } else {
                    request.getSession().setAttribute(msjDevoluciones, "El producto con el mismo motivo ya existe");
                }
            } else {

            }
        } else {
            request.getSession().setAttribute(msjDevoluciones, "El producto no existe en el envío.");
        }

        response.sendRedirect(request.getContextPath() + "/JSP/usuarioTienda/crearDevolucion.jsp");
    }

    private void quitarProducto(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String codigo = request.getParameter("codigo");
        String motivo = request.getParameter("motivo");
        if (listaDetalleDevolucion.size() > 1) {
            //removemos el detalle en la devolucion
            for (int i = 0; i < this.listaDetalleDevolucion.size(); i++) {
                if (listaDetalleDevolucion.get(i).getCodigoProducto().equals(codigo)
                        && listaDetalleDevolucion.get(i).getMotivo().equals(motivo)) {
                    listaDetalleDevolucion.remove(i);
                    request.getSession().setAttribute(msjDevoluciones, "Se ha quitado con éxito el producto en la devolución.");
                    break;
                }
            }
        } else {
            request.getSession().setAttribute(msjDevoluciones, "La devolución debe tener al menos un producto.");
        }
        response.sendRedirect(request.getContextPath() + "/JSP/usuarioTienda/crearDevolucion.jsp");
    }

    private void realizarDevolucion(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String fecha = request.getParameter("fecha");
        Envio envio = (Envio) request.getSession().getAttribute("envio");
        Usuario usuario = (Usuario) request.getSession().getAttribute("user");
        Tienda tienda = (Tienda) request.getSession().getAttribute("tienda");

        if (devolucionDB.inset(
                new Devolucion(
                        0,
                        fecha,
                        EstadoDevolucionEnum.ACTIVA.toString(),
                        manejoDevoluciones.getTotal(listaDetalleDevolucion),
                        usuario.getCodigo(),
                        tienda.getCodigo(),
                        envio.getId()))) {
            int ultimo = this.incidenciaDB.getUltimoID(DevolucionDB.ULTIMO);
            for (DetalleDevolucion detalleDevolucion : this.listaDetalleDevolucion) {
                detalleDevolucion.setIdDevolucion(ultimo);
                this.detalleDevolucionDB.inset(detalleDevolucion);
            }
            request.getSession().setAttribute(msje, "Se enviado la devolución.");
        } else {
            request.getSession().setAttribute(msje, "No se ha podido enviar la devolución.");
        }
        listarEnviosRecibidos(request, response);
    }

}
