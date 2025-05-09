package com.umg.implementacion;

import com.umg.interfaces.IEmpleados;
import com.umg.modelos.ModeloEmpleado;
import com.umg.sql.Conector;
import com.umg.sql.Sql;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.text.SimpleDateFormat;

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


    @Override
    public boolean insertarEmpleado(ModeloEmpleado modelo) {
        boolean resultado = true;
        conector.conectar();
        int id_empleado;

        try {
            ps = conector.preparar(sql.getINSERTAR_EMPLEADO());
            ps.setString(1, modelo.getDpi());
            ps.setString(2, modelo.getSexo());
           // ps.setString(3, modelo.getEstadoCivil());
            ps.setString(4, modelo.getPrimerNombre());
            ps.setString(5, modelo.getSegundoNombre());

            setNullableString(ps, 6, modelo.getTercerNombre()); // opcional
            ps.setString(7, modelo.getPrimerApellido());
            ps.setString(8, modelo.getSegundoApellido());

            setNullableString(ps, 9, modelo.getApellidoCasada()); // opcional

            ps.setDate(10, Date.valueOf(modelo.getFechaNacimiento()));
            ps.setInt(11, modelo.getEdad());
            ps.setInt(12, 1); // puesto_id
            ps.setString(13, modelo.getCorreoElectronico());
            ps.setString(14, modelo.getNumeroTelefono1());
            ps.setString(15, modelo.getNumeroTelefono2());
            ps.setTime(16, Time.valueOf(modelo.getHorarioEntrada()));
            ps.setTime(17, Time.valueOf(modelo.getHorarioSalida()));
            ps.setInt(18, 1); // jefe_inmediato_id


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
        try{
            ps =    conector.preparar(sql.getELIMINAR_EMPLEADO());
            ps.setInt(1, Integer.parseInt(dpi_empleado));
            return ps.execute();

        } catch (SQLException ex) {
           conector.mensaje("No se pudo eliminar el empleado", "Error al eliminar", 0);
        return resultado;
        }

    }

    @Override
    public DefaultTableModel modeloEmpleado() {
        return null;
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

                modelo.setIdEmpleado(rs.getInt(1));
                modelo.setDpi(rs.getString(2));
                modelo.setSexo(rs.getString(3));
                modelo.setPrimerNombre(rs.getString(4));
                modelo.setSegundoNombre(rs.getString(5));
                modelo.setTercerNombre(rs.getString(6));
                modelo.setPrimerApellido(rs.getString(7));
                modelo.setSegundoApellido(rs.getString(8));
                modelo.setApellidoCasada(rs.getString(9));

                // Usamos índice en lugar de nombre por seguridad
                modelo.setFechaNacimiento(rs.getString(10)); // columna 10 = fec_nacimiento

                modelo.setEdad(rs.getInt(11));
                modelo.setIdPuesto(rs.getInt(12));
                modelo.setHorarioEntrada(rs.getString(13));
                modelo.setHorarioSalida(rs.getString(14));
                modelo.setIdJefeInmediato(rs.getInt(15));
                modelo.setIdDireccion(rs.getInt(16));
                modelo.setDepartamento(rs.getString(17));
                modelo.setMunicipio(rs.getString(18));
                modelo.setAldeaColonia(rs.getString(19));
                modelo.setDireccionVivienda(rs.getString(20));
                modelo.setIdHuella(rs.getInt(21));
                modelo.setHuella(rs.getBytes(22));
            }

            conector.desconectar();
        } catch (SQLException ex) {
            conector.mensaje(ex.getMessage(), "Error al mostrar", 0);
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
            ps.setString(1, modelo.getDpi());
            ps.setString(2, modelo.getSexo());
            ps.setString(3, modelo.getPrimerNombre());
            ps.setString(4, modelo.getSegundoNombre());
            ps.setString(5, modelo.getTercerNombre());
            ps.setString(6, modelo.getPrimerApellido());
            ps.setString(7, modelo.getSegundoApellido());
            ps.setString(8, modelo.getApellidoCasada());
            ps.setString(9, modelo.getFechaNacimiento());
            ps.setInt(10, modelo.getEdad());
            ps.setInt(11, modelo.getIdPuesto());
            ps.setString(12, modelo.getHorarioEntrada());
            ps.setString(13, modelo.getHorarioSalida());
            ps.setInt(14, modelo.getIdJefeInmediato());
            ps.setInt(15, modelo.getIdDireccion());
            ps.setString(16, modelo.getDepartamento());
            ps.setString(17, modelo.getMunicipio());
            ps.setString(18, modelo.getAldeaColonia());
            ps.setString(19, modelo.getDireccionVivienda());
            ps.setInt(20, modelo.getIdHuella());
            ps.setBytes(21, modelo.getHuella());
            ps.setInt(22, modelo.getIdEmpleado()); //
            ps.executeUpdate();
            resultado = true;
        } catch (SQLException ex) {
            conector.mensaje("No se pudo actualizar el empleado", "Error al actualizar", 0);
        } finally {
            conector.desconectar();
        }

        return resultado;
    }
    @Override
    public DefaultTableModel modeloEmpleado(String dpi) {
        return null;
    }

   /* @Override
    public ModeloEmpleado mostrarEmpleado(String dpi) {
        return null;
    */}
//}
//NITIDOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO
//gerson te odio vv ojala don diablo ame mas aaxel