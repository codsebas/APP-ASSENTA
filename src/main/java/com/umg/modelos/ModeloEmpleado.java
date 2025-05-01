package com.umg.modelos;

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

    public ModeloEmpleado(int idEmpleado) {

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

    public ModeloEmpleado(byte[] huella) {
        this.huella = huella;
    }
}


