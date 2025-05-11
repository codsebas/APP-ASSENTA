package com.umg.controlador;

import com.umg.implementacion.PuestoImp;
import com.umg.modelos.ModeloJefeInmediato;
import com.umg.modelos.ModeloPuesto;

import javax.swing.*;
import java.util.List;

public class ControladorPuesto {

    private PuestoImp puestoDao;

    public ControladorPuesto() {
        puestoDao = new PuestoImp();
    }

    public List<ModeloPuesto> obtenerListaPuestos() {
        return puestoDao.obtenerPuestos();
    }
    public void cargarPuestos(JComboBox<String> comboBox) {
        PuestoImp puesto = new PuestoImp();
        List<ModeloPuesto> puestos = puesto.obtenerPuestos();
        comboBox.removeAllItems();

        for (ModeloPuesto p : puestos) {
            comboBox.addItem(p.toString());
        }
    }
    public void cargarJefes(JComboBox<String> comboBox) {
        PuestoImp jefe = new PuestoImp();
        List<ModeloJefeInmediato> jefes = jefe.obtenerJefesInmediatos();
        comboBox.removeAllItems();

        for (ModeloJefeInmediato j : jefes) {
            comboBox.addItem(String.valueOf(j));
        }
    }


}