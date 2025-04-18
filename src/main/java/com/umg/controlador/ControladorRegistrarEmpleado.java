package com.umg.controlador;

import com.umg.modelos.ModeloVistaRegistrarEmpleado;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ControladorRegistrarEmpleado implements MouseListener {
    
    ModeloVistaRegistrarEmpleado modelo;
    
    public ControladorRegistrarEmpleado(ModeloVistaRegistrarEmpleado modelo) {
        this.modelo = modelo;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getComponent().equals(modelo.getvRegistraEmpleado().btnRegistrarEmpleado)) {
            unirNombre();
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
        if (e.getComponent().equals(modelo.getvRegistraEmpleado().btnRegistrarEmpleado)) {
            modelo.getvRegistraEmpleado().btnRegistrarEmpleado.setBackground(new Color(0, 127, 75));
        }
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getComponent().equals(modelo.getvRegistraEmpleado().btnRegistrarEmpleado)) {
            modelo.getvRegistraEmpleado().btnRegistrarEmpleado.setBackground(new Color(219, 252, 231));
        }
    }
    
    private void unirNombre() {
        String texto;
        texto = modelo.getvRegistraEmpleado().txtNom1.getText() + " " + modelo.getvRegistraEmpleado().txtNom2.getText();
        System.out.println("El nombre unido es: " + texto);

    }
    
}
