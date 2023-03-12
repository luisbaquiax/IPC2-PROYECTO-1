/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.db.coneccion;

import com.baquiax.tienda.db.modelo.PedidoDB;
import com.baquiax.tienda.db.modelo.UsuarioAdministradorDB;
import com.baquiax.tienda.db.modelo.UsuarioDB;
import com.baquiax.tienda.db.modelo.reporteSupervisor.ReportePedidosSupervisorDB;
import com.baquiax.tienda.entidad.Pedido;
import com.baquiax.tienda.entidad.Usuario;
import com.baquiax.tienda.entidad.UsuarioAdministrador;
import com.baquiax.tienda.entidad.encripta.Encriptador;
import com.baquiax.tienda.entidad.enumEntidad.TipoUsuarioEnum;
import java.sql.SQLException;

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
            /**
             *
             */
            String[] contra = new String[]{"admin1", "userTienda1", "userBodega1", "userSupervisor1", "userTienda2"};
//            for (String c : contra) {
//                System.out.println(encriptador.encriptar(c));
//            }

            for (Pedido p : reportePedidosSupervisor.getPedidos("SUPERVISADA")) {
                System.out.println(p.toString());
            }
            
//            Usuario u = new Usuario("codeAdmin", TipoUsuario.USUARIO_ADMIN.getTipo(), "luisbaquiax", "Luis", encriptador.encriptar("admin"), true);
//            usuarioDB.insert(u);
//            UsuarioAdministrador admin = new UsuarioAdministrador();
//            admin.setCodigo(u.getCodigo());
//            usuarioAdministradorDB.insert(admin);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
