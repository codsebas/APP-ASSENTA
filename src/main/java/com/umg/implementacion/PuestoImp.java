package com.umg.implementacion;
import java.util.ArrayList;
import java.util.List;
import com.umg.interfaces.IPuestos;
import com.umg.modelos.Modelo;
import com.umg.modelos.ModeloJefeInmediato;
import com.umg.modelos.ModeloPuesto;
import com.umg.sql.Conector;
import com.umg.sql.Sql;

import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PuestoImp implements IPuestos {
    Conector conector = new Conector();
    Sql sql = new Sql();
    PreparedStatement ps;
    ResultSet rs;


    @Override
    public boolean insertarPuesto(ModeloPuesto modelo) {
        boolean resultado = false;
        conector.conectar();
        try {
            ps = conector.preparar(sql.getINSERTAR_PUESTO());
            ps.setString(1, modelo.getNombrePuesto());
            ps.setString(2, modelo.getDescripcionPuesto());
            ps.executeUpdate();
            resultado = true;
        } catch (SQLException e) {
            conector.mensaje("No se pudo insertar el puesto", "Error al insertar", 0);
        } finally {
            conector.desconectar();
        }
        return resultado;
    }

    @Override
    public boolean eliminarPuesto(int idPuesto) {
        boolean resultado = false;
        conector.conectar();
        try {
            ps = conector.preparar(sql.getELIMINAR_PUESTO());
            ps.setInt(1, idPuesto);
            resultado = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            conector.mensaje("No se pudo eliminar el puesto", "Error al eliminar", 0);
        } finally {
            conector.desconectar();
        }
        return resultado;
    }

    @Override
    public boolean actualizarPuesto(ModeloPuesto modelo) {
        boolean resultado = false;
        conector.conectar();
        try {
            ps = conector.preparar(sql.getACTUALIZAR_PUESTO());
            ps.setString(1, modelo.getNombrePuesto());
            ps.setString(2, modelo.getDescripcionPuesto());
            ps.setInt(3, modelo.getIdPuesto());
            resultado = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            conector.mensaje("No se pudo actualizar el puesto", "Error al actualizar", 0);
        } finally {
            conector.desconectar();
        }
        return resultado;
    }

    @Override
    public ModeloPuesto mostrarPuesto(int idPuesto) {
        ModeloPuesto modelo = null;
        conector.conectar();
        try {
            ps = conector.preparar(sql.getCONSULTAR_PUESTO());
            ps.setInt(1, idPuesto);
            rs = ps.executeQuery();
            if (rs.next()) {
                modelo = new ModeloPuesto();
                modelo.setIdPuesto(rs.getInt("id_puesto"));
                modelo.setNombrePuesto(rs.getString("nombre_puesto"));
            }
        } catch (SQLException e) {
            conector.mensaje("No se pudo consultar el puesto", "Error al consultar", 0);
        } finally {
            conector.desconectar();
        }
        return modelo;
    }

    @Override
    public ModeloJefeInmediato mostrarJefeInmediato(int idJefeInmediato) {
        return null;
    }

    @Override
    public DefaultTableModel modeloPuesto() {
        DefaultTableModel modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID Puesto");
        modeloTabla.addColumn("Nombre Puesto");
        modeloTabla.addColumn("Descripcion");

        conector.conectar();
        try {
            ps = conector.preparar(sql.getCONSULTA_TABLA_PUESTO());
            rs = ps.executeQuery();
            while (rs.next()) {
                Object[] fila = {
                        rs.getInt("id_puesto"),
                        rs.getString("nombre_puesto"),
                        rs.getString("desc_puesto")
                };
                modeloTabla.addRow(fila);
            }
        } catch (SQLException e) {
            conector.mensaje("Error al cargar los puestos", "Error", 0);
        } finally {
            conector.desconectar();
        }

        return modeloTabla;
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
}