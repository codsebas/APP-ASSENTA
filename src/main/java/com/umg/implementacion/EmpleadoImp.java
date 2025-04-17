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
           conector.mensaje("No se pudo eliminar el cliente", "Error al eliminar", 0);
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
        return null;
    }

    @Override
    public boolean actualizarEmpleado(ModeloEmpleado modelo) {
        return false;
    }
}
//NITIDOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO