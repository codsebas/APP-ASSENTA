package com.umg.sql;

public class Sql {
    private final String CONSULTA_TODOS_EMPLEADO = "SELECT * FROM empleado";
    private final String CONSULTA_EMPLEADO_DPI = "SELECT * FROM empleado WHERE dpi_empelado = ?";
    private final String INSERTAR_EMPLEADO = "INSERT INTO empleado VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private final String ELIMINAR_EMPLEADO = "DELETE FROM cliente WHERE dpi_empleado = ?";

    public Sql() {

    }

    public String getCONSULTA_TODOS_EMPLEADO() {
        return CONSULTA_TODOS_EMPLEADO;
    }

    public String getCONSULTA_EMPLEADO_DPI() {
        return CONSULTA_EMPLEADO_DPI;
    }

    public String getINSERTAR_EMPLEADO() {
        return INSERTAR_EMPLEADO;
    }

    public String getELIMINAR_EMPLEADO() {
        return ELIMINAR_EMPLEADO;
    }
}
