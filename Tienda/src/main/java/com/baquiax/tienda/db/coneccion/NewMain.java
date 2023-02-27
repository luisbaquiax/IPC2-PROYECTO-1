/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.db.coneccion;

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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
}
