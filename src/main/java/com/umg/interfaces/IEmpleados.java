package com.umg.interfaces;

import com.umg.modelos.ModeloEmpleado;

import javax.swing.table.DefaultTableModel;

public interface IEmpleados {

    public boolean insertarEmpleado(ModeloEmpleado modelo);
    public boolean eliminarEmpleado(String dpi_empleado);
    public DefaultTableModel modeloEmpleado();
    public DefaultTableModel modeloEmpleado(int dpi_empleado);
    public ModeloEmpleado mostrarEmpleado (int dpi_empleado);
    public boolean actualizarEmpleado(ModeloEmpleado modelo);

    DefaultTableModel modeloEmpleado(String dpi);

    ModeloEmpleado mostrarEmpleado(String dpi);
}
