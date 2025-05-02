package com.umg.sql;

public class Sql {
    private final String CONSULTA_TODOS_EMPLEADO = "SELECT * FROM empleado";
    private final String CONSULTA_EMPLEADO_DPI = "SELECT * FROM empleado WHERE dpi_empelado = ?";
    private final String INSERTAR_EMPLEADO = "INSERT INTO empleado VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private final String ELIMINAR_EMPLEADO = "DELETE FROM cliente WHERE dpi_empleado = ?";
    private final String CONSULTA_TODOS_USUARIO = "SELECT * FROM usuarios";
    private final String CONSULTA_USUARIO_ID = "SELECT * FROM usuarios WHERE id_usuario = ?";
    private final String INSERTAR_USUARIO = "INSERT INTO usuarios (usuario, password) VALUES (?, crypt(?, gen_salt('bf')))";
    private final String ACTUALIZAR_USUARIO = "UPDATE usuarios SET password = crypt(?, gen_salt('bf')), usuario = ? WHERE id_usuario = ?";
    private final String ELIMINAR_USUARIO = "DELETE FROM usuarios WHERE id_usuario = ?";

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

    public String getCONSULTA_TODOS_USUARIO() {
        return CONSULTA_TODOS_USUARIO;
    }

    public String getCONSULTA_USUARIO_ID() {
        return CONSULTA_USUARIO_ID;
    }

    public String getINSERTAR_USUARIO() {
        return INSERTAR_USUARIO;
    }

    public String getACTUALIZAR_USUARIO() {
        return ACTUALIZAR_USUARIO;
    }

    public String getELIMINAR_USUARIO() {
        return ELIMINAR_USUARIO;
    }
}
