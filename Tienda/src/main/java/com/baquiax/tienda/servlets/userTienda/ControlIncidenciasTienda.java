/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.servlets.userTienda;

import com.baquiax.tienda.db.modelo.DetalleEnvioDB;
import com.baquiax.tienda.db.modelo.DetalleIncidenciaDB;
import com.baquiax.tienda.db.modelo.EnvioDB;
import com.baquiax.tienda.db.modelo.IncidenciaDB;
import com.baquiax.tienda.entidad.DetalleEnvio;
import com.baquiax.tienda.entidad.DetalleIncidencia;
import com.baquiax.tienda.entidad.Envio;
import com.baquiax.tienda.entidad.EstadosTipos.ListaEstadosIncidencia;
import com.baquiax.tienda.entidad.EstadosTipos.ListaMotivoIncidencia;
import com.baquiax.tienda.entidad.Incidencia;
import com.baquiax.tienda.entidad.Tienda;
import com.baquiax.tienda.entidad.Usuario;
import com.baquiax.tienda.entidad.enumEntidad.EstadoEnvioEnum;
import com.baquiax.tienda.entidad.enumEntidad.EstadoIncidenciaEnum;
import com.baquiax.tienda.entidad.manejadores.ManejoEnvio;
import com.baquiax.tienda.entidad.manejadores.ManejoIncidencias;
import java.io.IOException;
import java.sql.Array;
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
@WebServlet(name = "ControlIncidenciasTienda", urlPatterns = {"/ControlIncidenciasTienda"})
public class ControlIncidenciasTienda extends HttpServlet {

    private EnvioDB envioDB;
    private IncidenciaDB incidenciaDB;
    private DetalleIncidenciaDB detalleIncidenciaDB;
    private DetalleEnvioDB detalleEnvioDB;

    private List<Incidencia> listaIncidencias;
    private List<Envio> listaEnviosRecibidos;
    private List<DetalleEnvio> listaDetalleEnvio;
    private List<DetalleIncidencia> listaDetalleIncidencia;

    private ListaMotivoIncidencia listaMotivosIncidencia;
    private ManejoIncidencias manejoIncidencias;
    private ManejoEnvio manejoEnvio;

    private String msjEnvioRecibido;
    private String msjCrearIncidencia;

    public ControlIncidenciasTienda() {
        this.envioDB = new EnvioDB();
        this.incidenciaDB = new IncidenciaDB();
        this.detalleIncidenciaDB = new DetalleIncidenciaDB();
        this.detalleEnvioDB = new DetalleEnvioDB();

        this.listaMotivosIncidencia = new ListaMotivoIncidencia();
        this.listaIncidencias = new ArrayList<>();
        this.listaEnviosRecibidos = new ArrayList<>();
        this.listaDetalleEnvio = new ArrayList<>();
        this.listaDetalleIncidencia = new ArrayList<>();

        this.msjEnvioRecibido = "msjEnvioRecibido";
        this.msjCrearIncidencia = "msjCrearIncidencia";
        this.manejoIncidencias = new ManejoIncidencias();
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
        String tarea = request.getParameter("tarea");
        switch (tarea) {
            case "listarEnviosRecibidos":
                listarEnviosRecibidos(request, response);
                break;
            case "agregarIncidencia":
                agregarIncidencia(request, response);
                break;
            case "quitarProducto":
                quitarProducto(request, response);
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
            case "agregarProducto":
                agregarProducto(request, response);
                break;
            case "reportarIncidencia":
                reportarIncidencia(request, response);
                break;
            case "listarPorId":
                listarPorId(request, response);
                break;
        }
    }

    /**
     * Muestra los envios al usuario de tienda que están
     *
     * @param request
     * @param response
     */
    private void listarEnviosRecibidos(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Tienda tienda = (Tienda) request.getSession().getAttribute("tienda");

        this.listaEnviosRecibidos = this.envioDB.getEnvios(tienda.getCodigo(), EstadoEnvioEnum.RECIBIDO.toString());

        request.getSession().setAttribute("enviosRecibidos", this.listaEnviosRecibidos);
        response.sendRedirect(request.getContextPath() + "/JSP/usuarioTienda/enviosRecibidos.jsp");
    }

    /**
     * Redirige a la página para crear una incidencia
     *
     * @param request
     * @param response
     * @throws IOException
     */
    private void agregarIncidencia(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Envio envio = manejoEnvio.getEnvio(id, listaEnviosRecibidos);
        this.listaDetalleEnvio = this.detalleEnvioDB.getDetalleByEnvio(envio);
        System.out.println("detalle envio\n" + Arrays.toString(listaDetalleEnvio.toArray()));
        System.out.println("motivos\n" + (listaMotivosIncidencia.toString()));
        this.listaDetalleIncidencia = new ArrayList<>();
        request.getSession().removeAttribute(msjCrearIncidencia);
        request.getSession().setAttribute("listaDetalleIncidencia", this.listaDetalleIncidencia);
        request.getSession().setAttribute("envioEncidencia", envio);
        request.getSession().setAttribute("listMotivosIncidencia", this.listaMotivosIncidencia.getMotivos());
        request.getSession().setAttribute("listaDetalleEnvio", this.listaDetalleEnvio);
        response.sendRedirect(request.getContextPath() + "/JSP/usuarioTienda/crearIncidencia.jsp");
    }

    /**
     * Agrega 'producto' a la incidencia
     *
     * @param request
     * @param response
     * @throws IOException
     */
    private void agregarProducto(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String msj = "";
        String[] codeCantidad = request.getParameter("codigoProducto").split(",");
        String codigo = codeCantidad[0];
        String motivoIncidencia = request.getParameter("motivoIncidencia");
        int cantidad = Integer.parseInt(request.getParameter("cantidadProducto"));

        DetalleEnvio detalle = manejoEnvio.getDetalleEnvio(codigo, listaDetalleEnvio);
        System.out.println("cantidad " + manejoIncidencias.cantidadProductos(codigo, listaDetalleIncidencia));
        if (detalle != null) {
            if ((detalle.getCantidad() >= cantidad) 
                    || detalle.getCantidad() >= (cantidad + manejoIncidencias.cantidadProductos(codigo, listaDetalleIncidencia))) {
                if (manejoIncidencias.validarDetalleIncidencia(codigo, motivoIncidencia, this.listaDetalleIncidencia) == null) {
                    this.listaDetalleIncidencia.add(new DetalleIncidencia(0, codigo, cantidad, motivoIncidencia));
                    msj = "Se ha agregado el producto en la incidencia.";
                } else {
                    msj = "Este producto y motivo ya existe en la detalle de la incidencia.";
                }
            } else {
                msj = "La cantidad a agregar no debe ser mayor a la cantidad de productos del envío.";
            }
        } else {
            msj = "El producto no existe en el envio.";
        }
        System.out.println(Arrays.toString(listaDetalleIncidencia.toArray()));
        request.getSession().setAttribute(msjCrearIncidencia, msj);
        request.getSession().setAttribute("listaDetalleIncidencia", this.listaDetalleIncidencia);
        response.sendRedirect(request.getContextPath() + "/JSP/usuarioTienda/crearIncidencia.jsp");
    }

    /**
     * Quita un "producto" en la incidencia
     *
     * @param request
     * @param response
     * @throws IOException
     */
    private void quitarProducto(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String codigo = request.getParameter("codigo");
        String motivo = request.getParameter("motivo");
        if (listaDetalleIncidencia.size() > 1) {
            manejoIncidencias.removerDetalleIncidencia(codigo, motivo, listaDetalleIncidencia);
            request.getSession().setAttribute(msjCrearIncidencia, "Se ha quitado el producto en la incidencia.");
        } else {
            request.getSession().setAttribute(msjCrearIncidencia, "La incidencia debe tener al menos un producto.");
        }
        request.getSession().setAttribute("listaDetalleIncidencia", this.listaDetalleIncidencia);
        response.sendRedirect(request.getContextPath() + "/JSP/usuarioTienda/crearIncidencia.jsp");
    }

    /**
     * Agregamos la incidencia en la base de datos y su detalle de productos
     *
     * @param request
     * @param response
     * @throws IOException
     */
    private void reportarIncidencia(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fecha = request.getParameter("fecha");
        Tienda tienda = (Tienda) request.getSession().getAttribute("tienda");
        Usuario usuario = (Usuario) request.getSession().getAttribute("user");
        Envio envio = (Envio) request.getSession().getAttribute("envioEncidencia");
        Incidencia incidencia = new Incidencia(
                0,
                fecha,
                EstadoIncidenciaEnum.ACTIVA.toString(),
                usuario.getCodigo(),
                tienda.getCodigo(),
                envio.getId());
        if (incidenciaDB.insert(incidencia)) {
            int ultimaIncidenia = this.incidenciaDB.getUltimoID(IncidenciaDB.ULTIMO);
            for (DetalleIncidencia detalle : this.listaDetalleIncidencia) {
                detalle.setIdIncidencia(ultimaIncidenia);
                this.detalleIncidenciaDB.insert(detalle);
            }
            request.getSession().setAttribute(msjEnvioRecibido, "Se ha reportado la incidencia.");
        } else {
            request.getSession().setAttribute(msjEnvioRecibido, "No se pudo realizar la acción.");
        }
        listarEnviosRecibidos(request, response);
    }

    /**
     * Solo mostrara el envio buscado por su id
     *
     * @param request
     * @param response
     * @throws IOException
     */
    private void listarPorId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("idEnvio"));
        Envio envio = manejoEnvio.getEnvio(id, listaEnviosRecibidos);
        List<Envio> envios = new java.util.ArrayList<>();
        if (envio != null) {
            envios.add(envio);
        }
        request.getSession().setAttribute("enviosRecibidos", envios);
        response.sendRedirect(request.getContextPath() + "/JSP/usuarioTienda/enviosRecibidos.jsp");

    }

}
