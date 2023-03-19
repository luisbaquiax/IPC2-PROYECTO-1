/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.entidad.datos;

import com.baquiax.tienda.entidad.*;
import com.baquiax.tienda.entidad.enumEntidad.*;
import com.baquiax.tienda.entidad.manejadores.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author luis
 */
public class ManejoDatosJSON {

    //listas
    public List<Producto> productos;
    public List<Tienda> tiendas;
    public List<Usuario> usuarios;
    public List<Devolucion> devolucions;
    public List<Incidencia> incidencias;
    public List<Pedido> pedidos;
    public List<UsuarioTienda> usuarioTiendas;
    public List<UsuarioBodega> usuarioBodegas;
    public List<Envio> envios;

    //errores
    private List<DatoError> errores;
    //entidad
    private ManejoPedido manejoPedido;
    private ManejoProductos manejoProductos;
    private ManejoUsuario manejoUsuario;
    private ManejoTiendas manejoTiendas;
    private ManejoEnvio manejoEnvio;
    private ManejoIncidencias manejoIncidencias;
    private ManejoDevoluciones manejoDevoluciones;

    public ManejoDatosJSON() {
        this.productos = new ArrayList<>();
        this.tiendas = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.devolucions = new ArrayList<>();
        this.incidencias = new ArrayList<>();
        this.pedidos = new ArrayList<>();
        this.usuarioTiendas = new ArrayList<>();
        this.usuarioBodegas = new ArrayList<>();
        this.envios = new ArrayList<>();
        //errores
        this.errores = new ArrayList<>();
        //entidad
        this.manejoPedido = new ManejoPedido();
        this.manejoProductos = new ManejoProductos();
        this.manejoUsuario = new ManejoUsuario();
        this.manejoTiendas = new ManejoTiendas();
        this.manejoEnvio = new ManejoEnvio();
        this.manejoDevoluciones = new ManejoDevoluciones();
        this.manejoIncidencias = new ManejoIncidencias();
    }

    public void procesarInformacion(String contenido) throws ParseException {
        JSONParser parser = new JSONParser();
        Object jsonObj = parser.parse(contenido);

        JSONObject jsonObject = (JSONObject) jsonObj;

        Usuario usuario = new Usuario();
        procesarProductos(jsonObject);
        procesarTiendas(jsonObject);
        procesarAdmins(jsonObject, usuario);
        procesarUsuariosTienda(jsonObject, usuario);
        procesarSupervisores(jsonObject, usuario);
        procesarBodegueros(jsonObject, usuario);
        procesarPedidos(jsonObject);
        procesarEnvios(jsonObject);
        procesarIncidencias(jsonObject);
        procesarDevoluciones(jsonObject);
        System.out.println(Arrays.toString(productos.toArray()));

    }

    private void procesarProductos(JSONObject jsonObject) {
        JSONArray productos = (JSONArray) jsonObject.get("productos");
        System.out.println("productos");
        for (int i = 0; i < productos.size(); i++) {
            JSONObject detalleProducto = (JSONObject) productos.get(i);
            Producto p = null;
            try {
                p = new Producto(
                        detalleProducto.get("codigo").toString(),
                        detalleProducto.get("nombre").toString(),
                        Double.parseDouble(detalleProducto.get("costo").toString()),
                        Double.parseDouble(detalleProducto.get("precio").toString()),
                        Integer.parseInt(detalleProducto.get("existencias").toString()));
                if (manejoProductos.getProducto(p.getCodigo(), this.productos) == null) {
                    this.productos.add(p);
                } else {
                    this.errores.add(new DatoError("Producto repetido", p.toString()));
                }
            } catch (NumberFormatException e) {
                this.errores.add(new DatoError("Formato númerico erróneo",
                        "PRODUCTO: " + detalleProducto.get("codigo").toString()
                        + ", " + detalleProducto.get("nombre").toString()));
            } catch (NullPointerException e) {
                this.errores.add(new DatoError("No existe dato.",
                        "Producto en la posición: " + (i + 1)));
            }
        }
    }

    private void procesarTiendas(JSONObject jsonObject) {
        JSONArray tiendas = (JSONArray) jsonObject.get("tiendas");
        for (int i = 0; i < tiendas.size(); i++) {
            try {
                JSONObject tienda = (JSONObject) tiendas.get(i);
                Tienda t = new Tienda(tienda.get("codigo").toString(), tienda.get("direccion").toString(), tienda.get("tipo").toString());
                JSONArray catalogo = (JSONArray) jsonObject.get("productos");

                for (int j = 0; j < catalogo.size(); j++) {
                    JSONObject producto = (JSONObject) catalogo.get(i);
                    try {
                        Producto p = new Producto();
                        p.setCodigo(producto.get("codigo").toString());
                        p.setExistencia(Integer.parseInt(producto.get("existencias").toString()));
                    } catch (NumberFormatException e) {
                        this.errores.add(new DatoError("Formato númerico erróneo",
                                "Producto de tienda: " + tienda.get("codigo")));
                    } catch (NullPointerException e) {
                        this.errores.add(new DatoError("No existe dato.",
                                "Producto en la posición: " + (j + 1) + " , de la tienda. " + tienda.get("codigo").toString()));
                    }
                }
                if (manejoTiendas.getTienda(t.getCodigo(), this.tiendas) == null) {
                    this.tiendas.add(t);
                } else {
                    this.errores.add(new DatoError("Tienda repetido", t.toString()));
                }
            } catch (NullPointerException e) {
                this.errores.add(new DatoError("No existe dato.",
                        "Tienda en la posición: " + (i + 1)));
            }
        }
    }

    private void procesarAdmins(JSONObject jsonObject, Usuario usuario) {
        JSONArray admins = (JSONArray) jsonObject.get("admins");
        for (int i = 0; i < admins.size(); i++) {
            try {
                JSONObject admin = (JSONObject) admins.get(i);
                usuario = new Usuario(
                        admin.get("codigo").toString(),
                        TipoUsuarioEnum.USUARIO_ADMIN.getTipo().toUpperCase(),
                        admin.get("username").toString(),
                        admin.get("nombre").toString(),
                        admin.get("password").toString(),
                        true,
                        "");
                if (manejoUsuario.getUser(usuario.getCodigo(), this.usuarios) == null) {
                    this.usuarios.add(usuario);
                } else {
                    this.errores.add(new DatoError("Usuario repetido", usuario.toString()));
                }
            } catch (NullPointerException e) {
                this.errores.add(new DatoError("No existe dato.",
                        "Usuario administrador en la posición: " + (i + 1)));
            }
        }
    }

    private void procesarUsuariosTienda(JSONObject jsonObject, Usuario usuario) {
        System.out.println("usuariostienda");
        JSONArray usuariostienda = (JSONArray) jsonObject.get("usuariostienda");
        for (int i = 0; i < usuariostienda.size(); i++) {
            try {
                JSONObject usuarioTienda = (JSONObject) usuariostienda.get(i);
                usuario = new Usuario(
                        usuarioTienda.get("codigo").toString(),
                        TipoUsuarioEnum.USUARIO_TIENDA.getTipo().toUpperCase(),
                        usuarioTienda.get("username").toString(),
                        usuarioTienda.get("nombre").toString(),
                        usuarioTienda.get("password").toString(),
                        true,
                        usuarioTienda.get("email").toString());
                if (manejoUsuario.getUser(usuario.getCodigo(), this.usuarios) == null) {
                    this.usuarios.add(usuario);
                    UsuarioTienda uTienda = new UsuarioTienda();
                    uTienda.setCodigo(usuario.getCodigo());
                    uTienda.setCodigoTienda(usuarioTienda.get("tienda").toString());
                    this.usuarioTiendas.add(uTienda);
                } else {
                    this.errores.add(new DatoError("Usuario repetido", usuario.toString()));
                }

            } catch (NullPointerException e) {
                this.errores.add(new DatoError("No existe dato.",
                        "Usuario tienda en la posición: " + (i + 1)));
            }
        }
    }

    private void procesarSupervisores(JSONObject jsonObject, Usuario usuario) {

        JSONArray supervisores = (JSONArray) jsonObject.get("supervisores");
        for (int i = 0; i < supervisores.size(); i++) {
            try {
                JSONObject supervisor = (JSONObject) supervisores.get(i);
                usuario = new Usuario(
                        supervisor.get("codigo").toString(),
                        TipoUsuarioEnum.USUARIO_SUPERVISOR.getTipo().toUpperCase(),
                        supervisor.get("username").toString(),
                        supervisor.get("nombre").toString(),
                        supervisor.get("password").toString(),
                        true,
                        supervisor.get("email").toString());
                if (manejoUsuario.getUser(usuario.getCodigo(), this.usuarios) == null) {
                    this.usuarios.add(usuario);
                } else {
                    this.errores.add(new DatoError("Usuario repetido", usuario.toString()));
                }
            } catch (NullPointerException e) {
                this.errores.add(new DatoError("No existe dato.",
                        "Usuario supervisor en la posición: " + (i + 1)));
            }
        }
    }

    private void procesarBodegueros(JSONObject jsonObject, Usuario usuario) {
        JSONArray bodegueros = (JSONArray) jsonObject.get("encargadosBodega");
        for (int i = 0; i < bodegueros.size(); i++) {
            try {
                JSONObject bodeguero = (JSONObject) bodegueros.get(i);
                usuario = new Usuario(
                        bodeguero.get("codigo").toString(),
                        TipoUsuarioEnum.USUARIO_BODEGA.getTipo().toUpperCase(),
                        bodeguero.get("username").toString(),
                        bodeguero.get("nombre").toString(),
                        bodeguero.get("password").toString(),
                        true,
                        bodeguero.get("email").toString());

                UsuarioBodega uBodega = new UsuarioBodega();
                uBodega.setCodigo(usuario.getCodigo());

                List<Tienda> tBoedga = new ArrayList<>();
                uBodega.setTiendas((ArrayList<Tienda>) tBoedga);

                JSONArray tiendasB = (JSONArray) bodeguero.get("tiendas");
                for (int j = 0; j < tiendasB.size(); j++) {
                    System.out.println();
                    Tienda tU = new Tienda();
                    tU.setCodigo(tiendasB.get(j).toString());
                    if (manejoTiendas.getTienda(tiendasB.get(j).toString(), this.tiendas) != null) {
                        for (UsuarioBodega usBodega : this.usuarioBodegas) {
                            if (manejoTiendas.getTienda(tiendasB.get(j).toString(), uBodega.getTiendas()) == null) {
                                uBodega.getTiendas().add(tU);
                                break;
                            } else {
                                this.errores.add(new DatoError("Tienda asiganada a bodeguero.", "La tienda ya le pertenece a otro usuario."));
                                break;
                            }
                        }
                    } else {
                        this.errores.add(new DatoError("No existe dato", "La tienda no existe en la lista de tiendas"));
                    }
                }
                if (manejoUsuario.getUser(usuario.getCodigo(), this.usuarios) == null) {
                    this.usuarios.add(usuario);
                    this.usuarioBodegas.add(uBodega);
                } else {
                    this.errores.add(new DatoError("Usuario repetido", usuario.toString()));
                }
            } catch (NullPointerException e) {
                this.errores.add(new DatoError("No existe dato.",
                        "Usuario de bodega en la posición: " + (i + 1)));
            }
        }
    }

    private void procesarPedidos(JSONObject jsonObject) {
        JSONArray pedidos = (JSONArray) jsonObject.get("pedidos");
        for (int i = 0; i < pedidos.size(); i++) {
            try {
                JSONObject pedido = (JSONObject) pedidos.get(i);
                Pedido p = new Pedido(
                        Integer.parseInt(pedido.get("id").toString()),
                        pedido.get("fecha").toString(),
                        Double.parseDouble(pedido.get("total").toString()),
                        pedido.get("estado").toString(),
                        pedido.get("usuario").toString(),
                        pedido.get("tienda").toString(),
                        "");
                ArrayList<DetallePedido> dps = new ArrayList<>();
                p.setDetalle(dps);
                JSONArray productosPedido = (JSONArray) pedido.get("productos");
                for (int j = 0; j < productosPedido.size(); j++) {
                    try {
                        JSONObject productoPedido = (JSONObject) productosPedido.get(j);
                        dps.add(new DetallePedido(
                                p.getId(),
                                productoPedido.get("codigo").toString(),
                                Integer.parseInt(productoPedido.get("cantidad").toString()),
                                Double.parseDouble(productoPedido.get("costoU").toString()),
                                Double.parseDouble(productoPedido.get("costoTotal").toString())));

                    } catch (NumberFormatException e) {
                        this.errores.add(new DatoError("Fómato númerico inválido",
                                "Producto en pedido: " + p.getId()));
                    } catch (NullPointerException e) {
                        this.errores.add(new DatoError("No existe dato.",
                                "Producto en pedido: " + p.getId()));
                    }

                }
                if (manejoPedido.getPedido(p.getId(), this.pedidos) == null) {
                    this.pedidos.add(p);
                } else {
                    this.errores.add(new DatoError("Pedido repetido", p.toString()));
                }
            } catch (NumberFormatException e) {
                this.errores.add(new DatoError("Fómato númerico inválido",
                        "Pedido en la posición: " + (i + 1)));
            } catch (NullPointerException e) {
                this.errores.add(new DatoError("FNo existe dato",
                        "Pedido en la posición: " + (i + 1)));
            }
        }
    }

    private void procesarEnvios(JSONObject jsonObject) {
        System.out.println("envios");
        JSONArray envios = (JSONArray) jsonObject.get("envios");
        for (int i = 0; i < envios.size(); i++) {
            try {
                JSONObject envio = (JSONObject) envios.get(i);
                Envio en = new Envio(
                        Integer.parseInt(envio.get("id").toString()),
                        envio.get("fechaSalida").toString(),
                        envio.get("fechaRecibido").toString(),
                        Double.parseDouble(envio.get("total").toString()),
                        envio.get("estado").toString(),
                        envio.get("tienda").toString(),
                        envio.get("usuario").toString());

                JSONArray productosEnvio = (JSONArray) envio.get("productos");
                ArrayList<DetalleEnvio> pEnvio = new ArrayList<>();
                en.setDetalle(pEnvio);

                for (int j = 0; j < productosEnvio.size(); j++) {
                    JSONObject productoEnvio = (JSONObject) productosEnvio.get(j);
                    try {
                        pEnvio.add(new DetalleEnvio(
                                en.getId(),
                                productoEnvio.get("codigo").toString(),
                                Integer.parseInt(productoEnvio.get("cantidad").toString()),
                                Double.parseDouble(productoEnvio.get("costoU").toString()),
                                Double.parseDouble(productoEnvio.get("costoTotal").toString())));
                    } catch (NumberFormatException ex) {
                        this.errores.add(new DatoError("Fómato númerico inválido",
                                "Producto del envio. ID envío: " + en.getId()));
                    } catch (NullPointerException e2) {
                        this.errores.add(new DatoError("FNo existe dato",
                                "Producto del envio. ID envío: " + en.getId()));
                    }
                }
                if (manejoEnvio.getEnvio(en.getId(), this.envios) == null) {
                    this.envios.add(en);
                } else {
                    this.errores.add(new DatoError("Envio repetido", en.toString()));
                }
            } catch (NumberFormatException e) {
                this.errores.add(new DatoError("Fómato númerico inválido",
                        "Envio en la posición: " + (i + 1)));
            } catch (NullPointerException e) {
                this.errores.add(new DatoError("FNo existe dato",
                        "Envio en la posición: " + (i + 1)));
            }

        }
    }

    private void procesarIncidencias(JSONObject jsonObject) {
        System.out.println("incidencias");
        JSONArray incidencias = (JSONArray) jsonObject.get("incidencias");
        for (int i = 0; i < incidencias.size(); i++) {
            try {
                JSONObject incidencia = (JSONObject) incidencias.get(i);

                System.out.println(incidencia.get("solucion"));

                Incidencia inc = new Incidencia(
                        Integer.parseInt(incidencia.get("id").toString()),
                        incidencia.get("fecha").toString(),
                        incidencia.get("estado").toString(),
                        incidencia.get("usuario").toString(),
                        incidencia.get("tienda").toString());

                ArrayList<DetalleIncidencia> pIncidencia = new ArrayList<>();
                inc.setDetalle(pIncidencia);

                JSONArray productosIncidencia = (JSONArray) incidencia.get("productos");
                for (int j = 0; j < productosIncidencia.size(); j++) {
                    JSONObject productoIncidencia = (JSONObject) productosIncidencia.get(j);
                    try {
                        pIncidencia.add(new DetalleIncidencia(
                                inc.getId(),
                                productoIncidencia.get("codigo").toString(),
                                Integer.parseInt(productoIncidencia.get("cantidad").toString()),
                                productoIncidencia.get("motivo").toString()));
                    } catch (NumberFormatException e) {
                        this.errores.add(new DatoError("Formato numérico erróneo", "Detalle de incidencia: " + (j + 1)));
                    } catch (NullPointerException e) {
                        this.errores.add(new DatoError("No existe el dato", "Detalle de incidencia: " + (j + 1)));
                    }
                }
                if (manejoIncidencias.getIncidencia(inc.getId(), this.incidencias) == null) {
                    this.incidencias.add(inc);
                } else {
                    this.errores.add(new DatoError("Incidencia reptida.", inc.toString()));
                }

            } catch (NumberFormatException e) {
                this.errores.add(new DatoError("Formato numérico inválido.", "Incidencia en la posición:  " + (i + 1)));
            } catch (NullPointerException e) {
                this.errores.add(new DatoError("No existe dato de la incidencia", "Incidencia en la posición:  " + (i + 1)));
            }
        }
    }

    private void procesarDevoluciones(JSONObject jsonObject) {
        JSONArray devoluciones = (JSONArray) jsonObject.get("devoluciones");
        for (int i = 0; i < devoluciones.size(); i++) {

            try {
                JSONObject devolucion = (JSONObject) devoluciones.get(i);
                Devolucion dev = new Devolucion(
                        Integer.parseInt(devolucion.get("id").toString()),
                        devolucion.get("fecha").toString(),
                        devolucion.get("estado").toString(),
                        Double.parseDouble(devolucion.get("total").toString()),
                        devolucion.get("usuario").toString(),
                        devolucion.get("tienda").toString());
                ArrayList<DetalleDevolucion> listDetalle = new ArrayList<>();
                dev.setDetalle(listDetalle);

                JSONArray productosDevolucion = (JSONArray) devolucion.get("productos");
                for (int j = 0; j < productosDevolucion.size(); j++) {
                    try {
                        JSONObject productoDevolucion = (JSONObject) productosDevolucion.get(j);
                        listDetalle.add(new DetalleDevolucion(
                                dev.getId(),
                                productoDevolucion.get("codigo").toString(),
                                Double.parseDouble(productoDevolucion.get("costo").toString()),
                                Integer.parseInt(productoDevolucion.get("cantidad").toString()),
                                productoDevolucion.get("motivo").toString(),
                                Double.parseDouble(productoDevolucion.get("costoTotal").toString())));
                    } catch (NumberFormatException e) {
                        this.errores.add(new DatoError("Formato numérico erróneo", "Detalle de devolución: " + (j + 1)));
                    } catch (NullPointerException e) {
                        this.errores.add(new DatoError("No existe el dato", "Detalle de devolución: " + (j + 1)));
                    }
                }
                if (manejoDevoluciones.getDevolucion(dev.getId(), this.devolucions) == null) {
                    this.devolucions.add(dev);
                } else {
                    this.errores.add(new DatoError("Devolución repetida.", dev.toString()));
                }
            } catch (NumberFormatException e) {
                this.errores.add(new DatoError("Formato numérico inválido.", "Devolución en la posición:  " + (i + 1)));
            } catch (NullPointerException e) {
                this.errores.add(new DatoError("No existe dato de la incidencia", "Devolución en la posición:  " + (i + 1)));
            }
        }
    }

}
