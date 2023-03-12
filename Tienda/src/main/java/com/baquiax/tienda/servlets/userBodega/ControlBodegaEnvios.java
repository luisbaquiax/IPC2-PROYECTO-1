/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.servlets.userBodega;

import com.baquiax.tienda.db.modelo.DetalleEnvioDB;
import com.baquiax.tienda.db.modelo.DetallePedidoDB;
import com.baquiax.tienda.db.modelo.EnvioDB;
import com.baquiax.tienda.db.modelo.PedidoDB;
import com.baquiax.tienda.db.modelo.ProductoDB;
import com.baquiax.tienda.entidad.DetalleEnvio;
import com.baquiax.tienda.entidad.DetallePedido;
import com.baquiax.tienda.entidad.Envio;
import com.baquiax.tienda.entidad.Pedido;
import com.baquiax.tienda.entidad.Producto;
import com.baquiax.tienda.entidad.Usuario;
import com.baquiax.tienda.entidad.enumEntidad.EstadoEnvioEnum;
import com.baquiax.tienda.entidad.enumEntidad.EstadoPedidoEnum;
import com.baquiax.tienda.servlets.userTienda.ControlPedidoTienda;
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
@WebServlet(name = "ControlBodegaEnvios", urlPatterns = {"/ControlBodegaEnvios"})
public class ControlBodegaEnvios extends HttpServlet {

    //base
    private PedidoDB pedidoDB;
    private EnvioDB envioDB;
    private DetallePedidoDB detallePedidoDB;
    private DetalleEnvioDB detalleEnvioDB;
    private ProductoDB productoDB;
    //servlet
    private ControlPedidoTienda controlPedidoTienda;
    //entidad
    private List<Pedido> listaPedidos;
    private List<Producto> listaProductos;
    private List<DetallePedido> listaDetallePedido;
    private Pedido pedido;
    //
    private String msjedicionPedidoSolicitado;

    public ControlBodegaEnvios() {
        //base
        this.pedidoDB = new PedidoDB();
        this.envioDB = new EnvioDB();
        this.detalleEnvioDB = new DetalleEnvioDB();
        this.detallePedidoDB = new DetallePedidoDB();
        this.productoDB = new ProductoDB();
        //servlet
        this.controlPedidoTienda = new ControlPedidoTienda();
        //entidad
        this.listaPedidos = new ArrayList<>();
        this.listaProductos = this.productoDB.getProductos();
        this.listaDetallePedido = new ArrayList<>();
        this.pedido = new Pedido();
        //
        msjedicionPedidoSolicitado = "";
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
            case "listarPedidosSolicitados":
                listarPedidosSolicitados(request, response);
                break;
            case "realizarEnvio":
                realizarEnvio(request, response);
                break;
            case "modificarEnvio":
                modificarEnvio(request, response);
                break;
            case "quitarProductoEnvio":
                quitarProductoEnvio(request, response);
                break;
            case "openModalEditProductoSolicitado":
                openModalEditProductoSolicitado(request, response);
                break;
            case "realizarEnvioModificado":
                realizarEnvioModificado(request, response);
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
            case "agregarProductoAlEnvio":
                agregarProductoAlEnvio(request, response);
                break;
            case "pedidosSolicitadosPorTienda":
                pedidosSolicitadosPorTienda(request, response);
                break;
            case "modificarCantidad":
                modificarCantidad(request, response);
                break;
        }
    }

    private void listarPedidosSolicitados(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Usuario usuario = (Usuario) request.getSession().getAttribute("user");
        this.listaPedidos = this.pedidoDB.getPedidosByUsuarioBodegaByEstado(usuario.getCodigo(), EstadoPedidoEnum.SOLICITADO.toString());

        request.getSession().setAttribute("pedidosSolicitados", this.listaPedidos);
        response.sendRedirect(request.getContextPath() + "/JSP/bodega/pedidosSolicitados.jsp");
    }

    private void pedidosSolicitadosPorTienda(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Usuario usuario = (Usuario) request.getSession().getAttribute("user");
        String codigoTienda = request.getParameter("tienda");
        this.listaPedidos = this.pedidoDB.getPedidosByTiendaAndUsuarioAndEstadoPedido(usuario.getCodigo(), codigoTienda, EstadoPedidoEnum.SOLICITADO.toString());

        request.getSession().setAttribute("pedidosSolicitados", this.listaPedidos);
        response.sendRedirect(request.getContextPath() + "/JSP/bodega/pedidosSolicitados.jsp");
    }

    private void realizarEnvio(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idPedido = Integer.parseInt(request.getParameter("id"));
        Usuario usuario = (Usuario) request.getSession().getAttribute("user");
        //se busca el pedido en la lista-pedidos
        this.pedido = getPedido(idPedido, listaPedidos);
        //se crea un envio
        Envio envio = new Envio(
                LocalDate.now().toString(),
                LocalDate.now().toString(),
                pedido.getTotal(),
                EstadoEnvioEnum.DESPACHADO.toString(),
                pedido.getCodigoTienda(),
                usuario.getCodigo(),
                null);
        this.listaDetallePedido = this.detallePedidoDB.getDetallePedidoByIdPedido(pedido);
        //
        this.pedido.setDetalle((ArrayList<DetallePedido>) listaDetallePedido);
        ingresarEnvio(this.pedido.getDetalle(), envio, request, response);
    }

    private void realizarEnvioModificado(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idPedido = Integer.parseInt(request.getParameter("id"));
        Usuario usuario = (Usuario) request.getSession().getAttribute("user");
        //se busca el pedido en la lista-pedidos
        this.pedido = getPedido(idPedido, listaPedidos);
        //se crea un envio
        Envio envio = new Envio(
                LocalDate.now().toString(),
                LocalDate.now().toString(),
                pedido.getTotal(),
                EstadoEnvioEnum.DESPACHADO.toString(),
                pedido.getCodigoTienda(),
                usuario.getCodigo(),
                null);
        ingresarEnvio(this.listaDetallePedido, envio, request, response);
    }

    private void ingresarEnvio(List<DetallePedido> listaDetallePedido, Envio envio, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //se ingresa el envio
        if (envioDB.inset(envio)) {
            int ultimo = this.envioDB.getUltimoID();
            System.out.println("id ultimo envio " + ultimo);
            System.out.println(listaDetallePedido.toString());
            //se ingresa los productos del envio
            for (int i = 0; i < listaDetallePedido.size(); i++) {
                DetalleEnvio detalleEnvio = new DetalleEnvio(
                        ultimo,
                        listaDetallePedido.get(i).getCodigoProducto(),
                        listaDetallePedido.get(i).getCantidad(),
                        listaDetallePedido.get(i).getPrecioUnitario(),
                        listaDetallePedido.get(i).getCantidad() * listaDetallePedido.get(i).getPrecioUnitario());
                detalleEnvioDB.insert(detalleEnvio);

            }
            //se cambia el estado del envio a despachado
            envio.setEstado(EstadoEnvioEnum.DESPACHADO.toString());
            //se cambia el estado del pedido a enviado
            pedido.setEstado(EstadoPedidoEnum.ENVIADO.toString());
            //se actualiza la cantidad de productos en el catálogo general
            for (DetallePedido detallePedido : listaDetallePedido) {
                for (Producto p : listaProductos) {
                    if (p.getCodigo().equals(detallePedido.getCodigoProducto())) {
                        p.setExistencia(p.getExistencia() - detallePedido.getCantidad());
                        this.productoDB.update(p);
                        break;
                    }
                }
            }
            //se actualizan los datos
            this.envioDB.update(envio);
            this.pedidoDB.update(pedido);
            request.getSession().setAttribute("msjBodega", "Se ha hecho el envío.");
            listarPedidosSolicitados(request, response);
        } else {
            request.getSession().setAttribute("msjBodega", "No se ha podido realizar la acción, lo sentimos.");
            response.sendRedirect(request.getContextPath() + "/JSP/bodega/pedidosSolicitados.jsp");
        }
    }

    /**
     * Modificar el pedido antes de enviar
     *
     * @param request
     * @param response
     */
    private void modificarEnvio(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().removeAttribute("msjedicionPedidoSolicitado");
        int idPedido = Integer.parseInt(request.getParameter("id"));
        this.pedido = getPedido(idPedido, listaPedidos);
        this.listaDetallePedido = this.detallePedidoDB.getDetallePedidoByIdPedido(pedido);

        request.getSession().setAttribute("pedidoSolicitado", this.pedido);
        request.getSession().setAttribute("productosPedido", this.listaDetallePedido);
        request.getSession().setAttribute("listaProductos", listaProductos);
        response.sendRedirect(request.getContextPath() + "/JSP/bodega/edicionPedidoSolicitado.jsp");
    }

    private void agregarProductoAlEnvio(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] datos = request.getParameter("codigoProducto").split(",");
        String codigoProducto = datos[0];
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));

        if (controlPedidoTienda.getProducto(codigoProducto, listaProductos) != null) {
            if (controlPedidoTienda.getProducto(codigoProducto, listaProductos).getExistencia() >= cantidad) {
                //agregamos el producto al detalle
                for (DetallePedido d : this.listaDetallePedido) {
                    if (d.getCodigoProducto().equalsIgnoreCase(codigoProducto)) {
                        request.getSession().setAttribute("msjedicionPedidoSolicitado", "El producto ya existe en el envío.");
                        break;
                    } else {
                        listaDetallePedido.add(
                                new DetallePedido(
                                        pedido.getId(),
                                        codigoProducto,
                                        cantidad,
                                        controlPedidoTienda.getProducto(codigoProducto, this.listaProductos).getPrecio(),
                                        cantidad * controlPedidoTienda.getProducto(codigoProducto, this.listaProductos).getPrecio()));
                        break;
                    }
                }
            } else {
                request.getSession().setAttribute("msjedicionPedidoSolicitado", "La cantidad solicitada es mayor a la existencias del producto.");
            }
        } else {
            request.getSession().setAttribute("msjedicionPedidoSolicitado", "Producto no encontrado.");
        }

        request.getSession().setAttribute("productosPedido", this.listaDetallePedido);
        request.getSession().setAttribute("listaProductos", listaProductos);
        response.sendRedirect(request.getContextPath() + "/JSP/bodega/edicionPedidoSolicitado.jsp");

    }

    private void quitarProductoEnvio(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String codigoProdcuto = request.getParameter("codigoProducto");
        if (listaDetallePedido.size() > 1) {
            for (int i = 0; i < listaDetallePedido.size(); i++) {
                if (listaDetallePedido.get(i).getCodigoProducto().equals(codigoProdcuto)) {
                    listaDetallePedido.remove(listaDetallePedido.get(i));
                    break;
                }
            }
        } else {
            request.getSession().setAttribute("msjedicionPedidoSolicitado", "La lista productos del envío no debe ser vacía.");
        }
        request.getSession().setAttribute("productosPedido", this.listaDetallePedido);
        request.getSession().setAttribute("listaProductos", listaProductos);
        response.sendRedirect(request.getContextPath() + "/JSP/bodega/edicionPedidoSolicitado.jsp");
    }

    private void openModalEditProductoSolicitado(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String codigoProducto = request.getParameter("codigoProducto");
        DetallePedido detalle = getDetallePedido(listaDetallePedido, codigoProducto);
        request.getSession().setAttribute("producto", detalle);
        response.sendRedirect(request.getContextPath() + "/JSP/bodega/modalEditCantidadProductoPedido.jsp");

    }

    private void modificarCantidad(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String codigo = request.getParameter("codigo");
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));
        if (cantidad > 0) {
            for (DetallePedido d : listaDetallePedido) {
                if (d.getCodigoProducto().equals(codigo)) {
                    for (Producto p : listaProductos) {
                        if (cantidad > p.getExistencia()) {
                            request.getSession().setAttribute("msjedicionPedidoSolicitado", "La cantidad del producto solicitada no existe en la bodega");
                            break;
                        } else {
                            d.setCantidad(cantidad);
                            d.setSubtotal(d.getCantidad() * d.getPrecioUnitario());
                            break;
                        }
                    }

                }
            }
        } else {
            request.getSession().setAttribute("msjedicionPedidoSolicitado", "La cantidad debe ser mayor a 0");
        }
        request.getSession().setAttribute("productosPedido", this.listaDetallePedido);
        request.getSession().setAttribute("listaProductos", listaProductos);
        response.sendRedirect(request.getContextPath() + "/JSP/bodega/edicionPedidoSolicitado.jsp");

    }

    private Pedido getPedido(int id, List<Pedido> pedidos) {
        for (Pedido pedido : pedidos) {
            if (pedido.getId() == id) {
                return pedido;
            }
        }
        return null;
    }

    public DetallePedido getDetallePedido(List<DetallePedido> listaDetallePedidos, String codigoProducto) {
        for (DetallePedido detalle : listaDetallePedidos) {
            if (detalle.getCodigoProducto().equals(codigoProducto)) {
                return detalle;
            }
        }
        return null;
    }

}
