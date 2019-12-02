package com.aar.app.proyectoLlodio.sopaLetras.easyadapter;

import androidx.annotation.NonNull;

import java.util.List;

public class CompositeData <T> {
    public List<T> data;
    public CompositeData(@NonNull List<T> data) {
        this.data = data;
    }
}
