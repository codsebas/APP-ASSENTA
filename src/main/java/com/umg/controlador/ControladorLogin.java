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
public class ControladorLogin implements ActionListener{
    ModeloLogin modelo;
    VistaLogin vista;
    VistaPrincipal vistaPrincipal;

    public ControladorLogin(ModeloLogin modelo, VistaLogin vista, VistaPrincipal vistaPrincipal) {
        this.modelo = modelo;
        this.vista = vista;
        this.vistaPrincipal = vistaPrincipal;
        
        vista.getBtnIniciarSesion().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(vista.getTxt1().getText());
        ModeloPrueba modelo2 = new ModeloPrueba();
        
        VistaPrueba vista2 = new VistaPrueba();
        new ControladorPrueba(modelo2,vista2, vistaPrincipal);
        vistaPrincipal.cambiarPanel(vista2);                
    }
    
}
