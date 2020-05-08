package com.aar.app.proyectoLaudio;

import android.graphics.drawable.Drawable;

public class Foto {

    String nombreFoto;
    int idDrawable;

    public Foto(String nombreFoto, int idDrawable) {
        this.nombreFoto = nombreFoto;
        this.idDrawable = idDrawable;
    }

    public int getIdDrawable() {
        return idDrawable;
    }

    public String getNombreFoto() {
        return nombreFoto;
    }

    public void setIdDrawable(int idDrawable) {
        this.idDrawable = idDrawable;
    }

    public void setNombreFoto(String nombreFoto) {
        this.nombreFoto = nombreFoto;
    }
}
