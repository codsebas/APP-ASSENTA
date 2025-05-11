/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.umg.controlador;

import com.umg.implementacion.EmpleadoImp;
import com.umg.implementacion.PuestoImp;
import com.umg.modelos.ModeloActualizarEmpleado;
import com.umg.modelos.ModeloEmpleado;
import com.umg.modelos.ModeloJefeInmediato;
import com.umg.modelos.ModeloPuesto;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

/**
 *
 * @author axels
 */
public class ControladorActualizarEmpleado implements MouseListener, ActionListener {
    ModeloActualizarEmpleado modelo;
    PuestoImp implementacionPuesto = new PuestoImp();


    public ControladorActualizarEmpleado(ModeloActualizarEmpleado modelo) {
        this.modelo = modelo;
    }

    public void cargarJefes(JComboBox<String> comboBox) {

        List<ModeloJefeInmediato> jefes = implementacionPuesto.obtenerJefesInmediatos();
        comboBox.removeAllItems();

        for (ModeloJefeInmediato j : jefes) {
            comboBox.addItem("Elegir Jefe");
            comboBox.addItem(String.valueOf(j));
        }
    }

    public void cargarPuestos(JComboBox<String> comboBox) {
        List<ModeloPuesto> puestos = implementacionPuesto.obtenerPuestos();
        comboBox.removeAllItems();

        for (ModeloPuesto p : puestos) {
            comboBox.addItem("Elegir Puesto");
            comboBox.addItem(p.toString());
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {
    if (e.getComponent().equals(modelo.getvActualizarEmpleado().btnActualizarEmpleado)){
actualizarEmpleado();
    } else if (e.getComponent().equals(modelo.getvActualizarEmpleado().btnRegistrarEmpleado1)){
        mostrarEmpleado();
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

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public void mostrarEmpleado() { String dpi = modelo.getvActualizarEmpleado().txtDPI.getText();

        if (dpi == null || dpi.isEmpty()) {
            JOptionPane.showMessageDialog(null, "⚠️ Por favor, ingresa un DPI.");
            return;
        }

        EmpleadoImp imp = new EmpleadoImp();
        ModeloEmpleado empleado = imp.mostrarEmpleadoPorDpi(dpi);

        if (empleado == null) {
            JOptionPane.showMessageDialog(null, "❌ No se encontró un empleado con ese DPI.");
            return;
        }

        var vista = modelo.getvActualizarEmpleado();

        // Llenar campos con la información recuperada
        vista.cbSexo.setSelectedItem(empleado.getSexo());
        vista.cbEstadoCivil.setSelectedItem(empleado.getEstadoCivil());
        vista.txtNom1.setText(empleado.getPrimerNombre());
        vista.txtNom2.setText(empleado.getSegundoNombre());
        vista.txtNom3.setText(empleado.getTercerNombre());
        vista.txtApe1.setText(empleado.getPrimerApellido());
        vista.txtApe2.setText(empleado.getSegundoApellido());
        vista.txtApeC.setText(empleado.getApellidoCasada());
        vista.txtFecha.setText(empleado.getFechaNacimiento());
        // Si quieres mostrar la edad
        // vista.txtEdad.setText(String.valueOf(empleado.getEdad()));
        vista.txtCorreo.setText(empleado.getCorreoElectronico());
        vista.txtNum1.setText(empleado.getNumeroTelefono1());
        vista.txtNum2.setText(empleado.getNumeroTelefono2());
        vista.txtHoraEntrada.setText(empleado.getHorarioEntrada());
        vista.txtHoraSalida.setText(empleado.getHorarioSalida());

        // Si estás usando JComboBox con los nombres visibles, seleccionamos por nombre:
        vista.cbPuesto.setSelectedItem(empleado.getNombrePuesto());
        vista.cbJefeInmediato.setSelectedItem(empleado.getNombreJefeInmediato());

        // Si tu vista también tiene los datos de dirección (esto dependerá de cómo llenes esos ComboBox)
        vista.cbDepto.setSelectedItem(empleado.getDepartamento());
        vista.cbMun.setSelectedItem(empleado.getMunicipio());
        vista.txtAldea.setText(empleado.getAldeaColonia());
        vista.txtDireccion.setText(empleado.getDireccionVivienda());

        JOptionPane.showMessageDialog(null, "✅ Datos del empleado cargados.");
    }

    public void actualizarEmpleado(){
        EmpleadoImp imp = new EmpleadoImp();
        ModeloEmpleado empleado = new ModeloEmpleado();

        try {
            var vista = modelo.getvActualizarEmpleado();

            empleado.setIdEmpleado(Integer.parseInt(vista.txtDPI.getText())); // Suponiendo que ID está en txtDPI (¿seguro? suele ser otro campo)
            empleado.setDpi(vista.txtDPI.getText());
            empleado.setSexo((String) vista.cbSexo.getSelectedItem());
            empleado.setEstadoCivil((String) vista.cbEstadoCivil.getSelectedItem());
            empleado.setPrimerNombre(vista.txtNom1.getText());
            empleado.setSegundoNombre(vista.txtNom2.getText());
            empleado.setTercerNombre(vista.txtNom3.getText());
            empleado.setPrimerApellido(vista.txtApe1.getText());
            empleado.setSegundoApellido(vista.txtApe2.getText());
            empleado.setApellidoCasada(vista.txtApeC.getText());
            empleado.setFechaNacimiento(vista.txtFecha.getText());
            // La edad deberías calcularla automáticamente por fecha de nacimiento, pero si está en un campo:
            // empleado.setEdad(Integer.parseInt(vista.txtEdad.getText()));  // Si tienes txtEdad
            empleado.setCorreoElectronico(vista.txtCorreo.getText());
            empleado.setNumeroTelefono1(vista.txtNum1.getText());
            empleado.setNumeroTelefono2(vista.txtNum2.getText());

            // Estos deberían venir de cbPuesto, cbJefeInmediato, etc.
            empleado.setIdPuesto(Integer.parseInt((String) vista.cbPuesto.getSelectedItem()));
            empleado.setHorarioEntrada(vista.txtHoraEntrada.getText());
            empleado.setHorarioSalida(vista.txtHoraSalida.getText());
            empleado.setIdJefeInmediato(Integer.parseInt((String) vista.cbJefeInmediato.getSelectedItem()));

            // Dirección
            empleado.setDepartamento((String) vista.cbDepto.getSelectedItem());
            empleado.setMunicipio((String) vista.cbMun.getSelectedItem());
            empleado.setAldeaColonia(vista.txtAldea.getText());
            empleado.setDireccionVivienda(vista.txtDireccion.getText());

            // Datos de huella (placeholder)
            empleado.setIdHuella(1);
            empleado.setHuella(new byte[0]);

            // El ID de dirección parece faltar: podrías obtenerlo desde algún campo oculto si lo tienes
            empleado.setIdDireccion(0); // cambiar si tienes un campo para esto

            boolean resultado = imp.actualizarEmpleado(empleado);

            if (resultado) {
                JOptionPane.showMessageDialog(null, "✅ Empleado actualizado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "❌ No se pudo actualizar el empleado.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "⚠️ Error de formato numérico: " + ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "⚠️ Error al actualizar empleado: " + ex.getMessage());
        }
    }
}


