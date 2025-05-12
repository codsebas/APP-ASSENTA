package com.umg.modelos;

import com.umg.vistas.VistaMantenimientoPuestos;

public class ModeloPuesto {
    private VistaMantenimientoPuestos vistaPuestos;
    private int idPuesto;
    private String nombrePuesto;
    private String descripcionPuesto;

    public ModeloPuesto() {}

    public ModeloPuesto(VistaMantenimientoPuestos vistaPuestos) {
        this.vistaPuestos = vistaPuestos;
    }

    public ModeloPuesto(int idPuesto, String nombrePuesto) {
        this.idPuesto = idPuesto;
        this.nombrePuesto = nombrePuesto;
    }

    public VistaMantenimientoPuestos getVistaPuestos() {
        return vistaPuestos;
    }

    public void setVistaPuestos(VistaMantenimientoPuestos vistaPuestos) {
        this.vistaPuestos = vistaPuestos;
    }

    public int getIdPuesto() {
        return idPuesto;
    }

    public void setIdPuesto(int idPuesto) {
        this.idPuesto = idPuesto;
    }

    public String getNombrePuesto() {
        return nombrePuesto;
    }

    public void setNombrePuesto(String nombrePuesto) {
        this.nombrePuesto = nombrePuesto;
    }

    public String getDescripcionPuesto() {
        return descripcionPuesto;
    }

    public void setDescripcionPuesto(String descripcionPuesto) {
        this.descripcionPuesto = descripcionPuesto;
    }

    @Override
    public String toString() {
        return nombrePuesto; // Para que se muestre correctamente en el ComboBox
    }
}

