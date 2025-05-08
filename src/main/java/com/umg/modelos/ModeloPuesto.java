package com.umg.modelos;

public class ModeloPuesto {
    private int idPuesto;
    private String nombrePuesto;

    public ModeloPuesto() {}

    public ModeloPuesto(int idPuesto, String nombrePuesto) {
        this.idPuesto = idPuesto;
        this.nombrePuesto = nombrePuesto;
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

    @Override
    public String toString() {
        return nombrePuesto; // Para que se muestre correctamente en el ComboBox
    }
}

