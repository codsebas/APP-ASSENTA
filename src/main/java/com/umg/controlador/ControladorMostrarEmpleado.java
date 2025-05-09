package com.umg.controlador;

import com.umg.implementacion.EmpleadoImp;
import com.umg.modelos.ModeloEmpleado;
import com.umg.modelos.ModeloVistaRegistrarEmpleado;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ControladorMostrarEmpleado {

    EmpleadoImp implementacion = new EmpleadoImp();
    ModeloVistaRegistrarEmpleado modelo;
    ModeloEmpleado modeloEmpleado;



    public void mostrarEmpleado(){
        // String dpi = modelo.getVistaMostrarEmpleados().txtDPI.getText();
        //ModeloEmpleado modeloEmpleado = implementacion.mostrarEmpleado(dpi);

        if (modelo != null) {
            DefaultTableModel tabla = new DefaultTableModel();
            tabla.addColumn("dpi_empleado");
            tabla.addColumn("sexo_empleado");
            tabla.addColumn("estado_civil");
            tabla.addColumn("nombre1_empleado ");
            tabla.addColumn("nombre2_empleado");
            tabla.addColumn("nombre3_empleado");
            tabla.addColumn("apellido1_empleado");
            tabla.addColumn("apellido2_empleado");
            tabla.addColumn("apellidocasada_empleado");
            tabla.addColumn("fec_nacimiento");
            tabla.addColumn("edad_empleado");
            tabla.addColumn("puesto_id");
            tabla.addColumn("email_empleado");
            tabla.addColumn("telefono1_empleado");
            tabla.addColumn("telefono2_empleado");
            tabla.addColumn("horario_entrada");
            tabla.addColumn("horario_salida");
            tabla.addColumn("jefe_inmediato_id");

            Object[] fila = {

                    modeloEmpleado.getDpi(),
                    modeloEmpleado.getSexo(),
                    modeloEmpleado.getEstadoCivil(),
                    modeloEmpleado.getPrimerNombre(),
                    modeloEmpleado.getSegundoNombre(),
                    modeloEmpleado.getTercerNombre(),
                    modeloEmpleado.getPrimerApellido(),
                    modeloEmpleado.getSegundoApellido(),
                    modeloEmpleado.getApellidoCasada(),
                    modeloEmpleado.getFechaNacimiento(),
                    modeloEmpleado.getEdad(),
                    modeloEmpleado.getIdPuesto(),
                    modeloEmpleado.getCorreoElectronico(),
                    modeloEmpleado.getNumeroTelefono1(),
                    modeloEmpleado.getNumeroTelefono2(),
                    modeloEmpleado.getHorarioEntrada(),
                    modeloEmpleado.getHorarioSalida(),
                    modeloEmpleado.getIdJefeInmediato()
            };

            tabla.addRow(fila);
            modeloEmpleado.getVista().jTable1.setModel(tabla);
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró el empleado con ese DPI.");
        }
    }
}
