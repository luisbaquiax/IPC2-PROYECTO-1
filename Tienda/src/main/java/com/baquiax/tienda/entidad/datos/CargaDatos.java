/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.entidad.datos;

import com.baquiax.tienda.db.modelo.BodegaTiendaDB;
import com.baquiax.tienda.db.modelo.CatalogoDB;
import com.baquiax.tienda.db.modelo.DetalleDevolucionDB;
import com.baquiax.tienda.db.modelo.DetalleEnvioDB;
import com.baquiax.tienda.db.modelo.DetalleIncidenciaDB;
import com.baquiax.tienda.db.modelo.DetallePedidoDB;
import com.baquiax.tienda.db.modelo.DevolucionDB;
import com.baquiax.tienda.db.modelo.EnvioDB;
import com.baquiax.tienda.db.modelo.IncidenciaDB;
import com.baquiax.tienda.db.modelo.PedidoDB;
import com.baquiax.tienda.db.modelo.ProductoDB;
import com.baquiax.tienda.db.modelo.TiendaDB;
import com.baquiax.tienda.db.modelo.UsuarioAdministradorDB;
import com.baquiax.tienda.db.modelo.UsuarioBodegaDB;
import com.baquiax.tienda.db.modelo.UsuarioDB;
import com.baquiax.tienda.db.modelo.UsuarioSupervisorDB;
import com.baquiax.tienda.db.modelo.UsuarioTiendaDB;
import com.baquiax.tienda.entidad.*;
import com.baquiax.tienda.entidad.datos.ManejoDatosJSON;
import com.baquiax.tienda.entidad.encripta.Encriptador;
import com.baquiax.tienda.entidad.enumEntidad.TipoUsuarioEnum;
import java.util.List;
import org.json.simple.parser.ParseException;

/**
 *
 * @author luis
 */
public class CargaDatos {

    //base
    private ProductoDB productoDB;
    private TiendaDB tiendaDB;
    private CatalogoDB catalogoDB;
    private UsuarioDB usuarioDB;
    private UsuarioAdministradorDB usuarioAdministradorDB;
    private UsuarioSupervisorDB usuarioSupervisorDB;
    private UsuarioBodegaDB usuarioBodegaDB;
    private BodegaTiendaDB bodegaTiendaDB;
    private UsuarioTiendaDB usuarioTiendaDB;
    private DetalleDevolucionDB detalleDevolucionDB;
    private DevolucionDB devolucionDB;
    private DetalleEnvioDB detalleEnvioDB;
    private EnvioDB envioDB;
    private DetalleIncidenciaDB detalleIncidenciaDB;
    private IncidenciaDB incidenciaDB;
    private DetallePedidoDB detallePedidoDB;
    private PedidoDB pedidoDB;
    //entidad
    private Encriptador encriptador;

    public CargaDatos() {
        //base
        this.productoDB = new ProductoDB();
        this.tiendaDB = new TiendaDB();
        this.catalogoDB = new CatalogoDB();
        this.usuarioDB = new UsuarioDB();
        this.usuarioAdministradorDB = new UsuarioAdministradorDB();
        this.usuarioSupervisorDB = new UsuarioSupervisorDB();
        this.usuarioBodegaDB = new UsuarioBodegaDB();
        this.usuarioTiendaDB = new UsuarioTiendaDB();
        this.bodegaTiendaDB = new BodegaTiendaDB();
        this.detalleDevolucionDB = new DetalleDevolucionDB();
        this.detalleEnvioDB = new DetalleEnvioDB();
        this.detalleIncidenciaDB = new DetalleIncidenciaDB();
        this.detallePedidoDB = new DetallePedidoDB();
        this.devolucionDB = new DevolucionDB();
        this.envioDB = new EnvioDB();
        this.incidenciaDB = new IncidenciaDB();
        this.pedidoDB = new PedidoDB();
        //entidad
        this.encriptador = new Encriptador();
    }

    public void subirDatos(ManejoDatosJSON manejoDatosJSON) throws ParseException {
        subirProductos(manejoDatosJSON.getProductos());
        subirTiendas(manejoDatosJSON.getTiendas());
        subirUsuarios(manejoDatosJSON.getUsuarios(), manejoDatosJSON.getUsuarioBodegas(), manejoDatosJSON.getUsuarioTiendas());
        subirPedidos(manejoDatosJSON.getPedidos());
        subirEnvios(manejoDatosJSON.getEnvios());
        subirIncidencias(manejoDatosJSON.getIncidencias());
        subirDevoluciones(manejoDatosJSON.getDevolucions());
    }

    private void subirProductos(List<Producto> productos) {
        for (Producto producto : productos) {
            this.productoDB.insert(producto);
        }
    }

    private void subirTiendas(List<Tienda> tiendas) {
        for (Tienda tienda : tiendas) {
            if (this.tiendaDB.insert(tienda)) {
                for (Producto p : tienda.getListaProductos()) {
                    this.catalogoDB.insert(new Catalogo(tienda.getCodigo(), p.getCodigo(), p.getExistencia()));
                }
            }
        }
    }

    private void subirUsuarios(List<Usuario> usuarios, List<UsuarioBodega> bodegueros, List<UsuarioTienda> usuariosTienda) {
        for (Usuario usuario : usuarios) {
            usuario.setPassword(encriptador.encriptar(usuario.getPassword()));
            if (this.usuarioDB.insert(usuario)) {
                if (usuario.getTipo().equalsIgnoreCase(TipoUsuarioEnum.USUARIO_ADMIN.getTipo())) {
                    UsuarioAdministrador admin = new UsuarioAdministrador();
                    admin.setCodigo(usuario.getCodigo());
                    this.usuarioAdministradorDB.insert(admin);
                } else if (usuario.getTipo().equalsIgnoreCase(TipoUsuarioEnum.USUARIO_BODEGA.getTipo())) {
                    UsuarioBodega uBodega = new UsuarioBodega();
                    uBodega.setCodigo(usuario.getCodigo());
                    if (this.usuarioBodegaDB.insert(uBodega)) {
                        for (UsuarioBodega userBodega : bodegueros) {
                            if (userBodega.getCodigo().equals(usuario.getCodigo())) {
                                for (Tienda t : userBodega.getTiendas()) {
                                    this.bodegaTiendaDB.insert(new BodegaTienda(userBodega.getCodigo(), t.getCodigo()));
                                }
                                break;
                            }
                        }
                    }
                } else if (usuario.getTipo().equalsIgnoreCase(TipoUsuarioEnum.USUARIO_SUPERVISOR.getTipo())) {
                    UsuarioSupervisor usuarioSupervisor = new UsuarioSupervisor();
                    usuarioSupervisor.setCodigo(usuario.getCodigo());
                    this.usuarioSupervisorDB.insert(usuarioSupervisor);
                } else if (usuario.getTipo().equalsIgnoreCase(TipoUsuarioEnum.USUARIO_TIENDA.getTipo())) {
                    for (UsuarioTienda usuarioTienda : usuariosTienda) {
                        if (usuario.getCodigo().equals(usuarioTienda.getCodigo())) {
                            this.usuarioTiendaDB.insert(usuarioTienda);
                            break;
                        }
                    }
                }
            }
        }
    }

    private void subirPedidos(List<Pedido> pedidos) {
        for (Pedido pedido : pedidos) {
            if (this.pedidoDB.insertFromFile(pedido)) {
                for (DetallePedido detalleP : pedido.getDetalle()) {
                    this.detallePedidoDB.insert(detalleP);
                }
            }
        }
    }

    private void subirEnvios(List<Envio> envios) {
        for (Envio envio : envios) {
            if (this.envioDB.insetFromFile(envio)) {
                for (DetalleEnvio dE : envio.getDetalle()) {
                    this.detalleEnvioDB.insert(dE);
                }
            }
        }
    }

    private void subirIncidencias(List<Incidencia> incidencias) {
        for (Incidencia incidencia : incidencias) {
            if (this.incidenciaDB.insertFromFile(incidencia)) {
                for (DetalleIncidencia dIncidencia : incidencia.getDetalle()) {
                    this.detalleIncidenciaDB.insert(dIncidencia);
                }
            }
        }
    }

    private void subirDevoluciones(List<Devolucion> devolucions) {
        for (Devolucion devolucion : devolucions) {
            if (this.devolucionDB.insetFromFile(devolucion)) {
                for (DetalleDevolucion dDevolucion : devolucion.getDetalle()) {
                    this.detalleDevolucionDB.inset(dDevolucion);
                }
            }
        }
    }
}
