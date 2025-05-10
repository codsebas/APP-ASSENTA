package com.umg.modelos;

import com.umg.vistas.VistaMantenimientoUsuarios;

public class ModeloUsuario {
    private int idUsuario;
    private String usuario;
    private String password;
    private String empleado_dpi;
    private VistaMantenimientoUsuarios vista;

    public ModeloUsuario() {
    }

    public ModeloUsuario(VistaMantenimientoUsuarios vista) {
        this.vista = vista;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public VistaMantenimientoUsuarios getVista() {
        return vista;
    }

    public void setVista(VistaMantenimientoUsuarios vista) {
        this.vista = vista;
    }

    public String getEmpleado_dpi() {
        return empleado_dpi;
    }

    public void setEmpleado_dpi(String empleado_dpi) {
        this.empleado_dpi = empleado_dpi;
    }
}
