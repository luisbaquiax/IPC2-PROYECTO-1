/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.entidad;

import java.io.Serializable;

/**
 *
 * @author luis
 */
public class Usuario implements Serializable {

    private String codigo;
    private String tipo;
    private String nombreUsuario;
    private String nombre;
    private String password;
    private boolean estado;
    private String email;

    public Usuario() {
    }

    /**
     *
     * @param codigo
     * @param tipo
     * @param nombreUsuario
     * @param nombre
     * @param password
     * @param estado
     * @param emial
     */
    public Usuario(String codigo, String tipo, String nombreUsuario, String nombre, String password, boolean estado, String emial) {
        this.codigo = codigo;
        this.tipo = tipo;
        this.nombreUsuario = nombreUsuario;
        this.nombre = nombre;
        this.password = password;
        this.estado = estado;
        this.email = emial;
    }

    @Override
    public String toString() {
        return "Usuario{" + "codigo=" + codigo + ", tipo=" + tipo + ", nombreUsuario=" + nombreUsuario + ", nombre=" + nombre + ", password=" + password + ", estado=" + estado + ", email=" + email + '}';
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the nombreUsuario
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * @param nombreUsuario the nombreUsuario to set
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the estado
     */
    public boolean isEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

}
