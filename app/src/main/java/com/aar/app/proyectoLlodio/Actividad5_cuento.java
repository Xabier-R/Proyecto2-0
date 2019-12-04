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
        setContentView(R.layout.actividad5_cuento);

        txtTitulo  = findViewById(R.id.txtTitulo);
        txtDescripcion = findViewById(R.id.textDescripcion);
        imgLibro  = findViewById(R.id.cartaImagen);
        imgLibro.setImageResource(R.drawable.libro526);

        //set the statue bar background to transparent
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        Intent intent = getIntent();
        String titulo = intent.getExtras().getString("titulo");
        String descripcion = intent.getExtras().getString("descripcion");

        txtTitulo.setText(titulo);
        txtDescripcion.setText(descripcion);

    }
}