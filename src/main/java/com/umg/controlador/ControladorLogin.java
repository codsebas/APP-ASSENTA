/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.umg.controlador;

import com.umg.modelos.ModeloLogin;
import com.umg.modelos.ModeloMenu;
import com.umg.vistas.VistaLogin;
import com.umg.vistas.VistaPrincipal;
import com.umg.vistas.VistaMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author axels
 */
public class ControladorLogin implements ActionListener, MouseListener {
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
        ModeloMenu modelo2 = new ModeloMenu();
        
        VistaMenu vista2 = new VistaMenu();
        new ControladorMenu(modelo2,vista2, vistaPrincipal);
        vistaPrincipal.cambiarPanel(vista2);                
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
