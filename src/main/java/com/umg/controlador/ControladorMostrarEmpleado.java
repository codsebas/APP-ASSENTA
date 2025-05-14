package com.umg.controlador;

import com.umg.implementacion.EmpleadoImp;
import com.umg.modelos.ModeloEmpleado;
import com.umg.modelos.ModeloVistaMostrarEmpleado;
import com.umg.sql.Conector;
import com.umg.sql.Sql;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class ControladorMostrarEmpleado implements ActionListener, MouseListener {


   ModeloVistaMostrarEmpleado modelo;

    public ControladorMostrarEmpleado(ModeloVistaMostrarEmpleado modelo) {
        this.modelo= modelo;
        mostrarTodosLosEmpleados();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    EmpleadoImp implementacion = new EmpleadoImp();

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getComponent().equals(modelo.getVistaMostrarEmpleados().btnBuscarEmpleado)){
            mostrarEmpleado();
        }

    }





    public void mostrarEmpleado() {
        String dpi = modelo.getVistaMostrarEmpleados().txtDPI.getText();
        ModeloEmpleado modeloEmpleado = implementacion.mostrarEmpleado(dpi);

        if (modeloEmpleado != null) {
            DefaultTableModel tabla = new DefaultTableModel();
            tabla.setColumnIdentifiers(new Object[]{
                    "DPI", "Sexo", "Estado Civil", "1er.Nombre", "2do.Nombre", "3er.Nombre", "1er.Apellido",
                    "2do.Apellido", "Apellido de Casada", "Edad", "Puesto", "Email", "telefono",
                    "Hor-Entrada", "Hor-Salida", "Jefe"
            });

            tabla.addRow(new Object[]{
                    modeloEmpleado.getDpi(),
                    modeloEmpleado.getSexo(),
                    modeloEmpleado.getEstadoCivil(),
                    modeloEmpleado.getPrimerNombre(),
                    modeloEmpleado.getSegundoNombre(),
                    modeloEmpleado.getTercerNombre(),
                    modeloEmpleado.getPrimerApellido(),
                    modeloEmpleado.getSegundoApellido(),
                    modeloEmpleado.getApellidoCasada(),
                    modeloEmpleado.getEdad(),
                    modeloEmpleado.getNombrePuesto(),
                    modeloEmpleado.getCorreoElectronico(),
                    modeloEmpleado.getNumeroTelefono1(),
                    modeloEmpleado.getHorarioEntrada(),
                    modeloEmpleado.getHorarioSalida(),
                    modeloEmpleado.getNombreJefeInmediato()
            });

            JTable tableEmpleados = new JTable(tabla);
            tableEmpleados.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            ajustarTamañoTabla(tableEmpleados);

            DefaultTableModel modeloTablaEmp = (DefaultTableModel) tableEmpleados.getModel();
            Vector<Vector> datos = modeloTablaEmp.getDataVector();
            Vector<String> columnas = new Vector<>();

            for(int i = 0; i < modeloTablaEmp.getColumnCount(); i++){
                columnas.add(modeloTablaEmp.getColumnName(i));
            }

            DefaultTableModel modeloNoEdit = new DefaultTableModel(datos, columnas){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            tableEmpleados.setModel(modeloNoEdit);

            for (int i = 0; i < tableEmpleados.getColumnCount(); i++) {
                TableColumn column = tableEmpleados.getColumnModel().getColumn(i);
                int ancho = 75;
                TableCellRenderer headerRenderer = tableEmpleados.getTableHeader().getDefaultRenderer();
                Component headerComp = headerRenderer.getTableCellRendererComponent(
                        tableEmpleados, column.getHeaderValue(), false, false, 0, i);
                ancho = Math.max(headerComp.getPreferredSize().width, ancho);
                for (int fila = 0; fila < tableEmpleados.getRowCount(); fila++) {
                    TableCellRenderer cellRenderer = tableEmpleados.getCellRenderer(fila, i);
                    Component cellComp = tableEmpleados.prepareRenderer(cellRenderer, fila, i);
                    ancho = Math.max(cellComp.getPreferredSize().width, ancho);
                }
                column.setPreferredWidth(ancho + 10);
            }

            tableEmpleados.getTableHeader().setReorderingAllowed(false);
            tableEmpleados.getTableHeader().setResizingAllowed(false);
            JScrollPane scrollTabla = new JScrollPane(
                    tableEmpleados,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
            );

            modelo.getVistaMostrarEmpleados().panelTabla.removeAll();
            modelo.getVistaMostrarEmpleados().panelTabla.setLayout(new java.awt.BorderLayout());
            modelo.getVistaMostrarEmpleados().panelTabla.add(scrollTabla, java.awt.BorderLayout.CENTER);
            modelo.getVistaMostrarEmpleados().panelTabla.revalidate();
            modelo.getVistaMostrarEmpleados().panelTabla.repaint();

        } else {
            JOptionPane.showMessageDialog(null, "No se encontró el empleado con ese DPI.");
        }
    }

    public void mostrarTodosLosEmpleados() {


        JTable tableEmpleados = new JTable(implementacion.modeloEmpleado());
        ajustarTamañoTabla(tableEmpleados);

        DefaultTableModel modeloTablaEmp = (DefaultTableModel) tableEmpleados.getModel();
        Vector<Vector> datos = modeloTablaEmp.getDataVector();
        Vector<String> columnas = new Vector<>();

        for(int i = 0; i < modeloTablaEmp.getColumnCount(); i++){
            columnas.add(modeloTablaEmp.getColumnName(i));
        }

        DefaultTableModel modeloNoEdit = new DefaultTableModel(datos, columnas){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableEmpleados.setModel(modeloNoEdit);

        tableEmpleados.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < tableEmpleados.getColumnCount(); i++) {
            TableColumn column = tableEmpleados.getColumnModel().getColumn(i);
            int ancho = 75; //

            TableCellRenderer headerRenderer = tableEmpleados.getTableHeader().getDefaultRenderer();
            Component headerComp = headerRenderer.getTableCellRendererComponent(
                    tableEmpleados, column.getHeaderValue(), false, false, 0, i);
            ancho = Math.max(headerComp.getPreferredSize().width, ancho);

            for (int fila = 0; fila < tableEmpleados.getRowCount(); fila++) {
                TableCellRenderer cellRenderer = tableEmpleados.getCellRenderer(fila, i);
                Component cellComp = tableEmpleados.prepareRenderer(cellRenderer, fila, i);
                ancho = Math.max(cellComp.getPreferredSize().width, ancho);
            }

            column.setPreferredWidth(ancho + 10);
        }

        tableEmpleados.getTableHeader().setReorderingAllowed(false);
        tableEmpleados.getTableHeader().setResizingAllowed(false);
        JScrollPane scrollTabla = new JScrollPane(
                tableEmpleados,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        modelo.getVistaMostrarEmpleados().panelTabla.removeAll();
        modelo.getVistaMostrarEmpleados().panelTabla.setLayout(new java.awt.BorderLayout());
        modelo.getVistaMostrarEmpleados().panelTabla.add(scrollTabla, java.awt.BorderLayout.CENTER);
        modelo.getVistaMostrarEmpleados().panelTabla.revalidate();
        modelo.getVistaMostrarEmpleados().panelTabla.repaint();
    }

        private void ajustarTamañoTabla(JTable tabla) {
        int rowHeight = tabla.getRowHeight();
        int rowCount = tabla.getRowCount();
        int headerHeight = tabla.getTableHeader().getPreferredSize().height;

        int alturaTotal = (rowHeight * rowCount) + headerHeight;
        tabla.setPreferredScrollableViewportSize(new Dimension(tabla.getPreferredSize().width, alturaTotal));
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
