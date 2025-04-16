package com.umg.modelos;

public class ModeloEmpleado {

    // DATOS PERSONALES
    private String primerNombre;
    private String segundoNombre;
    private String tercerNombre;
    private String primerApellido;
    private String segundoApellido;
    private String apellidoCasado;
    private String fechaNacimiento;
    private int edad;
    private String dpi;

    // DATOS CONTACTO
    private String numeroTelefono;
    private String correoElectronico;

    // DIRECCION
    private String departamento;
    private String municipio;
    private String aldeaColonia;
    private String direccionVivienda;

    // PUESTO EN LA EMPRESA
    private String puesto;

    // JORNADA
    private String tipoJornada;
    private String diaInicioJornada;
    private String diaFinJornada;

    // HUELLAS XD
    private String huella1;
    private String huella2;
    private String huella3;
    private String huella4;

    public ModeloEmpleado() {
    }

    public ModeloEmpleado(String primerNombre, String segundoNombre, String tercerNombre,
                          String primerApellido, String segundoApellido, String apellidoCasado,
                          String fechaNacimiento, int edad, String dpi,
                          String numeroTelefono, String correoElectronico,
                          String departamento, String municipio, String aldeaColonia, String direccionVivienda,
                          String puesto, String tipoJornada, String diaInicioJornada, String diaFinJornada,
                          String huella1, String huella2, String huella3, String huella4) {
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.tercerNombre = tercerNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.apellidoCasado = apellidoCasado;
        this.fechaNacimiento = fechaNacimiento;
        this.edad = edad;
        this.dpi = dpi;
        this.numeroTelefono = numeroTelefono;
        this.correoElectronico = correoElectronico;
        this.departamento = departamento;
        this.municipio = municipio;
        this.aldeaColonia = aldeaColonia;
        this.direccionVivienda = direccionVivienda;
        this.puesto = puesto;
        this.tipoJornada = tipoJornada;
        this.diaInicioJornada = diaInicioJornada;
        this.diaFinJornada = diaFinJornada;
        this.huella1 = huella1;
        this.huella2 = huella2;
        this.huella3 = huella3;
        this.huella4 = huella4;
    }



    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getTercerNombre() {
        return tercerNombre;
    }

    public void setTercerNombre(String tercerNombre) {
        this.tercerNombre = tercerNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getApellidoCasado() {
        return apellidoCasado;
    }

    public void setApellidoCasado(String apellidoCasado) {
        this.apellidoCasado = apellidoCasado;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getAldeaColonia() {
        return aldeaColonia;
    }

    public void setAldeaColonia(String aldeaColonia) {
        this.aldeaColonia = aldeaColonia;
    }

    public String getDireccionVivienda() {
        return direccionVivienda;
    }

    public void setDireccionVivienda(String direccionVivienda) {
        this.direccionVivienda = direccionVivienda;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getTipoJornada() {
        return tipoJornada;
    }

    public void setTipoJornada(String tipoJornada) {
        this.tipoJornada = tipoJornada;
    }

    public String getDiaInicioJornada() {
        return diaInicioJornada;
    }

    public void setDiaInicioJornada(String diaInicioJornada) {
        this.diaInicioJornada = diaInicioJornada;
    }

    public String getDiaFinJornada() {
        return diaFinJornada;
    }

    public void setDiaFinJornada(String diaFinJornada) {
        this.diaFinJornada = diaFinJornada;
    }

    public String getHuella1() {
        return huella1;
    }

    public void setHuella1(String huella1) {
        this.huella1 = huella1;
    }

    public String getHuella2() {
        return huella2;
    }

    public void setHuella2(String huella2) {
        this.huella2 = huella2;
    }

    public String getHuella3() {
        return huella3;
    }

    public void setHuella3(String huella3) {
        this.huella3 = huella3;
    }

    public String getHuella4() {
        return huella4;
    }

    public void setHuella4(String huella4) {
        this.huella4 = huella4;
    }
}

