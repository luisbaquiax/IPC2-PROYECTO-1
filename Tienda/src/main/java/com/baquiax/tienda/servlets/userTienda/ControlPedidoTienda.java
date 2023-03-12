/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.servlets.userTienda;

import com.baquiax.tienda.db.modelo.DetallePedidoDB;
import com.baquiax.tienda.db.modelo.EnvioDB;
import com.baquiax.tienda.db.modelo.PedidoDB;
import com.baquiax.tienda.db.modelo.ProductoDB;
import com.baquiax.tienda.db.modelo.TiendaDB;
import com.baquiax.tienda.entidad.DetallePedido;
import com.baquiax.tienda.entidad.Envio;
import com.baquiax.tienda.entidad.Pedido;
import com.baquiax.tienda.entidad.Producto;
import com.baquiax.tienda.entidad.Tienda;
import com.baquiax.tienda.entidad.Usuario;
import com.baquiax.tienda.entidad.UsuarioTienda;
import com.baquiax.tienda.entidad.enumEntidad.EstadoEnvioEnum;
import com.baquiax.tienda.entidad.enumEntidad.EstadoPedidoEnum;
import com.baquiax.tienda.entidad.enumEntidad.TipoTiendaEnum;
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
@WebServlet(name = "ControlPedidoTienda", urlPatterns = {"/ControlPedidoTienda"})
public class ControlPedidoTienda extends HttpServlet {

    //BASE
    private ProductoDB productoDB;
    private PedidoDB pedidoDB;
    private DetallePedidoDB detallePedidoDB;
    private TiendaDB tiendaDB;
    private EnvioDB envioDB;
    //LISTAS
    private List<Producto> productos;
    private List<Producto> productosAlPedido;
    private List<Envio> envios;
    //atributes
    private Usuario usuario;

    public ControlPedidoTienda() {
        //base
        this.productoDB = new ProductoDB();
        this.pedidoDB = new PedidoDB();
        this.detallePedidoDB = new DetallePedidoDB();
        this.tiendaDB = new TiendaDB();
        this.envioDB = new EnvioDB();
        //listas
        this.productos = this.productoDB.getProductos();
        this.productosAlPedido = new ArrayList<>();
        this.envios = new ArrayList<>();
        //atributes
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
            if (tarea != null) {
                switch (tarea) {
                    case "mostrarMenu":
                        mostrarMendu(request, response, request.getContextPath() + "/JSP/usuarioTienda/crearPedido.jsp");
                        break;
                    case "quitarProducto":
                        quitarProducto(request, response);
                        break;
                    case "reiniciar":
                        reiniciar(request, response);
                        break;
                    case "reealizar":
                        realizarPedido(request, response);
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
                    case "agregarProducto":
                        agregarProductoAlPedido(request, response);
                        break;
                }
            }
        }
    }

    public void mostrarMendu(HttpServletRequest request, HttpServletResponse response, String ruta) throws IOException {
        Tienda tienda = (Tienda) request.getSession().getAttribute("tienda");
        //envios que llegan a una tienda en específico
        this.envios = this.envioDB.getEnvios(tienda.getCodigo(), EstadoEnvioEnum.DESPACHADO.toString());
        request.getSession().setAttribute("productos", productos);
        request.getSession().setAttribute("productosP", productosAlPedido);
        request.getSession().setAttribute("envios", envios);
        response.sendRedirect(ruta);
    }

    private void agregarProductoAlPedido(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            String[] codeName = request.getParameter("codigoProducto").split(",");
            String codigo = codeName[0];
            int cantidad = Integer.parseInt(request.getParameter("cantidadProducto"));
            Producto productoAgregar = getProducto(codigo, productos);
            String msj = "";
            if (productoAgregar.getExistencia() >= cantidad) {
                Producto p = getProducto(codigo, productosAlPedido);
                if (p == null) {
                    productoAgregar.setExistencia(cantidad);
                    this.productosAlPedido.add(productoAgregar);
                    msj = "Producto agregado con éxito";
                } else {
                    //Ya existe en la lista.
                    msj = "Ya existe en la lista";
                }
            } else {
                //La cantidad solicitada es mayor a la cantidad existente
                msj = "La cantidad solicitada es mayor a la cantidad existente";
            }
            request.getSession().setAttribute("msj", msj);
            mostrarMendu(request, response, request.getContextPath() + "/JSP/usuarioTienda/crearPedido.jsp");
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("no existe");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void quitarProducto(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String codigo = request.getParameter("codigo");
        removerProducto(codigo, productosAlPedido);
        request.getSession().setAttribute("msj", "Producto quitado con éxito");
        mostrarMendu(request, response, request.getContextPath() + "/JSP/usuarioTienda/crearPedido.jsp");
    }

    private void reiniciar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.productosAlPedido = new ArrayList<>();
        request.getSession().setAttribute("msj", "");
        mostrarMendu(request, response, request.getContextPath() + "/JSP/usuarioTienda/crearPedido.jsp");
    }

    private void realizarPedido(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //usuario en sesión
        this.usuario = (Usuario) request.getSession().getAttribute("user");
        Tienda tienda = (Tienda) request.getSession().getAttribute("tienda");
        String msj = "";
        if (productosAlPedido.isEmpty()) {
            msj = "Debes agregar productos al pedido.";
        } else {
            if (tienda != null) {
                Pedido pedido = new Pedido(
                        LocalDate.now().toString(),
                        getTotalPedido(productosAlPedido),
                        "",
                        usuario.getCodigo(),
                        tienda.getCodigo(),
                        "",
                        null);
                //validar el tipo de tienda
                if (tienda.getTipo().equalsIgnoreCase(TipoTiendaEnum.NORMAL.getTipo())) {
                    //en caso la tienda no es supervisada
                    pedido.setEstado(EstadoPedidoEnum.SOLICITADO.toString());
                } else if (tienda.getTipo().equalsIgnoreCase(TipoTiendaEnum.SUPERVISADA.getTipo())) {
                    //en caso la tienda es de tipo supervisada
                    pedido.setEstado(EstadoPedidoEnum.PENDIENTE.toString());
                }
                //se guarda el pedido
                if (this.pedidoDB.insert(pedido)) {
                    //el último id_pedio que se ha ingresado
                    int ultimo = this.pedidoDB.getUltimo();
                    System.out.println("id ultimo pedido: " + ultimo);
                    pedido.setDetalle(getDetallePedido(ultimo, productosAlPedido));
                    for (DetallePedido d : pedido.getDetalle()) {
                        this.detallePedidoDB.insert(d);
                    }
                    msj = "Se ha hecho el pedido";
                } else {
                    // no se pudo ingresar el pedido
                    msj = "No se pudo realizar el pedido";
                }
            } else {
                //no se encontró la tienda que está a cargo del usuario_tienda
                msj = "";
            }
        }

        request.getSession().setAttribute("msj", msj);
        mostrarMendu(request, response, request.getContextPath() + "/JSP/usuarioTienda/crearPedido.jsp");
    }

    public Producto getProducto(String codigo, List<Producto> productos) {
        for (Producto p : productos) {
            if (p.getCodigo().equals(codigo)) {
                return p;
            }
        }
        return null;
    }

    private void removerProducto(String codigo, List<Producto> productos) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo().equals(codigo)) {
                productos.remove(i);
                break;
            }
        }
    }

    private double getTotalPedido(List<Producto> productos) {
        double total = 0;
        for (Producto producto : productos) {
            total += producto.getCosto() * producto.getExistencia();
        }
        return total;
    }

    private ArrayList<DetallePedido> getDetallePedido(int idPedido, List<Producto> productos) {
        List<DetallePedido> detalle = new ArrayList<>();
        for (Producto producto : productos) {
            detalle.add(new DetallePedido(
                    idPedido,
                    producto.getCodigo(),
                    producto.getExistencia(),
                    producto.getCosto(),
                    (producto.getCosto() * producto.getExistencia())));
        }
        return (ArrayList<DetallePedido>) detalle;
    }

}
