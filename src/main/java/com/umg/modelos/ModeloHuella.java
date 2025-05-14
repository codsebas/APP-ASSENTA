package com.umg.modelos;
import com.digitalpersona.uareu.*;

public class ModeloHuella {
    private int id;
    private Fmd fmd;

    public ModeloHuella() {
    }

    public ModeloHuella(int id, Fmd fmd) {
        this.id = id;
        this.fmd = fmd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Fmd getFmd() {
        return fmd;
    }

    public void setFmd(Fmd fmd) {
        this.fmd = fmd;
    }
}
