package com.umg.controlador;

import com.umg.implementacion.EmpleadoImp;
import com.umg.modelos.ModeloEliminarEmpleado;
import com.umg.modelos.ModeloEmpleado;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ControladorEliminarEmpleado implements MouseListener {
    private ModeloEliminarEmpleado modelo;
    private EmpleadoImp empleado;

    public ControladorEliminarEmpleado(ModeloEliminarEmpleado modelo) {
        this.modelo = modelo;
        this.empleado = new EmpleadoImp();

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getComponent().equals(modelo.getVistaEliminarEmpleados().btnBuscarEmpleado)) {
            buscarEmpleadoPorDPI();
        } else if (e.getComponent().equals(modelo.getVistaEliminarEmpleados().btnEliminarEmpleado)) {
            eliminarEmpleado();
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

    private void buscarEmpleadoPorDPI() {
        String dpi = modelo.getVistaEliminarEmpleados().txtDpiEmp.getText().trim();

        if (dpi == null || dpi.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, ingresa un DPI afdasfa.");
            return;
        }

        EmpleadoImp imp = new EmpleadoImp();
        ModeloEmpleado empleado = imp.mostrarNombreApellidoPuestoPorDPI(dpi);

        if (empleado == null) {
            JOptionPane.showMessageDialog(null, "El DPI no existe en la base de burrro.");
            return;
        }

        var vista = modelo.getVistaEliminarEmpleados();
        vista.txtNombresEmp.setText(empleado.getPrimerNombre());
        vista.txtApellidosEmp.setText(empleado.getPrimerApellido());
        vista.txtPuestoEmp.setText(empleado.getNombrePuesto());

        JOptionPane.showMessageDialog(null, "Datos cargados correctamente.");
    }

    private void eliminarEmpleado() {
        String dpi = modelo.getVistaEliminarEmpleados().txtDpiEmp.getText().trim();

        if (dpi == null || dpi.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingresa un DPI válido.");
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(null,
                "¿Estás seguro que deseas eliminar este empleado?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean eliminado = empleado.eliminarEmpleadoPorDPI(dpi);

            if (eliminado) {
                JOptionPane.showMessageDialog(null, "Empleado eliminado correctamente.");

                // Limpiar campos de texto
                var vista = modelo.getVistaEliminarEmpleados();
                vista.txtDpiEmp.setText("");
                vista.txtNombresEmp.setText("");
                vista.txtApellidosEmp.setText("");
                vista.txtPuestoEmp.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar al empleado.");
            }
        }
    }
}


