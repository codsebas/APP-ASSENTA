package com.umg.controlador;


import com.umg.implementacion.PuestoImp;
import com.umg.modelos.ModeloPuesto;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ControladorPuesto implements MouseListener {
    ModeloPuesto modelo;

    public ControladorPuesto(ModeloPuesto modelo) {
        this.modelo = modelo;
    }

    PuestoImp implementacion = new PuestoImp();


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

    public void agregarPuesto() {
        ModeloPuesto modeloPuesto = new ModeloPuesto();
        modeloPuesto.setNombrePuesto(modelo.getVistaPuestos().txtNombrePuesto.getText());
        modeloPuesto.setDescripcionPuesto("hola soy el puesto");
        implementacion.insertarPuesto(modeloPuesto);
    }
}