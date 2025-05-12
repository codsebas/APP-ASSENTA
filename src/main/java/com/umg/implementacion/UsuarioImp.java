package com.umg.implementacion;

import com.umg.interfaces.IUsuario;
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
        }catch (SQLException ex){
            if(ex.getSQLState().equals("23505")){
                JOptionPane.showMessageDialog(null, "El usuario ingresado ya existe, por favor intente de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
            }else {
                this.conector.mensaje(ex.getMessage(), "Error en la insercion", 0);
            }
        }
        return resultado;
    }

    @Override
    public boolean eliminarUsuario(int idUsuario) {
        boolean resultado = true;
        this.conector.conectar();
        this.ps = this.conector.preparar(this.sql.getELIMINAR_USUARIO());

        try {
            this.ps.setInt(1, idUsuario);
            resultado = this.ps.execute();
        } catch (SQLException e) {
            this.conector.mensaje(e.getMessage(), "Error en la eliminacion", 0);
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
}
