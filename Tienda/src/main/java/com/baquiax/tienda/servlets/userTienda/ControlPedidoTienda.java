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
import com.baquiax.tienda.entidad.EstadosTipos.EstadoPedido;
import com.baquiax.tienda.entidad.Pedido;
import com.baquiax.tienda.entidad.Producto;
import com.baquiax.tienda.entidad.Tienda;
import com.baquiax.tienda.entidad.Usuario;
import com.baquiax.tienda.entidad.enumEntidad.EstadoEnvioEnum;
import com.baquiax.tienda.entidad.enumEntidad.EstadoPedidoEnum;
import com.baquiax.tienda.entidad.enumEntidad.TipoTiendaEnum;
import com.baquiax.tienda.entidad.manejadores.ManejoProductos;
import java.io.IOException;
import java.time.LocalDate;
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
    private List<Pedido> pedidos;
    private List<DetallePedido> detalleProductosPedido;
    //entidad
    private Usuario usuario;
    private ManejoProductos manejoProductos;
    private String msjDetallePedidoRechazado;
    private String msjPedidoRechazo;

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
        this.pedidos = new ArrayList<>();
        this.detalleProductosPedido = new ArrayList<>();
        //atributes
        this.manejoProductos = new ManejoProductos();
        //
        this.msjDetallePedidoRechazado = "msjDetallePedidoRechazado";
        this.msjPedidoRechazo = "msjPedidoRechazo";
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
                    case "realizar":
                        realizarPedido(request, response);
                        break;
                    case "listarPedidosRechazados":
                        listarPedidosRechazados(request, response);
                        break;
                    case "editarPedido":
                        editarPedido(request, response);
                        break;
                    case "quitarDetallePedido":
                        quitarDetallePedido(request, response);
                        break;
                    case "guardarCambios":
                        guardarCambios(request, response);
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
                    case "agregarProductoPedidoRechazado":
                        agregarProductoPedidoRechazado(request, response);
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
            Producto productoAgregar = this.manejoProductos.getProducto(codigo, productos);
            String msj = "";
            if (productoAgregar.getExistencia() >= cantidad) {
                Producto p = this.manejoProductos.getProducto(codigo, productosAlPedido);
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
        this.manejoProductos.removerProducto(codigo, productosAlPedido);
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
                        this.manejoProductos.getTotalPedido(productosAlPedido),
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
        this.productosAlPedido = new ArrayList<>();
        request.getSession().setAttribute("msj", msj);
        mostrarMendu(request, response, request.getContextPath() + "/JSP/usuarioTienda/crearPedido.jsp");
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

    private void listarPedidosRechazados(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Tienda tienda = (Tienda) request.getSession().getAttribute("tienda");
        this.pedidos = this.pedidoDB.getPedidosByTiendaByEstado(tienda.getCodigo(), EstadoPedidoEnum.RECHAZADO.toString());

        request.getSession().setAttribute("pedidosRechazados", this.pedidos);
        response.sendRedirect(request.getContextPath() + "/JSP/usuarioTienda/pedidosRechazados.jsp");
    }

    private void editarPedido(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idPedido = Integer.parseInt(request.getParameter("id"));
        Pedido p = new Pedido();
        p.setId(idPedido);
        this.detalleProductosPedido = this.detallePedidoDB.getDetallePedidoByIdPedido(p);

        request.getSession().setAttribute("idPedidoRechazado", p.getId());
        request.getSession().setAttribute("productosPedido", detalleProductosPedido);
        response.sendRedirect(request.getContextPath() + "/JSP/usuarioTienda/editarPedido.jsp");
    }

    private void quitarDetallePedido(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String codigoProducto = request.getParameter("codigo");
        Pedido pedidoRechazado = new Pedido();
        pedidoRechazado.setId(id);
        DetallePedido detallePedido = new DetallePedido();
        detallePedido.setIdPedido(id);
        detallePedido.setCodigoProducto(codigoProducto);
        System.out.println(pedidoRechazado.toString());
        if (detallePedidoDB.getDetallePedidoByIdPedido(pedidoRechazado).size() > 1) {
            this.detallePedidoDB.delete(detallePedido);
            request.getSession().setAttribute(msjDetallePedidoRechazado, "Se ha realizado la acción exitosamente.");
        } else {
            request.getSession().setAttribute(msjDetallePedidoRechazado, "El pedido debe tener al menos un producto.");
        }
        this.detalleProductosPedido = this.detallePedidoDB.getDetallePedidoByIdPedido(pedidoRechazado);
        request.getSession().setAttribute("productosPedido", this.detalleProductosPedido);
        response.sendRedirect(request.getContextPath() + "/JSP/usuarioTienda/editarPedido.jsp");
    }

    private void agregarProductoPedidoRechazado(HttpServletRequest request, HttpServletResponse response) {
        try {
            String[] codeName = request.getParameter("codigoProducto").split(",");
            String codigo = codeName[0];
            int cantidad = Integer.parseInt(request.getParameter("cantidadProducto"));

            int idPedido = (Integer) request.getSession().getAttribute("idPedidoRechazado");

            Producto productoAgregar = this.manejoProductos.getProducto(codigo, productos);
            System.out.println(productoAgregar.toString());
            String msj = "";
            if (productoAgregar.getExistencia() >= cantidad) {
                DetallePedido d = getDetallePedido(codigo, this.detalleProductosPedido);
                if (d == null) {
                    productoAgregar.setExistencia(cantidad);
                    this.detalleProductosPedido.add(
                            new DetallePedido(
                                    idPedido,
                                    codigo,
                                    cantidad,
                                    productoAgregar.getPrecio(),
                                    cantidad * productoAgregar.getPrecio()));
                    msj = "Producto agregado con éxito";
                } else {
                    //Ya existe en la lista.
                    msj = "Ya existe en la lista";
                }
            } else {
                //La cantidad solicitada es mayor a la cantidad existente
                msj = "La cantidad solicitada es mayor a la cantidad existente";
            }
            request.getSession().setAttribute(msjDetallePedidoRechazado, msj);
            request.getSession().setAttribute("productosPedido", this.detalleProductosPedido);
            response.sendRedirect(request.getContextPath() + "/JSP/usuarioTienda/editarPedido.jsp");
        } catch (NumberFormatException | NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void guardarCambios(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idPedido = (Integer) request.getSession().getAttribute("idPedidoRechazado");
        Pedido pedido = new Pedido();
        pedido.setId(idPedido);
        if (detalleProductosPedido.isEmpty()) {
            request.getSession().setAttribute(msjDetallePedidoRechazado, "El pedio no debe estar vacío.");
            request.getSession().setAttribute("productosPedido", this.detalleProductosPedido);
            response.sendRedirect(request.getContextPath() + "/JSP/usuarioTienda/editarPedido.jsp");
        } else {
            for (DetallePedido d : this.detalleProductosPedido) {
                for (DetallePedido dp : this.detallePedidoDB.getDetallePedidoByIdPedido(pedido)) {
                    if (d.getCodigoProducto().equals(dp.getCodigoProducto())) {
                        this.detallePedidoDB.update(d);
                        break;
                    } else {
                        this.detallePedidoDB.insert(d);
                        break;
                    }
                }
            }
            pedido.setEstado(EstadoPedidoEnum.PENDIENTE.toString());
            pedido.setTotal(getTotal(this.detalleProductosPedido));
            pedido.setFecha(LocalDate.now().toString());
            this.pedidoDB.update(pedido);
            request.getSession().removeAttribute(msjDetallePedidoRechazado);
            request.getSession().setAttribute(msjPedidoRechazo, "Se guardó los cambios");
            listarPedidosRechazados(request, response);
        }

    }

    private double getTotal(List<DetallePedido> detallePedidos) {
        double total = 0;
        for (DetallePedido detallePedido : detallePedidos) {
            total += detallePedido.getSubtotal();
        }
        return total;
    }

    private DetallePedido getDetallePedido(String codigoProducto, List<DetallePedido> detallePedidos) {
        for (DetallePedido detallePedido : detallePedidos) {
            if (detallePedido.getCodigoProducto().equals(codigoProducto)) {
                return detallePedido;
            }
        }
        return null;
    }

}
