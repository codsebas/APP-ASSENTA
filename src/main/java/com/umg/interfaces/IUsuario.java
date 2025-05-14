package com.umg.interfaces;

import com.umg.modelos.ModeloUsuario;

import javax.swing.table.DefaultTableModel;

public interface IUsuario {
    boolean insertarUsuario(ModeloUsuario modeloUsuario);
    boolean actualizarUsuario(ModeloUsuario modeloUsuario);
    boolean eliminarUsuario(int idUsuario);
    DefaultTableModel modeloUsuario();
    DefaultTableModel modeloUsuario(int idUsuario);
}
