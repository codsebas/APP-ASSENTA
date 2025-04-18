package com.umg.modelos;

import com.umg.vistas.VistaRegistrarEmpleado;

public class ModeloVistaRegistrarEmpleado {
    VistaRegistrarEmpleado vRegistraEmpleado;

    public ModeloVistaRegistrarEmpleado(VistaRegistrarEmpleado vRegistraEmpleado) {
        this.vRegistraEmpleado = vRegistraEmpleado;
    }

    public VistaRegistrarEmpleado getvRegistraEmpleado() {
        return vRegistraEmpleado;
    }

    public void setvRegistraEmpleado(VistaRegistrarEmpleado vRegistraEmpleado) {
        this.vRegistraEmpleado = vRegistraEmpleado;
    }
     
}
