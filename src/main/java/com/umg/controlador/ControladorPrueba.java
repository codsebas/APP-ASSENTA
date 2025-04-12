/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.umg.controlador;

import com.umg.modelos.ModeloLogin;
import com.umg.modelos.ModeloPrueba;
import com.umg.vistas.VistaLogin;
import com.umg.vistas.VistaPrincipal;
import com.umg.vistas.VistaPrueba;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author axels
 */
public class ControladorPrueba implements ActionListener{
    ModeloPrueba modelo;
    VistaPrueba vista;
    VistaPrincipal vistsaPrincipal;

    public ControladorPrueba(ModeloPrueba modelo, VistaPrueba vista, VistaPrincipal vistsaPrincipal) {
        this.modelo = modelo;
        this.vista = vista;
        this.vistsaPrincipal = vistsaPrincipal;
        
        vista.getBtnRegresar().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ModeloLogin modelo2 = new ModeloLogin();
        
        VistaLogin vista2 = new VistaLogin();
        new ControladorLogin(modelo2,vista2, vistsaPrincipal);
        vistsaPrincipal.cambiarPanel(vista2);
    }
    
}
