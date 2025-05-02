package com.umg.controlador;

import com.umg.implementacion.UsuarioImp;
import com.umg.modelos.ModeloUsuario;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ControladorUsuario implements MouseListener, DocumentListener {
    ModeloUsuario modelo;
    UsuarioImp implementacion = new UsuarioImp();

    public ControladorUsuario(ModeloUsuario modelo) {
        this.modelo = modelo;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getComponent().equals(modelo.getVista().btnAgregarUsuario)) {
            insertarUsuario();
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

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private void insertarUsuario () {

        String password1 = String.valueOf(modelo.getVista().txtPasswordMant.getPassword());
        String password2 = String.valueOf(modelo.getVista().txtPasswordMantConf.getPassword());

        if(!password1.isEmpty() || !password2.isEmpty()) {
            if (password1.equals(password2)) {
                ModeloUsuario modelousuario = new ModeloUsuario();
                modelousuario.setUsuario(modelo.getVista().txtUsuarioMant.getText());
                modelousuario.setPassword(String.valueOf(modelo.getVista().txtPasswordMant.getPassword()));
                boolean resultado = this.implementacion.insertarUsuario(modelousuario);
                if (!resultado) {
                    JOptionPane.showMessageDialog(null, "Usuario creado con exito", "Creación de Usuario", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Hubo un error al crear el usuario", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Las contraseñas ingresadas son diferentes.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }else{
            modelo.getVista().lblErrorContraVa.setVisible(true);
            modelo.getVista().lblErrorContraVa1.setVisible(true);
        }

    }

    private void eliminarUsuario() {

    }

    private void consultarUsuarios() {
        
    }

    private void consultarUsuario() {
        
    }

    private void verificarCoincidencia(){
        String password1 = String.valueOf(modelo.getVista().txtPasswordMant.getPassword());
        String password2 = String.valueOf(modelo.getVista().txtPasswordMantConf.getPassword());

        if(!password1.equals(password2)) {
            modelo.getVista().lblErrorContra.setVisible(true);
        }else{
            modelo.getVista().lblErrorContra.setVisible(false);
        }
    }

    private void verificarBlancos(){
        String password1 = String.valueOf(modelo.getVista().txtPasswordMant.getPassword());
        String password2 = String.valueOf(modelo.getVista().txtPasswordMantConf.getPassword());
        if(!password1.isEmpty()){
            modelo.getVista().lblErrorContraVa.setVisible(false);
            modelo.getVista().lblErrorContraVa1.setVisible(false);
        }
        if(!password2.isEmpty()){
            modelo.getVista().lblErrorContraVa1.setVisible(false);
        }

    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        verificarCoincidencia();
        verificarBlancos();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        verificarCoincidencia();
        verificarBlancos();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        verificarCoincidencia();
        verificarBlancos();
    }
}
