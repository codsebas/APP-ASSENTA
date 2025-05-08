package com.umg.controlador;

import com.umg.implementacion.PuestoImp;
import com.umg.modelos.ModeloPuesto;

import java.util.List;

public class ControladorPuesto {

    private PuestoImp puestoDao;

    public ControladorPuesto() {
        puestoDao = new PuestoImp();
    }

    public List<ModeloPuesto> obtenerListaPuestos() {
        return puestoDao.obtenerPuestos();
    }
}