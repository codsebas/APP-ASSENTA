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
    private String apellidoCasado;
    private String fechaNacimiento; // Se puede usar java.util.Date o java.time.LocalDate si lo prefieres
    private int edad;

    // DATOS CONTACTO
    private String correoElectronico;
    private String numeroTelefono1;
    private String numeroTelefono2;

    // DIRECCION
    private String departamento;
    private String municipio;
    private String aldeaColonia;
    private String direccionVivienda;

    // PUESTO EN LA EMPRESA
    private int idPuesto;
    private String puesto;

    // JORNADA
    private String horarioEntrada;
    private String horarioSalida;

    // JEFE
    private int idJefeInmediato;

    // HUELLAS DIGITALES (puedes cambiar a byte[] si planeas manejar los datos binarios reales)
    private String huella1;
    private String huella2;
    private String huella3;
    private String huella4;

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

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
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

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getHorarioSalida() {
        return horarioSalida;
    }

    public void setHorarioSalida(String horarioSalida) {
        this.horarioSalida = horarioSalida;
    }

    public String getHuella1() {
        return huella1;
    }

    public void setHuella1(String huella1) {
        this.huella1 = huella1;
    }

    public int getIdJefeInmediato() {
        return idJefeInmediato;
    }

    public void setIdJefeInmediato(int idJefeInmediato) {
        this.idJefeInmediato = idJefeInmediato;
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
// Getters y Setters (puedes generarlos automáticamente si usas un IDE como IntelliJ o Eclipse)

    // Constructor vacío
    public ModeloEmpleado() {}

    public ModeloEmpleado(int idEmpleado, String dpi, String sexo, String estadoCivil, String primerNombre, String segundoNombre, String tercerNombre, String primerApellido, String segundoApellido, String apellidoCasado, String fechaNacimiento, int edad, String correoElectronico, String numeroTelefono1, String numeroTelefono2, String departamento, String municipio, String aldeaColonia, String direccionVivienda, int idPuesto, String puesto, String horarioEntrada, String horarioSalida, int idJefeInmediato, String huella1, String huella2, String huella3, String huella4) {
        this.idEmpleado = idEmpleado;
        this.dpi = dpi;
        this.sexo = sexo;
        this.estadoCivil = estadoCivil;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.tercerNombre = tercerNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.apellidoCasado = apellidoCasado;
        this.fechaNacimiento = fechaNacimiento;
        this.edad = edad;
        this.correoElectronico = correoElectronico;
        this.numeroTelefono1 = numeroTelefono1;
        this.numeroTelefono2 = numeroTelefono2;
        this.departamento = departamento;
        this.municipio = municipio;
        this.aldeaColonia = aldeaColonia;
        this.direccionVivienda = direccionVivienda;
        this.idPuesto = idPuesto;
        this.puesto = puesto;
        this.horarioEntrada = horarioEntrada;
        this.horarioSalida = horarioSalida;
        this.idJefeInmediato = idJefeInmediato;
        this.huella1 = huella1;
        this.huella2 = huella2;
        this.huella3 = huella3;
        this.huella4 = huella4;
    }

}

