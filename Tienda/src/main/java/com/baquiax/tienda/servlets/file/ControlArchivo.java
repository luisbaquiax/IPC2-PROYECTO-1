/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.servlets.file;

import com.baquiax.tienda.db.modelo.UsuarioDB;
import com.baquiax.tienda.entidad.datos.CargaDatos;
import com.baquiax.tienda.entidad.datos.ContenidoArchivoJSON;
import com.baquiax.tienda.entidad.datos.ManejoDatosJSON;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.json.simple.parser.ParseException;

/**
 *
 * @author luis
 */
@WebServlet(name = "ControlArchivo", urlPatterns = {"/ControlArchivo"})
@MultipartConfig
public class ControlArchivo extends HttpServlet {

    private ContenidoArchivoJSON contenidoArchivoJSON;
    private ManejoDatosJSON manejoDatosJSON;
    private CargaDatos cargaDatos;
    private UsuarioDB usuarioDB;

    public ControlArchivo() {
        this.contenidoArchivoJSON = new ContenidoArchivoJSON();
        this.cargaDatos = new CargaDatos();
        //base
        this.usuarioDB = new UsuarioDB();
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
        System.out.println("hola get");
        String tarea = request.getParameter("tarea");
        request.getSession().removeAttribute("msjeDatos");
        switch (tarea) {
            case "validarDatos":
                if (usuarioDB.getUsers().isEmpty()) {
                    response.sendRedirect(request.getContextPath() + "/cargaDatos.jsp");
                } else {
                    response.sendRedirect(request.getContextPath() + "/login.jsp");
                }
                break;
            case "subirDatos":
                try {
//            ManejoDatosJSON manejoDatos = (ManejoDatosJSON) request.getSession().getAttribute("manejoDatos");
                if (manejoDatosJSON.getErrores().isEmpty()) {
                    this.cargaDatos.subirDatos(this.manejoDatosJSON);
                    response.sendRedirect(request.getContextPath() + "/login.jsp");
                } else {
                    request.getSession().setAttribute("msjeDatos", "Se debe corregir los errores en la entrada de datos");
                    response.sendRedirect(request.getContextPath() + "/cargaDatos.jsp");
                }
            } catch (ParseException | NullPointerException ex) {
                response.sendRedirect(request.getContextPath() + "/cargaDatos.jsp");
                Logger.getLogger(ControlArchivo.class.getName()).log(Level.SEVERE, null, ex);
            }
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
        System.out.println("hola post");
        try {
            request.getSession().removeAttribute("manejoDatos");
            Part partes = request.getPart("archivo");
            InputStream inputStream = partes.getInputStream();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));

            this.manejoDatosJSON = new ManejoDatosJSON();
            String contenido = this.contenidoArchivoJSON.getContenidioArchivoJSON(buffer);
            System.out.println(contenido);
            this.manejoDatosJSON.procesarInformacion(contenido);

            request.getSession().setAttribute("manejoDatos", this.manejoDatosJSON);
            response.sendRedirect(request.getContextPath() + "/cargaDatos.jsp");
        } catch (ParseException | NullPointerException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(ControlArchivo.class.getName()).log(Level.SEVERE, null, ex);
            response.sendRedirect(request.getContextPath() + "/cargaDatos.jsp");
        }

    }
}
