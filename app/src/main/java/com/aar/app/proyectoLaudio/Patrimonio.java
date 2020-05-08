package com.aar.app.proyectoLaudio;

import android.widget.Button;

public class Patrimonio {

    int fondo;
    String nombre;
    Button btn;

    Patrimonio(int fondo, String nombre){
        this.fondo = fondo;
        this.nombre = nombre;
    }

    public int getFondo(){
        return fondo;
    }

    public String getNombre(){
        return nombre;
    }

    public void setFondo(int fondo) {
        this.fondo = fondo;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setBtn(Button btn){
        this.btn = btn;
    }

    public Button getBtn()
    {
        return btn;
    }
}