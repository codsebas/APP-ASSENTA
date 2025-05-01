package Implementacion;


import com.umg.interfaces.IEmpleados;
import com.umg.modelos.ModeloEmpleado;
import com.umg.sql.Conector;
import com.umg.sql.Sql;

import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class EmpleadoImp implements IEmpleados {

    Conector conector = new Conector();
    Sql sql = new Sql();
    PreparedStatement ps;
    ResultSet rs;

    @Override
    public boolean insertarEmpleado(ModeloEmpleado modelo) {
        boolean resultado = true;
        conector.conectar();
        ps = conector.preparar(sql.getINSERTAR_EMPLEADO());
        try {
            ps.setString(1, modelo.getDpi());
            ps.setString(2, modelo.getSexo());
            ps.setString(3, modelo.getEstadoCivil());
            ps.setString(4, modelo.getPrimerNombre());
            ps.setString(5, modelo.getSegundoNombre());
            ps.setString(6, modelo.getTercerNombre());
            ps.setString(7, modelo.getPrimerApellido());
            ps.setString(8, modelo.getSegundoApellido());
            ps.setString(9, modelo.getApellidoCasada());
            ps.setDate(10, Date.valueOf(modelo.getFechaNacimiento()));
            ps.setInt(11, modelo.getEdad());
            ps.setString(12, modelo.getCorreoElectronico());
            ps.setString(13, modelo.getNumeroTelefono1());
            ps.setString(14, modelo.getNumeroTelefono2());
            // Otros campos, como el puesto o jefe inmediato, pueden ser agregados aquí
            ps.setInt(15, modelo.getIdPuesto());
            ps.setTime(16, Time.valueOf(modelo.getHorarioEntrada()));
            ps.setTime(17, Time.valueOf(modelo.getHorarioSalida()));
            ps.setInt(18, modelo.getIdJefeInmediato());
            ps.setInt(19, modelo.getIdDireccion());
            ps.setString(20, modelo.getDepartamento());
            ps.setString(21, modelo.getMunicipio());
            ps.setString(22, modelo.getAldeaColonia());
            ps.setString(23, modelo.getDireccionVivienda());
            ps.setInt(24, modelo.getIdHuella());
            ps.setBytes(25, modelo.getHuella());
            return ps.execute();
        } catch (SQLException ex) {
            conector.mensaje(ex.getMessage(), "Error en la inserción", 0);
            return false;
        } finally {
            conector.desconectar();
        }
    }

    @Override
    public boolean eliminarEmpleado(String dpi) {
        boolean resultado = true;
        conector.conectar();
        try {
            ps = conector.preparar(sql.getELIMINAR_EMPLEADO());
            ps.setString(1, dpi);
            return ps.execute();
        } catch (SQLException ex) {
            conector.mensaje("No se pudo eliminar el empleado", "Error al eliminar", 0);
            return false;
        } finally {
            conector.desconectar();
        }
    }

    @Override
    public DefaultTableModel modeloEmpleado() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new Object[]{"DPI", "Primer Nombre", "Apellido", "Correo", "Telefono"});
        conector.conectar();
        try {
            ps = conector.preparar(sql.getCONSULTA_TODOS_EMPLEADO());
            rs = ps.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[]{
                        rs.getString("dpi_empelado"),
                        rs.getString("primerNombre"),
                        rs.getString("primerApellido"),
                        rs.getString("correoElectronico"),
                        rs.getString("numeroTelefono1")});
            }
        } catch (SQLException ex) {
            conector.mensaje(ex.getMessage(), "Error al cargar los empleados", 0);
        } finally {
            conector.desconectar();
        }
        return modelo;
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

    @Override
    public DefaultTableModel modeloEmpleado(String dpi) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new Object[]{"DPI", "Primer Nombre", "Apellido", "Correo", "Telefono"});
        conector.conectar();
        try {
            ps = conector.preparar(sql.getCONSULTA_EMPLEADO_DPI());
            ps.setString(1, dpi);
            rs = ps.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[]{
                        rs.getString("dpi_empelado"),
                        rs.getString("primerNombre"),
                        rs.getString("primerApellido"),
                        rs.getString("correoElectronico"),
                        rs.getString("numeroTelefono1")});
            }
        } catch (SQLException ex) {
            conector.mensaje(ex.getMessage(), "Error al buscar el empleado", 0);
        } finally {
            conector.desconectar();
        }
        return modelo;
    }

    @Override
    public ModeloEmpleado mostrarEmpleado(String dpi) {
        ModeloEmpleado modelo = new ModeloEmpleado();
        conector.conectar();
        try {
            ps = conector.preparar(sql.getCONSULTA_EMPLEADO_DPI());
            ps.setString(1, dpi);
            rs = ps.executeQuery();
            if (rs.next()) {
                modelo.setDpi(rs.getString("dpi_empelado"));
                modelo.setSexo(rs.getString("sexo"));
                modelo.setEstadoCivil(rs.getString("estadoCivil"));
                modelo.setPrimerNombre(rs.getString("primerNombre"));
                modelo.setSegundoNombre(rs.getString("segundoNombre"));
                modelo.setTercerNombre(rs.getString("tercerNombre"));
                modelo.setPrimerApellido(rs.getString("primerApellido"));
                modelo.setSegundoApellido(rs.getString("segundoApellido"));
                modelo.setApellidoCasada(rs.getString("apellidoCasada"));
                modelo.setFechaNacimiento(String.valueOf(rs.getDate("fechaNacimiento")));
                modelo.setEdad(rs.getInt("edad"));
                modelo.setCorreoElectronico(rs.getString("correoElectronico"));
                modelo.setNumeroTelefono1(rs.getString("numeroTelefono1"));
                modelo.setNumeroTelefono2(rs.getString("numeroTelefono2"));
                modelo.setIdPuesto(rs.getInt("idPuesto"));
                modelo.setHorarioEntrada(String.valueOf(rs.getTime("horarioEntrada")));
                modelo.setHorarioSalida(String.valueOf(rs.getTime("horarioSalida")));
                modelo.setIdJefeInmediato(rs.getInt("idJefeInmediato"));
                modelo.setIdDireccion(rs.getInt("idDireccion"));
                modelo.setDepartamento(rs.getString("departamento"));
                modelo.setMunicipio(rs.getString("municipio"));
                modelo.setAldeaColonia(rs.getString("aldeaColonia"));
                modelo.setDireccionVivienda(rs.getString("direccionVivienda"));
                modelo.setIdHuella(rs.getInt("idHuella"));
                modelo.setHuella(rs.getBytes("huella"));
            }
        } catch (SQLException ex) {
            conector.mensaje(ex.getMessage(), "Error al mostrar el empleado", 0);
        } finally {
            conector.desconectar();
        }
        return modelo;
    }
}

//JAVIFA REVISA SI ESTA BIEN XXDXDXDXDXDXDXD