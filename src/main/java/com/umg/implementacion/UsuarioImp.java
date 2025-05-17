package com.umg.implementacion;

import com.umg.interfaces.IUsuario;
import com.umg.modelos.Modelo;
import com.umg.modelos.ModeloEmpleado;
import com.umg.modelos.ModeloUsuario;
import com.umg.sql.Conector;
import com.umg.sql.Sql;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioImp implements IUsuario {
    Conector conector = new Conector();
    Sql sql = new Sql();
    PreparedStatement ps;
    ResultSet rs;

    public UsuarioImp() {
    }

    @Override
    public boolean insertarUsuario(ModeloUsuario modeloUsuario) {
        boolean resultado = true;
        this.conector.conectar();
        this.ps = this.conector.preparar(this.sql.getINSERTAR_USUARIO());

        try {
            this.ps.setString(1, modeloUsuario.getUsuario());
            this.ps.setString(2, modeloUsuario.getPassword());
            this.ps.setString(3, modeloUsuario.getEmpleado_dpi());
            resultado = this.ps.execute();
        } catch (SQLException ex) {
            if (ex.getSQLState().equals("23505")) {
                JOptionPane.showMessageDialog(null, "El usuario ingresado ya existe, por favor intente de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                this.conector.mensaje(ex.getMessage(), "Error en la insercion", 0);
            }
        }
        return resultado;
    }

    @Override
    public boolean actualizarUsuario(ModeloUsuario modeloUsuario) {
        boolean resultado = true;
        this.conector.conectar();
        this.ps = this.conector.preparar(this.sql.getACTUALIZAR_USUARIO());
        try {
            this.ps.setString(1, modeloUsuario.getPassword()); // Nueva contraseña
            this.ps.setString(2, modeloUsuario.getUsuario()); // Nuevo nombre de usuario
            this.ps.setString(3, modeloUsuario.getEmpleado_dpi()); // ID del usuario a actualizar
            resultado = this.ps.executeUpdate() > 0; // Devuelve true si se actualizó al menos una fila
        } catch (SQLException e) {
            this.conector.mensaje(e.getMessage(), "Error en la actualización", 0);
        } finally {
            this.conector.desconectar();
        }
        return resultado;
    }



    @Override
    public boolean eliminarUsuario(int idUsuario) {
        boolean resultado = false; // Cambiar a false por defecto
        this.conector.conectar();
        this.ps = this.conector.preparar(this.sql.getELIMINAR_USUARIO());
        try {
            this.ps.setInt(1, idUsuario);
            resultado = this.ps.executeUpdate() > 0; // Cambiar a executeUpdate
        } catch (SQLException e) {
            this.conector.mensaje(e.getMessage(), "Error en la eliminación", 0);
        } finally {
            this.conector.desconectar(); // Asegúrate de desconectar en el bloque finally
        }
        return resultado;
    }

    @Override
    public DefaultTableModel modeloUsuario() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new Object[]{"ID", "Usuario"});
        this.conector.conectar();
        try {
            this.ps = this.conector.preparar(this.sql.getCONSULTA_TODOS_USUARIO());
            this.rs = this.ps.executeQuery();

            while (rs.next()) {
                modelo.addRow(new Object[]{this.rs.getInt("id_usuario"), rs.getString("usuario")});
            }
        } catch (SQLException e) {
            this.conector.mensaje(e.getMessage(), "Error en la consulta", 0);
            this.conector.desconectar();
        }
        return modelo;
    }

    @Override
    public DefaultTableModel modeloUsuario(int idUsuario) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new Object[]{"ID", "Usuario"});
        this.conector.conectar();
        try {
            this.ps = this.conector.preparar(this.sql.getCONSULTA_USUARIO());
            this.ps.setInt(1, idUsuario);
            this.rs = this.ps.executeQuery();

            while (rs.next()) {
                modelo.addRow(new Object[]{this.rs.getInt("id_usuario"), rs.getString("usuario")});
            }
        } catch (SQLException e) {
            this.conector.mensaje(e.getMessage(), "Error en la consulta", 0);
            this.conector.desconectar();
        }
        return modelo;
    }

    @Override
    public ModeloUsuario MostrarUsuarioPorDPI(Modelo modeloUsuario) {
        return null;
    }

    @Override
    public ModeloEmpleado MostrarUsuarioPorDPI(String dpi_empleado) {
        return null;
    }


    @Override
    public String mostrarUsuarioPorDPI(String dpi_empleado) {
        conector.conectar();
        String usuario = null;
        try {
            // Aquí debes usar la consulta SQL que recupera el usuario por DPI
            ps = conector.preparar(sql.getCONSULTAR_USUARIO_POR_DPI()); // Asegúrate de tener este método en tu clase Sql
            ps.setString(1, dpi_empleado);
            rs = ps.executeQuery();
            if (rs.next()) {
                usuario = rs.getString("usuario"); // Cambia "usuario" por el nombre de la columna que contiene el usuario
            }
        } catch (SQLException ex) {
            conector.mensaje(ex.getMessage(), "Error al mostrar usuario", 0);
        } finally {
            conector.desconectar();
        }
        return usuario; // Devuelve el nombre de usuario
    }

    public boolean eliminarUsuarioConValidacion(String dpi, String password) {

        return false;
    }

}
