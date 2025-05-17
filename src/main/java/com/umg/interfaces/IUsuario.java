package com.umg.interfaces;

import com.umg.modelos.Modelo;
import com.umg.modelos.ModeloEmpleado;
import com.umg.modelos.ModeloUsuario;

import javax.swing.table.DefaultTableModel;

public interface IUsuario {
    boolean insertarUsuario(ModeloUsuario modeloUsuario);
    boolean actualizarUsuario(ModeloUsuario modeloUsuario);
    boolean eliminarUsuario(int idUsuario);
    DefaultTableModel modeloUsuario();
    DefaultTableModel modeloUsuario(int idUsuario);

    ModeloUsuario MostrarUsuarioPorDPI(Modelo modeloUsuario);


    ModeloEmpleado MostrarUsuarioPorDPI(String dpi_empleado);

    String mostrarUsuarioPorDPI(String dpi_empleado);
}
