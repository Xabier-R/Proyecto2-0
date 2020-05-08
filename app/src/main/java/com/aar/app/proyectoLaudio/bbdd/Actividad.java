package com.aar.app.proyectoLaudio.bbdd;


public class Actividad {

    public String nombre;
    public String realizada;

    public Actividad(String nombre, String realizada) {
        this.nombre = nombre;
        this.realizada = realizada;
    }

    public String getNombre() {
        return nombre;
    }

    public String isRealizada() {
        return realizada;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setRealizada(String realizada) {
        this.realizada = realizada;
    }
}
