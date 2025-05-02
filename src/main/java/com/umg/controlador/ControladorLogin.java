/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.umg.controlador;

import com.umg.implementacion.LoginImp;
import com.umg.modelos.ModeloLogin;
import com.umg.modelos.ModeloMenu;
import com.umg.vistas.VistaLogin;
import com.umg.vistas.VistaPrincipal;
import com.umg.vistas.VistaMenu;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import java.awt.Color;
import java.awt.event.*;

/**
 *
 * @author axels
 */
public class ControladorLogin implements ActionListener, MouseListener, DocumentListener, KeyListener {

    ModeloLogin modelo;
    VistaLogin vista;
    VistaPrincipal vistaPrincipal;
    LoginImp implementacion = new LoginImp();

    public ControladorLogin(ModeloLogin modelo, VistaLogin vista, VistaPrincipal vistaPrincipal) {
        this.modelo = modelo;
        this.vista = vista;
        this.vistaPrincipal = vistaPrincipal;
        
        vista.getBtnIniciarSesion().addMouseListener(this);
        vista.getTxtUsuario().getDocument().addDocumentListener(this);
        vista.getTxtPassword().getDocument().addDocumentListener(this);
        vista.getTxtPassword().addKeyListener(this);
        vista.getTxtUsuario().addKeyListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
               
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getComponent().equals(vista.getBtnIniciarSesion())){
            validarCampos();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if(e.getComponent().equals(vista.getBtnIniciarSesion())){
            vista.getBtnIniciarSesion().setBackground(new Color(38,163,106));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(e.getComponent().equals(vista.getBtnIniciarSesion())){
            vista.getBtnIniciarSesion().setBackground(new Color(0,127,75));
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getComponent().equals(this.vista.getTxtUsuario())){
            if(e.getKeyCode() == KeyEvent.VK_ENTER){
                this.vista.getTxtPassword().requestFocus();
            }
        }else if(e.getComponent().equals(this.vista.getTxtPassword())){
            if(e.getKeyCode() == KeyEvent.VK_ENTER){
                validarCampos();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        verificarCampoVacio();
        camposNoMayores();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        verificarCampoVacio();
        camposNoMayores();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        verificarCampoVacio();
        camposNoMayores();
    }

    private void validarCampos(){
        if(this.vista.getTxtUsuario().getText().trim().isEmpty() || String.valueOf(this.vista.getTxtPassword().getPassword()).trim().isEmpty()){
            if(this.vista.getTxtUsuario().getText().trim().isEmpty()){
                this.vista.lblErrorUsuario.setText("*Ingrese un usuario");
            }
            if(String.valueOf(this.vista.getTxtPassword().getPassword()).trim().isEmpty()){
                this.vista.lblErrorPassword.setText("*Ingrese una contraseña");
            }
        }else{
            validarUsuario();
        }
    }

    private void validarUsuario(){
        boolean respuesta = this.implementacion.consultaUsuario(this.vista.getTxtUsuario().getText(), String.valueOf(this.vista.getTxtPassword().getPassword()));
        if(respuesta){
            ModeloMenu modelo = new ModeloMenu();
            VistaMenu vista = new VistaMenu();
            new ControladorMenu(modelo, vista, vistaPrincipal);
            vistaPrincipal.cambiarPanel(vista);
        }else {
            JOptionPane.showMessageDialog(null, "Contraseña incorrecta", "Error al iniciar sesión", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void verificarCampoVacio(){
        String usuario = this.vista.getTxtUsuario().getText();
        String password = String.valueOf(this.vista.getTxtPassword().getPassword());
        if(!usuario.trim().isEmpty()){
            this.vista.getLblErrorUsuario().setText("");
        }
        if(!password.trim().isEmpty()){
            this.vista.getLblErrorPassword().setText("");
        }
    }

    private void camposNoMayores(){
        ((AbstractDocument) this.vista.getTxtUsuario().getDocument()).setDocumentFilter(new LimiteCaracteres(15));
        ((AbstractDocument) this.vista.getTxtPassword().getDocument()).setDocumentFilter(new LimiteCaracteres(30));
    }

}
