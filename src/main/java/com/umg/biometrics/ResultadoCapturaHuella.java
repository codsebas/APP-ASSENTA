package com.umg.biometrics;

import com.digitalpersona.uareu.Fid;
import com.digitalpersona.uareu.Fmd;

public class ResultadoCapturaHuella {
    private Fmd plantilla;
    private Fid imagen;

    public ResultadoCapturaHuella(Fmd plantilla, Fid imagen) {
        this.plantilla = plantilla;
        this.imagen = imagen;
    }

    public Fmd getPlantilla() {
        return plantilla;
    }

    public Fid getImagen() {
        return imagen;
    }
}
