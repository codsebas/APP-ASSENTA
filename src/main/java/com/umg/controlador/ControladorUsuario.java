package com.umg.controlador;

import com.umg.implementacion.UsuarioImp;
import com.umg.modelos.ModeloUsuario;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import java.awt.*;
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

        }else if(e.getComponent().equals(modelo.getVista().btnEliminarUsuario)) {
            eliminarUsuario();
        }else if ( e.getComponent().equals(modelo.getVista().btnActualizarContrasenia)){
            actualizarUsuario();

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
        if (e.getComponent().equals(modelo.getVista().btnAgregarUsuario)) {
            modelo.getVista().btnAgregarUsuario.setBackground(new Color(38, 163, 106));
        } else if (e.getComponent().equals(modelo.getVista().btnBuscarUsuario)) {
            modelo.getVista().btnBuscarUsuario.setBackground(new Color(38, 163, 106));
        } else if (e.getComponent().equals(modelo.getVista().btnActualizarContrasenia)) {
            modelo.getVista().btnActualizarContrasenia.setBackground(new Color(38, 163, 106));
        } else if (e.getComponent().equals(modelo.getVista().btnEliminarUsuario)) {
            modelo.getVista().btnEliminarUsuario.setBackground(new Color(38, 163, 106));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getComponent().equals(modelo.getVista().btnAgregarUsuario)) {
            modelo.getVista().btnAgregarUsuario.setBackground(new Color(0, 127, 75));
        } else if (e.getComponent().equals(modelo.getVista().btnBuscarUsuario)) {
            modelo.getVista().btnBuscarUsuario.setBackground(new Color(0, 127, 75));
        } else if (e.getComponent().equals(modelo.getVista().btnActualizarContrasenia)) {
            modelo.getVista().btnActualizarContrasenia.setBackground(new Color(0, 127, 75));
        } else if (e.getComponent().equals(modelo.getVista().btnEliminarUsuario)) {
            modelo.getVista().btnEliminarUsuario.setBackground(new Color(0, 127, 75));
        }
    }

    private void insertarUsuario () {

        String password1 = String.valueOf(modelo.getVista().txtPasswordMant.getPassword());
        String password2 = String.valueOf(modelo.getVista().txtPasswordMantConf.getPassword());

        if(!password1.trim().isEmpty() || !password2.trim().isEmpty()) {
            if(!modelo.getVista().txtUsuarioMant.getText().trim().isEmpty()) {
                if (password1.equals(password2)) {
                    ModeloUsuario modelousuario = new ModeloUsuario();
                    modelousuario.setUsuario(modelo.getVista().txtUsuarioMant.getText());
                    modelousuario.setPassword(String.valueOf(modelo.getVista().txtPasswordMant.getPassword()));
                    modelousuario.setEmpleado_dpi(modelo.getVista().txtDPI.getText());
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
                modelo.getVista().lblErrorUsuario.setVisible(true);
            }
        }else{
            if(modelo.getVista().txtUsuarioMant.getText().trim().isEmpty()){
                modelo.getVista().lblErrorUsuario.setVisible(true);
                modelo.getVista().txtUsuarioMant.setText("");
            }
            modelo.getVista().lblErrorContraVa.setVisible(true);
            modelo.getVista().lblErrorContraVa1.setVisible(true);
        }

    }

    private void eliminarUsuario() {

    }

    private void actualizarUsuario() {
        String password1 = String.valueOf(modelo.getVista().txtPasswordMant.getPassword());
        String password2 = String.valueOf(modelo.getVista().txtPasswordMantConf.getPassword());
        if (!password1.trim().isEmpty() || !password2.trim().isEmpty()) {
            if (!modelo.getVista().txtUsuarioMant.getText().trim().isEmpty()) {
                if (password1.equals(password2)) {
                    ModeloUsuario modelousuario = new ModeloUsuario();
                    modelousuario.setUsuario(modelo.getVista().txtUsuarioMant.getText());
                    modelousuario.setPassword(password1); // Usar la contraseña ingresada
                    modelousuario.setEmpleado_dpi(modelo.getVista().txtDPI.getText());
                    boolean resultado = this.implementacion.actualizarUsuario(modelousuario);
                    if (resultado) {
                        JOptionPane.showMessageDialog(null, "Usuario actualizado con éxito", "Actualización de Usuario", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Hubo un error al actualizar el usuario", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Las contraseñas ingresadas son diferentes.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                modelo.getVista().lblErrorUsuario.setVisible(true);
            }
        } else {
            if (modelo.getVista().txtUsuarioMant.getText().trim().isEmpty()) {
                modelo.getVista().lblErrorUsuario.setVisible(true);
                modelo.getVista().txtUsuarioMant.setText("");
            }
            modelo.getVista().lblErrorContraVa.setVisible(true);
            modelo.getVista().lblErrorContraVa1.setVisible(true);
        }
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
        if(!modelo.getVista().txtUsuarioMant.getText().isEmpty()){
            modelo.getVista().lblErrorUsuario.setVisible(false);
        }

    }

    private void usuarioNoMas15(){
        ((AbstractDocument) modelo.getVista().txtUsuarioMant.getDocument()).setDocumentFilter(new LimiteCaracteres(13));
        ((AbstractDocument) modelo.getVista().txtPasswordMant.getDocument()).setDocumentFilter(new LimiteCaracteres(30));
        ((AbstractDocument) modelo.getVista().txtPasswordMantConf.getDocument()).setDocumentFilter(new LimiteCaracteres(30));
        ((AbstractDocument) modelo.getVista().txtPasswordMantAntConf.getDocument()).setDocumentFilter(new LimiteCaracteres(30));
        ((AbstractDocument) modelo.getVista().txtPasswordMantNueva.getDocument()).setDocumentFilter(new LimiteCaracteres(30));
        ((AbstractDocument) modelo.getVista().txtPasswordMantConNueva.getDocument()).setDocumentFilter(new LimiteCaracteres(30));
        ((AbstractDocument) modelo.getVista().txtDPI.getDocument()).setDocumentFilter(new LimiteCaracteres(13));
        ((AbstractDocument) modelo.getVista().txtDPIBuscar.getDocument()).setDocumentFilter(new LimiteCaracteres(13));
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        verificarCoincidencia();
        verificarBlancos();
        usuarioNoMas15();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        verificarCoincidencia();
        verificarBlancos();
        usuarioNoMas15();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        verificarCoincidencia();
        verificarBlancos();
        usuarioNoMas15();
    }
}
