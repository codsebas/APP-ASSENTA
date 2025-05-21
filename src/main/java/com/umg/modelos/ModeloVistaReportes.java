package com.umg.modelos;

import com.umg.vistas.VistaReportes;

public class ModeloVistaReportes {

    VistaReportes vReportes;


    public ModeloVistaReportes() {
    }

    public ModeloVistaReportes(VistaReportes vReportes) {
        this.vReportes = vReportes;
    }

    public VistaReportes getvReportes() {
        return vReportes;
    }

    public void setvReportes(VistaReportes vReportes) {
        this.vReportes = vReportes;
    }
}
