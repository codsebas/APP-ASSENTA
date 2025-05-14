package com.umg.interfaces;

import com.umg.modelos.ModeloEmpleado;
import com.umg.modelos.ModeloHuella;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public interface IEmpleados {

    public boolean insertarEmpleado(ModeloEmpleado modelo);
    public boolean eliminarEmpleado(String dpi_empleado);
    public DefaultTableModel modeloEmpleado();
    public DefaultTableModel modeloEmpleado(int dpi_empleado);
    ModeloEmpleado mostrarEmpleado(String dpi_empleado);

    ModeloEmpleado mostrarEmpleadoPorDpi(String dpi_empleado);

    public boolean actualizarEmpleado(ModeloEmpleado modelo);

    DefaultTableModel modeloEmpleado(String dpi);

    //ModeloEmpleado mostrarEmpleado(String dpi);

    public DefaultTableModel mostrarTodosLosEmpleados();

    public boolean guardarHuella(List<ModeloHuella> modelo);
}
