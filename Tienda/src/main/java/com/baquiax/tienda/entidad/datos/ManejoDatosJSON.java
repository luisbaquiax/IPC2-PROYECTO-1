/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.entidad.datos;

import com.baquiax.tienda.entidad.*;
import com.baquiax.tienda.entidad.enumEntidad.*;
import com.baquiax.tienda.entidad.manejadores.*;
import java.sql.Date;
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
    private List<Producto> productos;
    private List<Tienda> tiendas;
    private List<Usuario> usuarios;
    private List<Devolucion> devolucions;
    private List<Incidencia> incidencias;
    private List<Pedido> pedidos;
    private List<UsuarioTienda> usuarioTiendas;
    private List<UsuarioBodega> usuarioBodegas;
    private List<Envio> envios;

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
        System.out.println(Arrays.toString(getProductos().toArray()));
        System.out.println(Arrays.toString(getTiendas().toArray()));
        System.out.println(Arrays.toString(getUsuarios().toArray()));
        System.out.println(Arrays.toString(getUsuarioBodegas().toArray()));
        System.out.println(Arrays.toString(getProductos().toArray()));
        System.out.println(Arrays.toString(getPedidos().toArray()));
        System.out.println(Arrays.toString(getEnvios().toArray()));
        System.out.println(Arrays.toString(getIncidencias().toArray()));
        System.out.println(Arrays.toString(getDevolucions().toArray()));

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
                if (manejoProductos.getProducto(p.getCodigo(), this.getProductos()) == null) {
                    this.getProductos().add(p);
                } else {
                    this.getErrores().add(new DatoError("Producto repetido", p.toString()));
                }
            } catch (NumberFormatException e) {
                this.getErrores().add(new DatoError("Formato númerico erróneo",
                        "PRODUCTO: " + detalleProducto.get("codigo").toString()
                        + ", " + detalleProducto.get("nombre").toString()));
            } catch (NullPointerException e) {
                this.getErrores().add(new DatoError("No existe dato.",
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
                ArrayList<Producto> pTienda = new ArrayList<>();
                t.setListaProductos(pTienda);
                JSONArray catalogo = (JSONArray) jsonObject.get("productos");

                for (int j = 0; j < catalogo.size(); j++) {
                    JSONObject producto = (JSONObject) catalogo.get(j);
                    try {
                        Producto p = new Producto();
                        p.setCodigo(producto.get("codigo").toString());
                        p.setExistencia(Integer.parseInt(producto.get("existencias").toString()));
                        pTienda.add(p);
                    } catch (NumberFormatException e) {
                        this.getErrores().add(new DatoError("Formato númerico erróneo",
                                "Producto de tienda: " + tienda.get("codigo")));
                    } catch (NullPointerException e) {
                        this.getErrores().add(new DatoError("No existe dato.",
                                "Producto en la posición: " + (j + 1) + " , de la tienda. " + tienda.get("codigo").toString()));
                    }
                }
                if (manejoTiendas.getTienda(t.getCodigo(), this.getTiendas()) == null) {
                    this.getTiendas().add(t);
                } else {
                    this.getErrores().add(new DatoError("Tienda repetido", t.toString()));
                }
            } catch (NullPointerException e) {
                this.getErrores().add(new DatoError("No existe dato.",
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
                if (manejoUsuario.getUser(usuario.getCodigo(), this.getUsuarios()) == null) {
                    this.getUsuarios().add(usuario);
                } else {
                    this.getErrores().add(new DatoError("Usuario repetido", usuario.toString()));
                }
            } catch (NullPointerException e) {
                this.getErrores().add(new DatoError("No existe dato.",
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
                if (manejoUsuario.getUser(usuario.getCodigo(), this.getUsuarios()) == null) {
                    this.getUsuarios().add(usuario);
                    UsuarioTienda uTienda = new UsuarioTienda();
                    uTienda.setCodigo(usuario.getCodigo());
                    uTienda.setCodigoTienda(usuarioTienda.get("tienda").toString());
                    this.getUsuarioTiendas().add(uTienda);
                } else {
                    this.getErrores().add(new DatoError("Usuario repetido", usuario.toString()));
                }

            } catch (NullPointerException e) {
                this.getErrores().add(new DatoError("No existe dato.",
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
                if (manejoUsuario.getUser(usuario.getCodigo(), this.getUsuarios()) == null) {
                    this.getUsuarios().add(usuario);
                } else {
                    this.getErrores().add(new DatoError("Usuario repetido", usuario.toString()));
                }
            } catch (NullPointerException e) {
                this.getErrores().add(new DatoError("No existe dato.",
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

                UsuarioBodega userBodega = new UsuarioBodega();
                userBodega.setCodigo(usuario.getCodigo());

                List<Tienda> tiendasBodeguero = new ArrayList<>();
                userBodega.setTiendas((ArrayList<Tienda>) tiendasBodeguero);

                JSONArray tiendasBodega = (JSONArray) bodeguero.get("tiendas");
                for (int j = 0; j < tiendasBodega.size(); j++) {
                    System.out.println();
                    Tienda tU = new Tienda();
                    tU.setCodigo(tiendasBodega.get(j).toString());
                    //corregir este método
                    if (manejoTiendas.getTienda(tiendasBodega.get(j).toString(), this.tiendas) != null) {
                        for (UsuarioBodega usBodega : this.getUsuarioBodegas()) {
                            if (manejoTiendas.getTienda(tiendasBodega.get(j).toString(), userBodega.getTiendas()) == null) {
                                userBodega.getTiendas().add(tU);
                                break;
                            } else {
                                this.getErrores().add(new DatoError("Tienda asiganada a bodeguero.", "La tienda ya le pertenece a otro usuario."));
                                break;
                            }
                        }
                    } else {
                        this.getErrores().add(new DatoError("No existe dato", "La tienda no existe en la lista de tiendas"));
                    }
                }
                if (manejoUsuario.getUser(usuario.getCodigo(), this.usuarios) == null) {
                    this.getUsuarios().add(usuario);
                    this.getUsuarioBodegas().add(userBodega);
                } else {
                    this.getErrores().add(new DatoError("Usuario repetido", usuario.toString()));
                }
            } catch (NullPointerException e) {
                this.getErrores().add(new DatoError("No existe dato.",
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
                        this.getErrores().add(new DatoError("Fómato númerico inválido",
                                "Producto en pedido: " + p.getId()));
                    } catch (NullPointerException e) {
                        this.getErrores().add(new DatoError("No existe dato.",
                                "Producto en pedido: " + p.getId()));
                    }

                }
                if (manejoPedido.getPedido(p.getId(), this.getPedidos()) == null) {
                    this.getPedidos().add(p);
                } else {
                    this.getErrores().add(new DatoError("Pedido repetido", p.toString()));
                }
            } catch (NumberFormatException e) {
                this.getErrores().add(new DatoError("Fómato númerico inválido",
                        "Pedido en la posición: " + (i + 1)));
            } catch (NullPointerException e) {
                this.getErrores().add(new DatoError("FNo existe dato",
                        "Pedido en la posición: " + (i + 1)));
            }
        }
    }

    private void procesarEnvios(JSONObject jsonObject) {
        System.out.println("envios");
        JSONArray envios = (JSONArray) jsonObject.get("envios");
        for (int i = 0; i < envios.size(); i++) {
            try {
                JSONObject jsonEnvio = (JSONObject) envios.get(i);
                Envio envio = new Envio(
                        Integer.parseInt(jsonEnvio.get("id").toString()),
                        jsonEnvio.get("fechaSalida").toString(),
                        jsonEnvio.get("fechaRecibido").toString(),
                        Double.parseDouble(jsonEnvio.get("total").toString()),
                        jsonEnvio.get("estado").toString(),
                        jsonEnvio.get("tienda").toString(),
                        jsonEnvio.get("usuario").toString(),
                        Integer.parseInt(jsonEnvio.get("pedido").toString()));

                if (jsonEnvio.get("fechaRecibido").toString().isBlank()) {
                    envio.setFechaLlegada(envio.getFechaSalida());
                }
                JSONArray productosEnvio = (JSONArray) jsonEnvio.get("productos");
                ArrayList<DetalleEnvio> productsEnvio = new ArrayList<>();
                envio.setDetalle(productsEnvio);

                for (int j = 0; j < productosEnvio.size(); j++) {
                    JSONObject productoEnvio = (JSONObject) productosEnvio.get(j);
                    try {
                        productsEnvio.add(new DetalleEnvio(
                                envio.getId(),
                                productoEnvio.get("codigo").toString(),
                                Integer.parseInt(productoEnvio.get("cantidad").toString()),
                                Double.parseDouble(productoEnvio.get("costoU").toString()),
                                Double.parseDouble(productoEnvio.get("costoTotal").toString())));
                    } catch (NumberFormatException ex) {
                        this.getErrores().add(new DatoError("Fómato númerico inválido",
                                "Producto del envio. ID envío: " + envio.getId()));
                    } catch (NullPointerException e2) {
                        this.getErrores().add(new DatoError("FNo existe dato",
                                "Producto del envio. ID envío: " + envio.getId()));
                    }
                }
                if (manejoEnvio.getEnvio(envio.getId(), this.getEnvios()) == null) {
                    this.getEnvios().add(envio);
                } else {
                    this.getErrores().add(new DatoError("Envio repetido", envio.toString()));
                }
            } catch (NumberFormatException e) {
                this.getErrores().add(new DatoError("Fómato númerico inválido",
                        "Envio en la posición: " + (i + 1)));
            } catch (NullPointerException e) {
                this.getErrores().add(new DatoError("FNo existe dato",
                        "Envio en la posición: " + (i + 1)));
            } catch (Exception e) {
                this.getErrores().add(new DatoError("Fomato inválido",
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
                        incidencia.get("tienda").toString(),
                        Integer.parseInt(incidencia.get("envio").toString()));

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
                        this.getErrores().add(new DatoError("Formato numérico erróneo", "Detalle de incidencia: " + (j + 1)));
                    } catch (NullPointerException e) {
                        this.getErrores().add(new DatoError("No existe el dato", "Detalle de incidencia: " + (j + 1)));
                    }
                }
                if (manejoIncidencias.getIncidencia(inc.getId(), this.getIncidencias()) == null) {
                    this.getIncidencias().add(inc);
                } else {
                    this.getErrores().add(new DatoError("Incidencia reptida.", inc.toString()));
                }

            } catch (NumberFormatException e) {
                this.getErrores().add(new DatoError("Formato numérico inválido.", "Incidencia en la posición:  " + (i + 1)));
            } catch (NullPointerException e) {
                this.getErrores().add(new DatoError("No existe dato de la incidencia", "Incidencia en la posición:  " + (i + 1)));
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
                        devolucion.get("tienda").toString(),
                        Integer.parseInt(devolucion.get("envio").toString()));
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
                        this.getErrores().add(new DatoError("Formato numérico erróneo", "Detalle de devolución: " + (j + 1)));
                    } catch (NullPointerException e) {
                        this.getErrores().add(new DatoError("No existe el dato", "Detalle de devolución: " + (j + 1)));
                    }
                }
                if (manejoDevoluciones.getDevolucion(dev.getId(), this.getDevolucions()) == null) {
                    this.getDevolucions().add(dev);
                } else {
                    this.getErrores().add(new DatoError("Devolución repetida.", dev.toString()));
                }
            } catch (NumberFormatException e) {
                this.getErrores().add(new DatoError("Formato numérico inválido.", "Devolución en la posición:  " + (i + 1)));
            } catch (NullPointerException e) {
                this.getErrores().add(new DatoError("No existe dato de la incidencia", "Devolución en la posición:  " + (i + 1)));
            }
        }
    }

    private boolean darFomatoFecha(String fecha) {
        try {
            Date.valueOf(fecha);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @return the productos
     */
    public List<Producto> getProductos() {
        return productos;
    }

    /**
     * @return the tiendas
     */
    public List<Tienda> getTiendas() {
        return tiendas;
    }

    /**
     * @return the usuarios
     */
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    /**
     * @return the devolucions
     */
    public List<Devolucion> getDevolucions() {
        return devolucions;
    }

    /**
     * @return the incidencias
     */
    public List<Incidencia> getIncidencias() {
        return incidencias;
    }

    /**
     * @return the pedidos
     */
    public List<Pedido> getPedidos() {
        return pedidos;
    }

    /**
     * @return the usuarioTiendas
     */
    public List<UsuarioTienda> getUsuarioTiendas() {
        return usuarioTiendas;
    }

    /**
     * @return the usuarioBodegas
     */
    public List<UsuarioBodega> getUsuarioBodegas() {
        return usuarioBodegas;
    }

    /**
     * @return the envios
     */
    public List<Envio> getEnvios() {
        return envios;
    }

    /**
     * @return the errores
     */
    public List<DatoError> getErrores() {
        return errores;
    }

}
