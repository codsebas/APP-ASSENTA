package com.umg.controlador;

import com.umg.implementacion.EmpleadoImp;
import com.umg.modelos.ModeloEmpleado;
import com.umg.modelos.ModeloVistaMostrarEmpleado;
import com.umg.sql.Conector;
import com.umg.sql.Sql;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ControladorMostrarEmpleado implements ActionListener, MouseListener {
    Conector conector = new Conector();
    Sql sql = new Sql();
    PreparedStatement ps;
    ResultSet rs;

   ModeloVistaMostrarEmpleado modelo;

    public ControladorMostrarEmpleado(ModeloVistaMostrarEmpleado modelo) {
        this.modelo= modelo;
        mostrarTodosLosEmpleados();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    EmpleadoImp implementacion = new EmpleadoImp();





    public void mostrarEmpleado(){
         String dpi = modelo.getVistaMostrarEmpleados().txtDPI.getText();
        ModeloEmpleado modeloEmpleado = implementacion.mostrarEmpleado(dpi);

        if (modelo != null) {
            DefaultTableModel tabla = new DefaultTableModel();
            tabla.addColumn("DPI");
            tabla.addColumn("Sexo");
            tabla.addColumn("Estado Civil");
            tabla.addColumn("1er.Nombre");
            tabla.addColumn("2do.Nombre");
            tabla.addColumn("3er.Nombre");
            tabla.addColumn("1er.Apellido");
            tabla.addColumn("2do.Apellido");
            tabla.addColumn("Apellido de Casada");
            tabla.addColumn("Fech-Nacimiento");
            tabla.addColumn("Edad");
            tabla.addColumn("Puesto");
            tabla.addColumn("Email");
            tabla.addColumn("1er.telefono");
            tabla.addColumn("2do.telefono");
            tabla.addColumn("Hor-Entrada");
            tabla.addColumn("Hor-Salida");
            tabla.addColumn("Jefe");

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
                    modeloEmpleado.getNombrePuesto(),
                    modeloEmpleado.getCorreoElectronico(),
                    modeloEmpleado.getNumeroTelefono1(),
                    modeloEmpleado.getNumeroTelefono2(),
                    modeloEmpleado.getHorarioEntrada(),
                    modeloEmpleado.getHorarioSalida(),
                    modeloEmpleado.getNombreJefeInmediato()
            };

            tabla.addRow(fila);
            modelo.getVistaMostrarEmpleados().jTable1.setModel(tabla);
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró el empleado con ese DPI.");
        }
    }

    public void mostrarTodosLosEmpleados() {
        Conector conector = new Conector();
        Sql sql = new Sql();
        PreparedStatement ps;
        ResultSet rs;

        try {
            conector.conectar(); //
            ps = conector.preparar(sql.getCONSULTA_TODOS_EMPLEADO());
            rs = ps.executeQuery();

            DefaultTableModel tabla = new DefaultTableModel();
            tabla.addColumn("DPI");
            tabla.addColumn("Sexo");
            tabla.addColumn("Estado Civil");
            tabla.addColumn("1er. Nombre");
            tabla.addColumn("2do. Nombre");
            tabla.addColumn("3er. Nombre");
            tabla.addColumn("1er. Apellido");
            tabla.addColumn("2do. Apellido");
            tabla.addColumn("Apellido de Casada");
            tabla.addColumn("Fecha de Nacimiento");
            tabla.addColumn("Edad");
            tabla.addColumn("Puesto");
            tabla.addColumn("Correo Electrónico");
            tabla.addColumn("Teléfono 1");
            tabla.addColumn("Teléfono 2");
            tabla.addColumn("Horario Entrada");
            tabla.addColumn("Horario Salida");
            tabla.addColumn("Jefe Inmediato");

            while (rs.next()) {
                Object[] fila = {
                        rs.getString("dpi_empleado"),
                        rs.getString("sexo_empleado"),
                        rs.getString("estado_civil"),
                        rs.getString("nombre1_empleado"),
                        rs.getString("nombre2_empleado"),
                        rs.getString("nombre3_empleado"),
                        rs.getString("apellido1_empleado"),
                        rs.getString("apellido2_empleado"),
                        rs.getString("apellidocasada_empleado"),
                        rs.getString("fec_nacimiento"),
                        rs.getInt("edad_empleado"),
                        rs.getString("nombre_puesto"),
                        rs.getString("email_empleado"),
                        rs.getString("telefono1_empleado"),
                        rs.getString("telefono2_empleado"),
                        rs.getString("horario_entrada"),
                        rs.getString("horario_salida"),
                        rs.getString("jefe_inmediato_nombre")
                };
                tabla.addRow(fila);
            }

            modelo.getVistaMostrarEmpleados().jTable1.setModel(tabla);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getComponent().equals(modelo.getVistaMostrarEmpleados().btnBuscarEmpleado)){
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
}
