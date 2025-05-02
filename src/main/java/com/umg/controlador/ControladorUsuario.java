package com.umg.controlador;

import com.umg.implementacion.UsuarioImp;
import com.umg.modelos.ModeloUsuario;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ControladorUsuario implements MouseListener {
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
        ModeloUsuario modelousuario = new ModeloUsuario();
        modelousuario.setUsuario(modelo.getVista().txtUsuarioMant.getText());
        modelousuario.setPassword(String.valueOf(modelo.getVista().txtPasswordMant.getPassword()));
        boolean resultado = this.implementacion.insertarUsuario(modelousuario);
        if (!resultado) {
            JOptionPane.showMessageDialog(null, "Usuario agregado con exito", "Agregar Usuario", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Hubo un error al insertar el usuario", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarUsuario() {

    }

    private void consultarUsuarios() {
        this.modelo.getVista().tblUsuarios.setModel(this.implementacion.modeloUsuario());
    }

    private void consultarUsuario() {
        this.modelo.getVista().tblUsuarios.setModel(this.implementacion.modeloUsuario());
    }
}
