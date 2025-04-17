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
            ps.setInt(1, modelo.getId_empleado());
            ps.setString(2, modelo.getPrimerNombre());
            ps.setString(3, modelo.getSegundoNombre());
            ps.setString (4, modelo.getPrimerApellido());
            ps.setString (5, modelo.getSegundoApellido());
            ps.setString (6, modelo.getFechaNacimiento());
            ps.setInt(7, modelo.getEdad());
            ps.setString(8, modelo.getDpi());
            ps.setString(10, modelo.getNumeroTelefono());
            ps.setString(11, modelo.getCorreoElectronico());
            ps.setString(12,modelo.getDireccionVivienda());
            ps.setString(13, modelo.getMunicipio());
            ps.setString(14, modelo.getAldeaColonia());
            ps.setString(15, modelo.getDireccionVivienda());
            ps.setString(16, modelo.getPuesto());
            ps.setString(17, modelo.getTipoJornada());
            ps.setString(18, modelo.getDiaInicioJornada());
            ps.setString(19, modelo.getDiaFinJornada());
            ps.setString(20, modelo.getHuella1());
            ps.setString(21, modelo.getHuella2());
            ps.setString(22, modelo.getHuella3());
            ps.setString(23, modelo.getHuella4());


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
                modelo.setId_empleado(Integer.parseInt(rs.getString(1)));
                modelo.setPrimerNombre(rs.getString(2));
                modelo.setSegundoNombre(rs.getString(3));
                modelo.setPrimerApellido(rs.getString(4));
                modelo.setSegundoApellido(rs.getString(5));
                modelo.setFechaNacimiento(rs.getString(6));
                modelo.setEdad(Integer.parseInt(rs.getString(7)));
                modelo.setDpi(rs.getString(8));
                modelo.setNumeroTelefono(rs.getString(10));
                modelo.setCorreoElectronico(rs.getString(11));
                modelo.setDireccionVivienda(rs.getString(12));
                modelo.setMunicipio(rs.getString(13));
                modelo.setAldeaColonia(rs.getString(14));
                modelo.setDireccionVivienda(rs.getString(15));
                modelo.setPuesto(rs.getString(16));
                modelo.setTipoJornada(rs.getString(17));
                modelo.setDiaInicioJornada(rs.getString(18));
                modelo.setDiaFinJornada(rs.getString(19));
                modelo.setHuella1(rs.getString(20));
                modelo.setHuella2(rs.getString(21));
                modelo.setHuella3(rs.getString(22));
                modelo.setHuella4(rs.getString(23));

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
}
//NITIDOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO
//gerson te odio vv ojala don diablo ame mas aaxel