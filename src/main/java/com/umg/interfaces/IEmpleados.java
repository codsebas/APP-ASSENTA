package com.umg.interfaces;

import com.umg.modelos.ModeloEmpleado;

public interface IEmpleados {

    public boolean insertarEmpleado(ModeloEmpleado modelo);
    public boolean eliminarEmpleado(String codigo);



}
