package com.umg.implementacion;

import com.umg.interfaces.IEmpleados;
import com.umg.modelos.ModeloEmpleado;
import com.umg.modelos.ModeloJefeInmediato;
import com.umg.modelos.ModeloPuesto;
import com.umg.sql.Conector;
import com.umg.sql.Sql;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoImp implements IEmpleados {

    Conector conector = new Conector();
    Sql sql = new Sql();
    PreparedStatement ps;
    ResultSet rs;


    private void setNullableString(PreparedStatement ps, int index, String valor) throws SQLException {
        if (valor == null || valor.trim().isEmpty()) {
            ps.setNull(index, Types.VARCHAR);
        } else {
            ps.setString(index, valor);
        }
    }
    private void setNullableTime(PreparedStatement ps, int index, String timeStr) throws SQLException {
        if (timeStr == null || timeStr.trim().isEmpty()) {
            ps.setNull(index, java.sql.Types.TIME);
        } else {
            ps.setTime(index, Time.valueOf(timeStr));
        }
    }

    @Override
    public boolean insertarEmpleado(ModeloEmpleado modelo) {
        boolean resultado = true;
        conector.conectar();
        int id_empleado;

        try {
            ps = conector.preparar(sql.getINSERTAR_EMPLEADO());
            ps.setString(1, modelo.getDpi());
            ps.setString(2, modelo.getSexo());
            ps.setString(3, modelo.getEstadoCivil());
            ps.setString(4, modelo.getPrimerNombre());

            setNullableString(ps, 5, modelo.getSegundoNombre()); // ← Segundo nombre opcional
            setNullableString(ps, 6, modelo.getTercerNombre());  // ← Ya estaba bien

            ps.setString(7, modelo.getPrimerApellido());
            ps.setString(8, modelo.getSegundoApellido());
            setNullableString(ps, 9, modelo.getApellidoCasada()); // ← Ya estaba bien

            ps.setDate(10, Date.valueOf(modelo.getFechaNacimiento()));
            ps.setInt(11, modelo.getEdad());
            ps.setInt(12, modelo.getIdPuesto()); // puesto_id

            setNullableString(ps, 13, modelo.getCorreoElectronico()); // ← Opcional
            ps.setString(14, modelo.getNumeroTelefono1());
            setNullableString(ps, 15, modelo.getNumeroTelefono2()); // ← Opcional

            // Hora entrada/salida como opcionales:
            setNullableTime(ps, 16, modelo.getHorarioEntrada());
            setNullableTime(ps, 17, modelo.getHorarioSalida());

            ps.setInt(18, modelo.getIdJefeInmediato());

            this.rs = this.ps.executeQuery();
            if (rs.next()) {
                id_empleado = rs.getInt("id_empleado");
            } else {
                throw new SQLException("No se pudo obtener el ID del empleado insertado.");
            }
            try {
                ps = conector.preparar(sql.getINSERTAR_DIRECCION());
                ps.setInt(1, id_empleado);
                ps.setString(2, modelo.getDepartamento());
                ps.setString(3, modelo.getMunicipio());
                ps.setString(4, modelo.getAldeaColonia());
                ps.setString(5, modelo.getDireccionVivienda());
                ps.executeUpdate();
            } catch (Exception e) {
                conector.mensaje(e.getMessage(), "Error al ingresar direccion", 0);
            }
            /*
            try {
                ps = conector.preparar(sql.getINSERTAR_HUELLA());
                for (int i = 1; i <= 4; i++) {
                    ps.setInt(1, id_empleado);
                    ps.setBytes(2, generarHuellaFicticia(i)); // Función para generar huellas
                    ps.executeUpdate();
                }
                }
                */

            resultado = true;


            // Ejecutar y procesar resultado...
        } catch (SQLException ex) {
            conector.mensaje(ex.getMessage(), "Error al ingresar", 0);
            return resultado;
        }


        return false;
    }

    @Override
    public boolean eliminarEmpleado(String dpi_empleado) {
        boolean resultado = true;
        conector.conectar();
        try {
            ps = conector.preparar(sql.getELIMINAR_EMPLEADO());
            ps.setInt(1, Integer.parseInt(dpi_empleado));
            return ps.execute();

        } catch (SQLException ex) {
            conector.mensaje("No se pudo eliminar el empleado", "Error al eliminar", 0);
            return resultado;
        }

    }

    @Override
    public DefaultTableModel modeloEmpleado() {
        DefaultTableModel tabla = new DefaultTableModel();

        try {
            conector.conectar(); //
            ps = conector.preparar(sql.getCONSULTA_TODOS_EMPLEADO());
            rs = ps.executeQuery();


            tabla.setColumnIdentifiers(new Object[]{"DPI", "Sexo", "Estado Civil", "1er.Nombre", "2do.Nombre", "3er.Nombre", "1er.Apellido",
                    "2do.Apellido", "Apellido de Casada", "Edad", "Puesto", "Email", "telefono", "Hor-Entrada", "Hor-Salida", "Jefe"});

            while (rs.next()) {
                tabla.addRow(new Object[]{
                        rs.getString("dpi_empleado"),
                        rs.getString("sexo_empleado"),
                        rs.getString("estado_civil"),
                        rs.getString("nombre1_empleado"),
                        rs.getString("nombre2_empleado"),
                        rs.getString("nombre3_empleado"),
                        rs.getString("apellido1_empleado"),
                        rs.getString("apellido2_empleado"),
                        rs.getString("apellidocasada_empleado"),
                        rs.getInt("edad_empleado"),
                        rs.getString("nombre_puesto"),
                        rs.getString("email_empleado"),
                        rs.getString("telefono1_empleado"),
                        rs.getString("horario_entrada"),
                        rs.getString("horario_salida"),
                        rs.getString("jefe_inmediato_nombre")
                });

            }


        } catch (SQLException ex) {
        }
        return tabla;
}

    @Override
    public DefaultTableModel modeloEmpleado(int dpi_empleado) {
        return null;
    }

    @Override
    public ModeloEmpleado mostrarEmpleado(String dpi_empleado) {
        conector.conectar();
        ModeloEmpleado modelo = null; // <- Aquí importante: empieza como null

        try {
            ps = conector.preparar(sql.getCONSULTA_EMPLEADO_DPI()); // SELECT * FROM empleado WHERE dpi_empleado = ?
            ps.setString(1, dpi_empleado);
            rs = ps.executeQuery();

            if (rs.next()) {
                modelo = new ModeloEmpleado(); // Solo si encuentra resultado

                modelo.setDpi(rs.getString(2));                           // dpi_empleado
                modelo.setSexo(rs.getString(3));                          // sexo_empleado
                modelo.setEstadoCivil(rs.getString(4));                   // estado_civil
                modelo.setPrimerNombre(rs.getString(5));                  // nombre1_empleado
                modelo.setSegundoNombre(rs.getString(6));                 // nombre2_empleado
                modelo.setTercerNombre(rs.getString(7));                  // nombre3_empleado
                modelo.setPrimerApellido(rs.getString(8));                // apellido1_empleado
                modelo.setSegundoApellido(rs.getString(9));               // apellido2_empleado
                modelo.setApellidoCasada(rs.getString(10));               // apellidocasada_empleado
                modelo.setFechaNacimiento(rs.getString(11));              // fec_nacimiento
                modelo.setEdad(rs.getInt(12));                            // edad_empleado
                modelo.setNombrePuesto(rs.getString(13));                 // nombre_puesto (viene del JOIN)
                modelo.setCorreoElectronico(rs.getString(14));            // email_empleado
                modelo.setNumeroTelefono1(rs.getString(15));              // telefono1_empleado
                modelo.setNumeroTelefono2(rs.getString(16));              // telefono2_empleado
                modelo.setHorarioEntrada(rs.getString(17));               // horario_entrada
                modelo.setHorarioSalida(rs.getString(18));                // horario_salida
                modelo.setNombreJefeInmediato(rs.getString(19));          // nombre del jefe (concat del JOIN)
     // jefe_inmediato_id

            }

            conector.desconectar();
        } catch (SQLException ex) {
            conector.mensaje(ex.getMessage(), "Error al mostrar", 0);
            conector.desconectar();
        }

        return modelo;
    }

    @Override
    public ModeloEmpleado mostrarEmpleadoPorDpi(String dpi_empleado) {
        conector.conectar();
        ModeloEmpleado modelo = null; // Empezamos como null

        try {
            // Usamos la consulta SQL definida en tu clase SQL
            ps = conector.preparar(sql.getCONSULTA_EMPLEADO_DPIUPD()); // Selecciona el empleado con el DPI dado
            ps.setString(1, dpi_empleado);
            rs = ps.executeQuery();

            if (rs.next()) {
                modelo = new ModeloEmpleado(); // Solo si encuentra un resultado

                // Llenamos el objeto modelo con los datos recuperados
                modelo.setIdEmpleado(rs.getInt("id_empleado"));
                modelo.setDpi(rs.getString("dpi_empleado"));              // dpi_empleado
                modelo.setSexo(rs.getString("sexo_empleado"));            // sexo_empleado
                modelo.setEstadoCivil(rs.getString("estado_civil"));     // estado_civil
                modelo.setPrimerNombre(rs.getString("nombre1_empleado")); // nombre1_empleado
                modelo.setSegundoNombre(rs.getString("nombre2_empleado")); // nombre2_empleado
                modelo.setTercerNombre(rs.getString("nombre3_empleado")); // nombre3_empleado
                modelo.setPrimerApellido(rs.getString("apellido1_empleado")); // apellido1_empleado
                modelo.setSegundoApellido(rs.getString("apellido2_empleado")); // apellido2_empleado
                modelo.setApellidoCasada(rs.getString("apellidocasada_empleado")); // apellidocasada_empleado
                modelo.setFechaNacimiento(rs.getString("fec_nacimiento")); // fec_nacimiento
                modelo.setEdad(rs.getInt("edad_empleado"));

                modelo.setDepartamento(rs.getString( "departamento" ));
                modelo.setMunicipio(rs.getString("municipio"));
                modelo.setAldeaColonia(rs.getString("aldea"));
                modelo.setDireccionVivienda(rs.getString("direccion"));
                modelo.setNombrePuesto(rs.getString("nombre_puesto"));   // nombre_puesto (viene del JOIN)
                modelo.setCorreoElectronico(rs.getString("email_empleado")); // email_empleado
                modelo.setNumeroTelefono1(rs.getString("telefono1_empleado")); // telefono1_empleado
                modelo.setNumeroTelefono2(rs.getString("telefono2_empleado")); // telefono2_empleado
                modelo.setHorarioEntrada(rs.getString("horario_entrada")); // horario_entrada
                modelo.setHorarioSalida(rs.getString("horario_salida")); // horario_salida
                modelo.setNombreJefeInmediato(rs.getString("nombre_jefe_inmediato")); // nombre_jefe_inmediato
            }

            conector.desconectar();
        } catch (SQLException ex) {
            conector.mensaje(ex.getMessage(), "Error al mostrar empleado", 0);
            conector.desconectar();
        }

        return modelo;
    }



    @Override
    public boolean actualizarEmpleado(ModeloEmpleado modelo) {
        boolean resultado = false;
        conector.conectar();

        try {
            ps = conector.preparar(sql.getACTUALIZAR_EMPLEADO());
            ps.setInt(17, modelo.getIdEmpleado());
            ps.setString(1, modelo.getSexo());
            ps.setString(2, "C");
            ps.setString(3, modelo.getPrimerNombre());
            ps.setString(4, modelo.getSegundoNombre());
            ps.setString(5, modelo.getTercerNombre());
            ps.setString(6, modelo.getPrimerApellido());
            ps.setString(7, modelo.getSegundoApellido());
            ps.setString(8, modelo.getApellidoCasada());
            ps.setDate(9, Date.valueOf(modelo.getFechaNacimiento()));
            ps.setInt(10, modelo.getEdad());
            ps.setString(11, modelo.getCorreoElectronico());
            ps.setString(12, modelo.getNumeroTelefono1());
            ps.setString(13, modelo.getNumeroTelefono2());
            ps.setTime(14, Time.valueOf(modelo.getHorarioEntrada()));
            ps.setTime(15, Time.valueOf(modelo.getHorarioSalida()));
//            ps.setInt(14, modelo.getIdJefeInmediato());
//            ps.setInt(15, modelo.getIdDireccion());
//            ps.setString(16, modelo.getDepartamento());
//            ps.setString(17, modelo.getMunicipio());
//            ps.setString(18, modelo.getAldeaColonia());
//            ps.setString(19, modelo.getDireccionVivienda());
//            ps.setInt(20, modelo.getIdHuella());
//            ps.setBytes(21, modelo.getHuella());
            ps.setInt(16, modelo.getIdJefeInmediato()); //
            ps.executeUpdate();
            resultado = true;
        } catch (SQLException ex) {
            conector.mensaje("No se pudo actualizar el empleado " + ex.getMessage(), "Error al actualizar", 0);
        } finally {
            conector.desconectar();
        }

        return resultado;
    }

    public List<ModeloPuesto> obtenerPuestos() {
        List<ModeloPuesto> lista = new ArrayList<>();
        conector.conectar();
        try {
            PreparedStatement stmt = conector.preparar(sql.getCONSULTA_TODOS_PUESTOS());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ModeloPuesto puesto = new ModeloPuesto(
                        rs.getInt("id_puesto"),
                        rs.getString("nombre_puesto")
                );
                lista.add(puesto);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            conector.mensaje("Error al obtener puestos: " + e.getMessage(), "Error", 0);
        } finally {
            conector.desconectar();
        }
        return lista;
    }

    public List<ModeloJefeInmediato> obtenerJefesInmediatos() {
        List<ModeloJefeInmediato> lista = new ArrayList<>();
        conector.conectar();
        try {
            PreparedStatement stmt = conector.preparar(sql.getCONSULTAR_TODOS_JEFES_INMEDIATOS());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ModeloJefeInmediato jefe = new ModeloJefeInmediato(
                        rs.getInt("id_empleado"),
                        rs.getString("nombre_completo")
                );
                lista.add(jefe);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            conector.mensaje("Error al obtener jefes inmediatos: " + e.getMessage(), "Error", 0);
        } finally {
            conector.desconectar();
        }
        return lista;

    }
    @Override
    public DefaultTableModel modeloEmpleado(String dpi) {
        return null;
    }

    @Override
    public DefaultTableModel mostrarTodosLosEmpleados() {
        return null;
    }

   /* @Override
    public ModeloEmpleado mostrarEmpleado(String dpi) {
        return null;
    */}
//}
//NITIDOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO
//gerson te odio vv ojala don diablo ame mas aaxel