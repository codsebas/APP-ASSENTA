package com.umg.modelos;

import com.umg.vistas.VistaEliminarEmpleado;

public class ModeloEliminarEmpleado {
    private VistaEliminarEmpleado vistaEliminarEmpleados;
    private int idEmpleado;
    private int dpiEmpleado;
    private String nombreEmpleado;
    private String apellidoEmpleado;
    private String puestoEmpleado;

    public ModeloEliminarEmpleado() {
    }

    public ModeloEliminarEmpleado(VistaEliminarEmpleado vistaEliminarEmpleados) {
        this.vistaEliminarEmpleados = vistaEliminarEmpleados;
    }

    public VistaEliminarEmpleado getVistaEliminarEmpleados() {
        return vistaEliminarEmpleados;
    }

    public void setVistaEliminarEmpleados(VistaEliminarEmpleado vistaEliminarEmpleados) {
        this.vistaEliminarEmpleados = vistaEliminarEmpleados;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getDpiEmpleado() {
        return dpiEmpleado;
    }

    public void setDpiEmpleado(int dpiEmpleado) {
        this.dpiEmpleado = dpiEmpleado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getApellidoEmpleado() {
        return apellidoEmpleado;
    }

    public void setApellidoEmpleado(String apellidoEmpleado) {
        this.apellidoEmpleado = apellidoEmpleado;
    }

    public String getPuestoEmpleado() {
        return puestoEmpleado;
    }

    public void setPuestoEmpleado(String puestoEmpleado) {
        this.puestoEmpleado = puestoEmpleado;
    }
}
