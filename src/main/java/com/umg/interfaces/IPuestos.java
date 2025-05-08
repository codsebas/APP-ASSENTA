package com.umg.interfaces;

import com.umg.modelos.ModeloPuesto;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public interface IPuestos {
    boolean insertarPuesto(ModeloPuesto modelo);
    boolean eliminarPuesto(int idPuesto);
    boolean actualizarPuesto(ModeloPuesto modelo);
    ModeloPuesto mostrarPuesto(int idPuesto);
    DefaultTableModel modeloPuesto();
}