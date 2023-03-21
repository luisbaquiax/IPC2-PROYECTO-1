/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.db.coneccion;

import com.baquiax.tienda.db.modelo.PedidoDB;
import com.baquiax.tienda.db.modelo.ProductoDB;
import com.baquiax.tienda.db.modelo.UsuarioAdministradorDB;
import com.baquiax.tienda.db.modelo.UsuarioDB;
import com.baquiax.tienda.db.modelo.reporteAdministracion.ReporteAdministracionDB;
import com.baquiax.tienda.db.modelo.reporteSupervisor.ReportePedidosSupervisorDB;
import com.baquiax.tienda.entidad.Pedido;
import com.baquiax.tienda.entidad.Producto;
import com.baquiax.tienda.entidad.Usuario;
import com.baquiax.tienda.entidad.UsuarioAdministrador;
import com.baquiax.tienda.entidad.encripta.Encriptador;
import com.baquiax.tienda.entidad.enumEntidad.TipoUsuarioEnum;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author luis
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            ConeccionDB.getConnection();
            UsuarioDB usuarioDB = new UsuarioDB();
            UsuarioAdministradorDB usuarioAdministradorDB = new UsuarioAdministradorDB();
            Encriptador encriptador = new Encriptador();
            PedidoDB pedidoDB = new PedidoDB();
            ReportePedidosSupervisorDB reportePedidosSupervisor = new ReportePedidosSupervisorDB();
            ReporteAdministracionDB reporteAdministracionDB = new ReporteAdministracionDB();
            List<String[]> lista = reporteAdministracionDB.getReporteAdministracion(
                    ReporteAdministracionDB.REPORTE_TIENDAS_PEDIDOS,
                    ReporteAdministracionDB.NUMERO_PEDIDOS,
                    ReporteAdministracionDB.CODIGO_TIENDA);

            List<String[]> lista1 = reporteAdministracionDB.getReporteAdministracion(
                    ReporteAdministracionDB.REPORTE_TIENDAS_PEDIDOS_BY_ESTADO_BETWEEN_FECHA,
                    ReporteAdministracionDB.NUMERO_PEDIDOS,
                    ReporteAdministracionDB.CODIGO_TIENDA,
                    "2023-03-06",
                    "2023-03-06",
                    "PENDIENTE");

            List<String[]> lista2 = reporteAdministracionDB.getReporteAdministracion(
                    ReporteAdministracionDB.REPORTE_USUARIOS_ENVIOS_GENERADOS,
                    ReporteAdministracionDB.NUMERO_ENVIOS,
                    ReporteAdministracionDB.USUARIO_BODEGA);

            List<String[]> lista3 = reporteAdministracionDB.getReporteAdministracion(
                    ReporteAdministracionDB.REPORTE_USUARIOS_ENVIOS_GENERADOS_BETWEEN_FEHCA,
                    ReporteAdministracionDB.NUMERO_ENVIOS,
                    ReporteAdministracionDB.USUARIO_BODEGA,
                    "2023-03-10",
                    "2023-03-10");

            List<String[]> lista4 = reporteAdministracionDB.getReporteAdministracion(
                    ReporteAdministracionDB.REPORTE_USUARIOS_PEDIDOS_GENERADOS,
                    ReporteAdministracionDB.NUMERO_PEDIDOS,
                    ReporteAdministracionDB.USUARIO_TIENDA);
            
            List<String[]> lista5 = reporteAdministracionDB.getReporteAdministracion(
                    ReporteAdministracionDB.REPORTE_USUARIOS_PEDIDOS_GENERADO_BETWEEN_FECHA,
                    ReporteAdministracionDB.NUMERO_PEDIDOS,
                    ReporteAdministracionDB.USUARIO_TIENDA,
                    "2023-03-06",
                    "2023-03-06");
            System.out.println("**************");
            for (int i = 0; i < lista.size(); i++) {
                System.out.println(lista.get(i)[0]);
                System.out.println(lista.get(i)[1]);
            }
            System.out.println("**************");
            for (int i = 0; i < lista1.size(); i++) {
                System.out.println(lista1.get(i)[0]);
                System.out.println(lista1.get(i)[1]);
            }
            System.out.println("**************");
            for (int i = 0; i < lista2.size(); i++) {
                System.out.println(lista2.get(i)[0]);
                System.out.println(lista2.get(i)[1]);
            }
            System.out.println("**************");
            for (int i = 0; i < lista3.size(); i++) {
                System.out.println(lista3.get(i)[0]);
                System.out.println(lista3.get(i)[1]);
            }
            System.out.println("**************");
            for (int i = 0; i < lista4.size(); i++) {
                System.out.println(lista4.get(i)[0]);
                System.out.println(lista4.get(i)[1]);
            }
            System.out.println("**************");
            for (int i = 0; i < lista5.size(); i++) {
                System.out.println(lista5.get(i)[0]);
                System.out.println(lista5.get(i)[1]);
            }
            /**
             *
             */
            System.out.println(encriptador.desencriptar("6Hh5snWZEXyesrVdXdntdQ=="));
            String[] contra = new String[]{"admin1", "userTienda1", "userBodega1", "userSupervisor1", "userTienda2"};
//            for (String c : contra) {
//                System.out.println(encriptador.encriptar(c));
//            }

//            Usuario u = new Usuario("codeAdmin", TipoUsuario.USUARIO_ADMIN.getTipo(), "luisbaquiax", "Luis", encriptador.encriptar("admin"), true);
//            usuarioDB.insert(u);
//            UsuarioAdministrador admin = new UsuarioAdministrador();
//            admin.setCodigo(u.getCodigo());
//            usuarioAdministradorDB.insert(admin);
            ProductoDB productoDB = new ProductoDB();
            for (Producto  p : productoDB.getProductos()) {
                System.out.println(p.toString());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
