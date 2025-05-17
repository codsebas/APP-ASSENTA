package com.umg.controlador;


import com.umg.implementacion.PuestoImp;
import com.umg.modelos.ModeloPuesto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class ControladorPuesto implements MouseListener {
    ModeloPuesto modelo;

    public ControladorPuesto(ModeloPuesto modelo) {
        this.modelo = modelo;
        mostrarTodosLosPuestos();
        modelo.getVistaPuestos().tblPuestos.addMouseListener(this); // Escuchar clics en la tabla
        modelo.getVistaPuestos().btnAgregar.addMouseListener(this);
        modelo.getVistaPuestos().btnEditar.addMouseListener(this);
        modelo.getVistaPuestos().btnEliminar.addMouseListener(this);
        mostrarTodosLosPuestos();
    }

    PuestoImp implementacion = new PuestoImp();


    @Override
    public void mouseClicked(MouseEvent e) {
        Object source = e.getSource();

        if (source.equals(modelo.getVistaPuestos().btnAgregar)) {
            agregarPuesto();
        } else if (source.equals(modelo.getVistaPuestos().btnEliminar)) {
            eliminarPuesto();
        } else if (source.equals(modelo.getVistaPuestos().btnEditar)) {
            actualizarPuesto();
        } else if (source.equals(modelo.getVistaPuestos().tblPuestos)) {
            // Llenar campos cuando se hace clic en una fila
            int filaSeleccionada = modelo.getVistaPuestos().tblPuestos.getSelectedRow();
            if (filaSeleccionada >= 0) {
                String nombre = modelo.getVistaPuestos().tblPuestos.getValueAt(filaSeleccionada, 1).toString();
                String descripcion = modelo.getVistaPuestos().tblPuestos.getValueAt(filaSeleccionada, 2).toString();

                modelo.getVistaPuestos().txtNombrePuesto.setText(nombre);
                modelo.getVistaPuestos().txtDescripcion.setText(descripcion);
            }
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
        if (e.getComponent().equals(modelo.getVistaPuestos().btnAgregar)) {
            modelo.getVistaPuestos().btnAgregar.setBackground(new Color(38, 163, 106));
        } else if (e.getComponent().equals(modelo.getVistaPuestos().btnEditar)) {
            modelo.getVistaPuestos().btnEditar.setBackground(new Color(38, 163, 106));
        } else if (e.getComponent().equals(modelo.getVistaPuestos().btnEliminar)) {
            modelo.getVistaPuestos().btnEliminar.setBackground(new Color(38, 163, 106));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getComponent().equals(modelo.getVistaPuestos().btnAgregar)) {
            modelo.getVistaPuestos().btnAgregar.setBackground(new Color(0,127,75));
        } else if (e.getComponent().equals(modelo.getVistaPuestos().btnEditar)) {
            modelo.getVistaPuestos().btnEditar.setBackground(new Color(0,127,75));
        } else if (e.getComponent().equals(modelo.getVistaPuestos().btnEliminar)) {
            modelo.getVistaPuestos().btnEliminar.setBackground(new Color(0,127,75));
        }
    }

    public void agregarPuesto() {
        ModeloPuesto modeloPuesto = new ModeloPuesto();
        String nombrePuesto = modelo.getVistaPuestos().txtNombrePuesto.getText();
        String descripcionPuesto = modelo.getVistaPuestos().txtDescripcion.getText();

        modeloPuesto.setNombrePuesto(nombrePuesto);
        modeloPuesto.setDescripcionPuesto(descripcionPuesto);

        if (implementacion.insertarPuesto(modeloPuesto)) {
            JOptionPane.showMessageDialog(modelo.getVistaPuestos(), "Puesto agregado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(modelo.getVistaPuestos(), "Error al agregar el puesto", "Error", JOptionPane.ERROR_MESSAGE);
        }

        mostrarTodosLosPuestos();
    }

    public void eliminarPuesto() {
        int filaSeleccionada = modelo.getVistaPuestos().tblPuestos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int idPuesto = (int) modelo.getVistaPuestos().tblPuestos.getValueAt(filaSeleccionada, 0);
            if (implementacion.eliminarPuesto(idPuesto)) {
                JOptionPane.showMessageDialog(modelo.getVistaPuestos(), "Puesto eliminado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(modelo.getVistaPuestos(), "Error al eliminar el puesto", "Error", JOptionPane.ERROR_MESSAGE);
            }
            mostrarTodosLosPuestos();
        } else {
            JOptionPane.showMessageDialog(modelo.getVistaPuestos(), "Seleccione un puesto para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void actualizarPuesto() {
        int filaSeleccionada = modelo.getVistaPuestos().tblPuestos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int idPuesto = (int) modelo.getVistaPuestos().tblPuestos.getValueAt(filaSeleccionada, 0);
            String nombrePuesto = modelo.getVistaPuestos().txtNombrePuesto.getText();
            String descripcionPuesto = modelo.getVistaPuestos().txtDescripcion.getText();

            ModeloPuesto modeloPuesto = new ModeloPuesto();
            modeloPuesto.setIdPuesto(idPuesto);
            modeloPuesto.setNombrePuesto(nombrePuesto);
            modeloPuesto.setDescripcionPuesto(descripcionPuesto);

            if (implementacion.actualizarPuesto(modeloPuesto)) {
                JOptionPane.showMessageDialog(modelo.getVistaPuestos(), "Puesto actualizado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(modelo.getVistaPuestos(), "Error al actualizar el puesto", "Error", JOptionPane.ERROR_MESSAGE);
            }
            mostrarTodosLosPuestos();
        } else {
            JOptionPane.showMessageDialog(modelo.getVistaPuestos(), "Seleccione un puesto para actualizar", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void mostrarTodosLosPuestos() {
        DefaultTableModel modeloTabla = implementacion.modeloPuesto();
        modelo.getVistaPuestos().tblPuestos.setModel(modeloTabla);
    }
}
