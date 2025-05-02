package com.umg.implementacion;

import com.umg.interfaces.ILogin;
import com.umg.modelos.ModeloLogin;
import com.umg.sql.Conector;
import com.umg.sql.Sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginImp implements ILogin {
    Conector conector = new Conector();
    Sql sql = new Sql();
    PreparedStatement ps;
    ResultSet rs;

    @Override
    public boolean consultaUsuario(String usuario, String password) {
        boolean respuesta = false;
        this.conector.conectar();
        try {
            this.ps = this.conector.preparar(this.sql.getCONSULTA_USUARIO_LOGIN());
            this.ps.setString(1, usuario);
            this.ps.setString(2, password);
            this.rs = this.ps.executeQuery();

            if(rs.next()) {
                respuesta = true;
            }

            this.conector.desconectar();
        }catch (SQLException e){
            this.conector.mensaje(e.getMessage(), "Error al buscar el usuario", 0);
        }
        return respuesta;
    }
}
