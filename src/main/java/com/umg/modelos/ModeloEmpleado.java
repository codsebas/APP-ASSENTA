package com.umg.modelos;

import com.umg.vistas.VistaMostrarEmpleados;

public class ModeloEmpleado {

    // DATOS PERSONALES
    private int idEmpleado;
    private String dpi;
    private String sexo;
    private String estadoCivil; // 'C' o 'S'
    private String primerNombre;
    private String segundoNombre;
    private String tercerNombre;
    private String primerApellido;
    private String segundoApellido;
    private String apellidoCasada;
    private String fechaNacimiento; // Se puede usar java.util.Date o java.time.LocalDate si lo prefieres
    private int edad;

    // DATOS CONTACTO
    private String correoElectronico;
    private String numeroTelefono1;
    private String numeroTelefono2;

    // PUESTO EN LA EMPRESA
    private int idPuesto;

    // JORNADA
    private String horarioEntrada;
    private String horarioSalida;

    // JEFE
    private int idJefeInmediato;

    // DIRECCION
    private int idDireccion;
    private String departamento;
    private String municipio;
    private String aldeaColonia;
    private String direccionVivienda;

    // HUELLAS DIGITALES (puedes cambiar a byte[] si planeas manejar los datos binarios reales)
    private int idHuella;
    private byte[] huella;

    //Vista
private VistaMostrarEmpleados vista;

    public ModeloEmpleado() {

    }

    public ModeloEmpleado(int idEmpleado, String dpi, String sexo, String estadoCivil, String primerNombre, String segundoNombre, String tercerNombre, String primerApellido, String segundoApellido, String apellidoCasada, String fechaNacimiento, int edad, String correoElectronico, String numeroTelefono1, String numeroTelefono2, int idPuesto, String horarioEntrada, String horarioSalida, int idJefeInmediato) {
        this.idEmpleado = idEmpleado;
        this.dpi = dpi;
        this.sexo = sexo;
        this.estadoCivil = estadoCivil;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.tercerNombre = tercerNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.apellidoCasada = apellidoCasada;
        this.fechaNacimiento = fechaNacimiento;
        this.edad = edad;
        this.correoElectronico = correoElectronico;
        this.numeroTelefono1 = numeroTelefono1;
        this.numeroTelefono2 = numeroTelefono2;
        this.idPuesto = idPuesto;
        this.horarioEntrada = horarioEntrada;
        this.horarioSalida = horarioSalida;
        this.idJefeInmediato = idJefeInmediato;
    }

    public ModeloEmpleado(String dpi, String sexo, String estadoCivil, String primerNombre, String segundoNombre, String tercerNombre, String primerApellido, String segundoApellido, String apellidoCasada, String fechaNacimiento, int edad, String correoElectronico, String numeroTelefono1, String numeroTelefono2, int idPuesto, String horarioEntrada, String horarioSalida, int idJefeInmediato) {
        this.dpi = dpi;
        this.sexo = sexo;
        this.estadoCivil = estadoCivil;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.tercerNombre = tercerNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.apellidoCasada = apellidoCasada;
        this.fechaNacimiento = fechaNacimiento;
        this.edad = edad;
        this.correoElectronico = correoElectronico;
        this.numeroTelefono1 = numeroTelefono1;
        this.numeroTelefono2 = numeroTelefono2;
        this.idPuesto = idPuesto;
        this.horarioEntrada = horarioEntrada;
        this.horarioSalida = horarioSalida;
        this.idJefeInmediato = idJefeInmediato;
    }

    public ModeloEmpleado(int idDireccion, String departamento, String municipio, String aldeaColonia, String direccionVivienda) {
        this.idDireccion = idDireccion;
        this.departamento = departamento;
        this.municipio = municipio;
        this.aldeaColonia = aldeaColonia;
        this.direccionVivienda = direccionVivienda;
    }

    public ModeloEmpleado(String departamento, String municipio, String aldeaColonia, String direccionVivienda) {
        this.departamento = departamento;
        this.municipio = municipio;
        this.aldeaColonia = aldeaColonia;
        this.direccionVivienda = direccionVivienda;
    }

    public ModeloEmpleado(int idHuella, byte[] huella) {

        this.idHuella = idHuella;
        this.huella = huella;
    }

    public VistaMostrarEmpleados getVista() {
        return vista;
    }

    public void setVista(VistaMostrarEmpleados vista) {
        this.vista = vista;
    }

    public ModeloEmpleado(byte[] huella) {
        this.huella = huella;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
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

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getApellidoCasada() {
        return apellidoCasada;
    }

    public void setApellidoCasada(String apellidoCasada) {
        this.apellidoCasada = apellidoCasada;
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

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getNumeroTelefono1() {
        return numeroTelefono1;
    }

    public void setNumeroTelefono1(String numeroTelefono1) {
        this.numeroTelefono1 = numeroTelefono1;
    }

    public String getNumeroTelefono2() {
        return numeroTelefono2;
    }

    public void setNumeroTelefono2(String numeroTelefono2) {
        this.numeroTelefono2 = numeroTelefono2;
    }

    public int getIdPuesto() {
        return idPuesto;
    }

    public void setIdPuesto(int idPuesto) {
        this.idPuesto = idPuesto;
    }

    public String getHorarioEntrada() {
        return horarioEntrada;
    }

    public void setHorarioEntrada(String horarioEntrada) {
        this.horarioEntrada = horarioEntrada;
    }

    public String getHorarioSalida() {
        return horarioSalida;
    }

    public void setHorarioSalida(String horarioSalida) {
        this.horarioSalida = horarioSalida;
    }

    public int getIdJefeInmediato() {
        return idJefeInmediato;
    }

    public void setIdJefeInmediato(int idJefeInmediato) {
        this.idJefeInmediato = idJefeInmediato;
    }

    public int getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(int idDireccion) {
        this.idDireccion = idDireccion;
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

    public int getIdHuella() {
        return idHuella;
    }

    public void setIdHuella(int idHuella) {
        this.idHuella = idHuella;
    }

    public byte[] getHuella() {
        return huella;
    }

    public void setHuella(byte[] huella) {
        this.huella = huella;
    }
}


