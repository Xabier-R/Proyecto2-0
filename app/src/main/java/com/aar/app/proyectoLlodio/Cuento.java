package com.aar.app.proyectoLlodio;

import android.graphics.drawable.Drawable;

public class Cuento {

    String titulo;
    String descripcion;
    String fotoSacada;

    public Cuento(String titulo, String descripcion, String fotoSacada)
    {
        this.descripcion = descripcion;
        this.titulo = titulo;
        this.fotoSacada = fotoSacada;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setFotoSacada(String fotoSacada) {
        this.fotoSacada = fotoSacada;
    }

    public String getFotoSacada() {
        return fotoSacada;
    }
}
