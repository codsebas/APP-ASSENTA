package com.umg.sql;

public class Sql {
    private final String CONSULTA_TODOS_EMPLEADO = "SELECT " +
            "e.id_empleado, " +
            "e.dpi_empleado, " +
            "e.sexo_empleado, " +
            "e.estado_civil, " +
            "e.nombre1_empleado, " +
            "e.nombre2_empleado, " +
            "e.nombre3_empleado, " +
            "e.apellido1_empleado, " +
            "e.apellido2_empleado, " +
            "e.apellidocasada_empleado, " +
            "e.edad_empleado, " +
            "p.nombre_puesto, " +
            "e.email_empleado, " +
            "e.telefono1_empleado, " +
            "e.horario_entrada, " +
            "e.horario_salida, " +
            "CONCAT(j.nombre1_empleado, ' ', j.apellido1_empleado) AS jefe_inmediato_nombre " +
            "FROM empleado e " +
            "INNER JOIN puesto p ON e.puesto_id = p.id_puesto " +
            "LEFT JOIN empleado j ON e.jefe_inmediato_id = j.id_empleado";
    private final String CONSULTA_EMPLEADO_DPI = "SELECT " +
            "e.id_empleado, e.dpi_empleado, e.sexo_empleado, e.estado_civil, " +
            "e.nombre1_empleado, e.nombre2_empleado, e.nombre3_empleado, " +
            "e.apellido1_empleado, e.apellido2_empleado, e.apellidocasada_empleado, " +
            "e.fec_nacimiento, e.edad_empleado, p.nombre_puesto, e.email_empleado, " +
            "e.telefono1_empleado, e.telefono2_empleado, e.horario_entrada, e.horario_salida, " +
            "CONCAT(j.nombre1_empleado, ' ', j.apellido1_empleado) AS jefe_inmediato_nombre " +
            "FROM empleado e " +
            "INNER JOIN puesto p ON e.puesto_id = p.id_puesto " +
            "LEFT JOIN empleado j ON e.jefe_inmediato_id = j.id_empleado " +
            "WHERE e.dpi_empleado = ?";


    private final String CONSULTA_EMPLEADO_DPIUPD =
            "SELECT e.id_empleado, e.dpi_empleado, e.sexo_empleado, e.estado_civil, " +
                    "e.nombre1_empleado, e.nombre2_empleado, e.nombre3_empleado, " +
                    "e.apellido1_empleado, e.apellido2_empleado, e.apellidocasada_empleado, " +
                    "e.fec_nacimiento, e.edad_empleado, p.nombre_puesto, e.puesto_id AS id_puesto, " +
                    "e.email_empleado, e.telefono1_empleado, e.telefono2_empleado, " +
                    "e.horario_entrada, e.horario_salida, " +
                    "e.jefe_inmediato_id AS id_jefe_inmediato, " +
                    "CONCAT(j.nombre1_empleado, ' ', j.apellido1_empleado) AS nombre_jefe_inmediato, " +
                    "d.departamento, d.municipio, d.aldea, d.direccion " +
                    "FROM empleado e " +
                    "LEFT JOIN puesto p ON e.puesto_id = p.id_puesto " +
                    "LEFT JOIN empleado j ON e.jefe_inmediato_id = j.id_empleado " +
                    "LEFT JOIN direccion_empleado d ON e.id_empleado = d.empleado_id " +
                    "WHERE e.dpi_empleado = ?";

    private final String INSERTAR_EMPLEADO = """
                    INSERT INTO empleado (
                        dpi_empleado, sexo_empleado, estado_civil, nombre1_empleado,
                        nombre2_empleado, nombre3_empleado, apellido1_empleado, apellido2_empleado,
                        apellidocasada_empleado, fec_nacimiento, edad_empleado, puesto_id,
                        email_empleado, telefono1_empleado, telefono2_empleado,
                        horario_entrada, horario_salida, jefe_inmediato_id
                    ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NULLIF(?, 0))
                    RETURNING id_empleado;
            
            """;


    private final String INSERTAR_DIRECCION = """
            INSERT INTO direccion_empleado (
                empleado_id, departamento, municipio, aldea, direccion
            ) VALUES (?, ?, ?, ?, ?);
            """;
    private final String ACTUALIZAR_DIRECCION = """
            UPDATE direccion_empleado
            SET departamento = ?,
                municipio = ?,
                aldea = ?,
                direccion = ?
            WHERE empleado_id = ?;
            """;

    private final String CONSULTA_EMPLEADO_DPI_ELIMINAR =  "SELECT e.nombre1_empleado, e.apellido1_empleado, p.nombre_puesto " +
            "FROM empleado e " +
            "INNER JOIN puesto p ON e.puesto_id = p.id_puesto " +
            "WHERE e.dpi_empleado = ?";
    private final String INSERTAR_HUELLA = "INSERT INTO huella (empleado_id, huella) VALUES (?, ?);";
    private final String ACTUALIZAR_EMPLEADO =
            "UPDATE empleado SET\n" +
                    "    sexo_empleado = ?, estado_civil = ?, nombre1_empleado = ?, nombre2_empleado = ?, nombre3_empleado = ?,\n" +
                    "    apellido1_empleado = ?, apellido2_empleado = ?, apellidocasada_empleado = ?, fec_nacimiento = ?, edad_empleado = ?,\n" +
                    "    puesto_id = ?, email_empleado = ?, telefono1_empleado = ?, telefono2_empleado = ?, horario_entrada = ?, horario_salida = ?, jefe_inmediato_id = ?\n" +
                    "WHERE dpi_empleado = ?\n";

    private final String ELIMINAR_EMPLEADO = "DELETE FROM cliente WHERE dpi_empleado = ?";
    private final String CONSULTA_TODOS_USUARIO = "SELECT * FROM usuarios";
    private final String CONSULTA_USUARIO = "SELECT * FROM usuarios WHERE usuario = ?";
    private final String CONSULTA_USUARIO_LOGIN = "SELECT * FROM usuarios WHERE usuario = ? AND password = crypt(?, password)";
    private final String INSERTAR_USUARIO = "INSERT INTO usuarios (usuario, password, empleado_dpi) VALUES (?, crypt(?, gen_salt('bf')),?)";
    private final String ACTUALIZAR_USUARIO = "UPDATE usuarios SET password = crypt(?, gen_salt('bf')), usuario = ? WHERE id_usuario = ?";
    private final String ELIMINAR_USUARIO = "DELETE FROM usuarios WHERE id_usuario = ?";

    private final String CONSULTAR_PUESTO = "SELECT * FROM empleado WHERE id_empleado = ?";
    private final String CONSULTA_TODOS_PUESTOS = "SELECT id_puesto, nombre_puesto FROM puesto";
    private final String INSERTAR_PUESTO = "INSERT INTO puesto (nombre_puesto, desc_puesto) VALUES (?, ?)";
    private final String ACTUALIZAR_PUESTO = "UPDATE puesto SET nombre_puesto = ?, desc_puesto = ? WHERE id_puesto = ?";
    private final String ELIMINAR_PUESTO = "DELETE FROM puesto WHERE id_puesto = ?";
    private final String CONSULTAR_TODOS_JEFES_INMEDIATOS = "SELECT id_empleado, nombre1_empleado || ' ' || apellido1_empleado AS nombre_completo FROM empleado";
    private final String CONSULTA_TABLA_PUESTO = "SELECT * FROM puesto";

    private final String OBTENER_ID_EMPLEADO_POR_DPI_ELIMINAR = "SELECT id_empleado FROM empleado WHERE dpi_empleado = ?";
    private final String ELIMINAR_HUELLA_ELIMINAR = "DELETE FROM huella WHERE empleado_id = ?";
    private final String ELIMINAR_DIRECCION_ELIMINAR = "DELETE FROM direccion_empleado WHERE empleado_id = ?";
    private final String ELIMINAR_USUARIO_ELIMINAR = "DELETE FROM usuarios WHERE empleado_dpi = ?";
    private final String ELIMINAR_EMPLEADO_ELIMINAR = "DELETE FROM empleado WHERE dpi_empleado = ?";
    private final String INSERTAR_HUELLA_EMPLEADO = "INSERT INTO asistencia_huella (empleado_id, huella_template) VALUES (?, ?)";

    public Sql() {
    }

    public String getACTUALIZAR_EMPLEADO() {
        return ACTUALIZAR_EMPLEADO;
    }

    public String getCONSULTA_TODOS_EMPLEADO() {
        return CONSULTA_TODOS_EMPLEADO;
    }

    public String getACTUALIZAR_DIRECCION() {
        return ACTUALIZAR_DIRECCION;
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

    public String getCONSULTA_TODOS_PUESTOS() {
        return CONSULTA_TODOS_PUESTOS;
    }

    public String getINSERTAR_PUESTO() {
        return INSERTAR_PUESTO;
    }

    public String getACTUALIZAR_PUESTO() {
        return ACTUALIZAR_PUESTO;
    }

    public String getELIMINAR_PUESTO() {
        return ELIMINAR_PUESTO;
    }

    public String getCONSULTAR_PUESTO() {
        return CONSULTAR_PUESTO;
    }

    public String getCONSULTA_EMPLEADO_DPIUPD() {
        return CONSULTA_EMPLEADO_DPIUPD;
    }

    public String getCONSULTAR_TODOS_JEFES_INMEDIATOS() {
        return CONSULTAR_TODOS_JEFES_INMEDIATOS;
    }

    public String getCONSULTA_TABLA_PUESTO() {
        return CONSULTA_TABLA_PUESTO;
    }

    public String getCONSULTA_EMPLEADO_DPI_ELIMINAR() {
        return CONSULTA_EMPLEADO_DPI_ELIMINAR;
    }

    public String getOBTENER_ID_EMPLEADO_POR_DPI_ELIMINAR() { return OBTENER_ID_EMPLEADO_POR_DPI_ELIMINAR; }

    public String getELIMINAR_HUELLA_ELIMINAR() { return ELIMINAR_HUELLA_ELIMINAR; }

    public String getELIMINAR_DIRECCION_ELIMINAR() { return ELIMINAR_DIRECCION_ELIMINAR; }

    public String getELIMINAR_USUARIO_ELIMINAR() { return ELIMINAR_USUARIO_ELIMINAR; }

    public String getELIMINAR_EMPLEADO_ELIMINAR() { return ELIMINAR_EMPLEADO_ELIMINAR; }

    public String getINSERTAR_HUELLA_EMPLEADO(){ return INSERTAR_HUELLA_EMPLEADO; }

}
