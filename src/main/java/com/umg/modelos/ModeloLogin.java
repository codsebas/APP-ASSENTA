/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.umg.modelos;

import com.umg.vistas.VistaLogin;

/**
 *
 * @author axels
 */
public class ModeloLogin {
    VistaLogin vistaL;
    private String usuario;
    private String password;

    public ModeloLogin() {
    }

    public ModeloLogin(VistaLogin vistaL) {
        this.vistaL = vistaL;
    }

    public VistaLogin getVistaL() {
        return vistaL;
    }

    public void setVistaL(VistaLogin vistaL) {
        this.vistaL = vistaL;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
