package com.aar.app.proyectoLlodio;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Actividad5_cuento extends AppCompatActivity {

    private TextView txtTitulo;
    private TextView txtDescripcion;
    private ImageView imgLibro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.actividad5_cuento);

        txtTitulo  = findViewById(R.id.txtTitulo);
        txtDescripcion = findViewById(R.id.textDescripcion);
        imgLibro  = findViewById(R.id.cartaImagen);
        imgLibro.setImageResource(R.drawable.libro526);

        Intent intent = getIntent();
        String titulo = intent.getExtras().getString("titulo");
        String descripcion = intent.getExtras().getString("descripcion");

        txtTitulo.setText(titulo);
        txtDescripcion.setText(descripcion);
    }
}