package com.umg.modelos;

public class ModeloJefeInmediato {

    private int idEmpleado;
    private String nombreCompleto;

    public ModeloJefeInmediato(int idEmpleado, String nombreCompleto) {
        this.idEmpleado = idEmpleado;
        this.nombreCompleto = nombreCompleto;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    @Override
    public String toString() {
        return nombreCompleto;
    }
}

