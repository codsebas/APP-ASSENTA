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
}