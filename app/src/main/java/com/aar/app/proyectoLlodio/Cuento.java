package com.aar.app.proyectoLlodio;

import android.graphics.drawable.Drawable;

public class Cuento {

    String titulo;
    String descripcion;
    Drawable fotoSacada;

    public Cuento(String titulo, String descripcion)
    {
        this.descripcion = descripcion;
        this.titulo = titulo;
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

    public void setFotoSacada(Drawable fotoSacada) {
        this.fotoSacada = fotoSacada;
    }

    public Drawable getFotoSacada() {
        return fotoSacada;
    }
}
