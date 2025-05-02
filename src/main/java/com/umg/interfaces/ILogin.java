package com.umg.interfaces;

import com.umg.modelos.ModeloLogin;

public interface ILogin {
    boolean consultaUsuario(String usuario, String password);
}
