package com.umg.sql;

public class Sql {
    private final String CONSULTA_TODOS_EMPLEADO = "SELECT * FROM empleado";
    private final String CONSULTA_EMPLEADO_DPI = "SELECT * FROM empleado WHERE dpi_empelado = ?";
    private final String INSERTAR_EMPLEADO = """
    INSERT INTO empleado (
        dpi_empleado, sexo_empleado, estado_civil, nombre1_empleado,
        nombre2_empleado, nombre3_empleado, apellido1_empleado, apellido2_empleado,
        apellidocasada_empleado, fec_nacimiento, edad_empleado, puesto_id,
        email_empleado, telefono1_empleado, telefono2_empleado,
        horario_entrada, horario_salida, jefe_inmediato_id
    ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    RETURNING id_empleado;
    """;


    private final String INSERTAR_DIRECCION = """
    INSERT INTO direccion_empleado (
        empleado_id, departamento, municipio, aldea, direccion
    ) VALUES (?, ?, ?, ?, ?);
    """;


    private final String INSERTAR_HUELLA = "INSERT INTO huella (empleado_id, huella) VALUES (?, ?);";
    private final String ACTUALIZAR_EMPLEADO =
            "UPDATE empleado SET dpi = ?, sexo = ?, primer_nombre = ?, segundo_nombre = ?, tercer_nombre = ?, " +
                    "primer_apellido = ?, segundo_apellido = ?, apellido_casada = ?, fecha_nacimiento = ?, edad = ?, " +
                    "id_puesto = ?, horario_entrada = ?, horario_salida = ?, id_jefe_inmediato = ?, id_direccion = ?, " +
                    "departamento = ?, municipio = ?, aldea_colonia = ?, direccion_vivienda = ?, id_huella = ?, huella = ? " +
                    "WHERE id_empleado = ?";
    private final String ELIMINAR_EMPLEADO = "DELETE FROM cliente WHERE dpi_empleado = ?";
    private final String CONSULTA_TODOS_USUARIO = "SELECT * FROM usuarios";
    private final String CONSULTA_USUARIO = "SELECT * FROM usuarios WHERE usuario = ?";
    private final String CONSULTA_USUARIO_LOGIN = "SELECT * FROM usuarios WHERE usuario = ? AND password = crypt(?, password)";
    private final String INSERTAR_USUARIO = "INSERT INTO usuarios (usuario, password) VALUES (?, crypt(?, gen_salt('bf')))";
    private final String ACTUALIZAR_USUARIO = "UPDATE usuarios SET password = crypt(?, gen_salt('bf')), usuario = ? WHERE id_usuario = ?";
    private final String ELIMINAR_USUARIO = "DELETE FROM usuarios WHERE id_usuario = ?";

    public Sql() {

    }
    public String getACTUALIZAR_EMPLEADO(){ return ACTUALIZAR_EMPLEADO; }

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

    public String getCONSULTA_USUARIO() {
        return CONSULTA_USUARIO;
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

    public String getCONSULTA_USUARIO_LOGIN() {
        return CONSULTA_USUARIO_LOGIN;
    }

    public String getINSERTAR_DIRECCION() {
        return INSERTAR_DIRECCION;
    }

    public String getINSERTAR_HUELLA() {
        return INSERTAR_HUELLA;
    }
}

//axel and hichos forever because your love is the best esto fue escrito por axel con any desk mientras javier se fue uwu dayane te amo

