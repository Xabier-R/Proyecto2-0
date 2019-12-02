package com.aar.app.proyectoLlodio;

public class Patrimonio {

    int fondo;
    String nombre;
    String texto;

    Patrimonio(int fondo, String nombre, String texto){
        this.fondo = fondo;
        this.nombre = nombre;
        this.texto = texto;
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

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTexto(){
        return texto;
    }


}
