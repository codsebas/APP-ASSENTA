package com.umg.implementacion;

import com.umg.interfaces.IEmpleados;
import com.umg.modelos.ModeloEmpleado;
import com.umg.sql.Conector;
import com.umg.sql.Sql;

import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmpleadoImp implements IEmpleados {

    Conector conector = new Conector();
    Sql sql = new Sql();
    PreparedStatement ps;
    ResultSet rs;


    @Override
    public boolean insertarEmpleado(ModeloEmpleado modelo) {
        boolean resultado = true;
        conector.conectar();

        try {
            ps = conector.preparar(sql.getINSERTAR_EMPLEADO());
            ps.setInt(1, modelo.getIdEmpleado());
            ps.setString(2, modelo.getDpi());
            ps.setString(3, modelo.getSexo());
            ps.setString(4, modelo.getPrimerNombre());
            ps.setString(5, modelo.getSegundoNombre());
            ps.setString(6, modelo.getTercerNombre());
            ps.setString(7, modelo.getPrimerApellido());
            ps.setString(8, modelo.getSegundoApellido());
            ps.setString(9, modelo.getApellidoCasada());
            ps.setString(10, modelo.getFechaNacimiento());
            ps.setInt(11, modelo.getEdad());
            ps.setInt(12, modelo.getIdPuesto());
            ps.setString(13, modelo.getHorarioEntrada());
            ps.setString(14, modelo.getHorarioSalida());
            ps.setInt(15, modelo.getIdJefeInmediato());
            ps.setInt(16, modelo.getIdDireccion());
            ps.setString(17, modelo.getDepartamento());
            ps.setString(18, modelo.getMunicipio());
            ps.setString(19, modelo.getAldeaColonia());
            ps.setString(20, modelo.getDireccionVivienda());
            ps.setInt(21, modelo.getIdHuella());
            ps.setBytes(22, modelo.getHuella()); // ← Aquí insertas la huella como byte[]

            ps.executeUpdate();
            resultado = true;


            // Ejecutar y procesar resultado...
        } catch (SQLException ex) {
            conector.mensaje("No se pudo ingresar el empleado", "Error al eliminar", 0);
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
    public ModeloEmpleado mostrarEmpleado(int dpi_empleado) {
        boolean resultado = true;
        conector.conectar();
        ModeloEmpleado modelo = new ModeloEmpleado();
        try{
            rs = ps.executeQuery();
            while (rs.next()) {
                ps =    conector.preparar(sql.getCONSULTA_TODOS_EMPLEADO());
                ps.setInt(1, dpi_empleado);
                modelo.setIdEmpleado(Integer.parseInt(rs.getString(1)));
                modelo.setDpi(rs.getString(2));
                modelo.setSexo(rs.getString(3));
                modelo.setPrimerNombre(rs.getString(4));
                modelo.setSegundoNombre(rs.getString(5));
                modelo.setTercerNombre(rs.getString(6));
                modelo.setPrimerApellido(rs.getString(7));
                modelo.setSegundoApellido(rs.getString(8));
                modelo.setApellidoCasada(rs.getString(9));
                modelo.setFechaNacimiento(rs.getString(10));
                modelo.setEdad(rs.getInt(11));
                modelo.setIdPuesto(Integer.parseInt(rs.getString(12)));
                modelo.setHorarioEntrada(rs.getString(13));
                modelo.setHorarioSalida(rs.getString(14));
                modelo.setIdJefeInmediato(Integer.parseInt(rs.getString(15)));
                modelo.setIdDireccion(Integer.parseInt(rs.getString(16)));
                modelo.setDepartamento(rs.getString(17));
                modelo.setMunicipio(rs.getString(18));
                modelo.setAldeaColonia(rs.getString(19));
                modelo.setDireccionVivienda(rs.getString(20));
                modelo.setIdHuella(Integer.parseInt(rs.getString(21)));
                modelo.setHuella(rs.getBytes(21));

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
        return false;
    }

    @Override
    public DefaultTableModel modeloEmpleado(String dpi) {
        return null;
    }

    @Override
    public ModeloEmpleado mostrarEmpleado(String dpi) {
        return null;
    }
}
//NITIDOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO
//gerson te odio vv ojala don diablo ame mas aaxel