package com.aar.app.proyectoLaudio;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Pantalla5_patrimonio_detalle extends AppCompatActivity {

    private ImageView img;
    private TextView txtDescripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.pantalla5_patrimonio_detalle);
        img = findViewById(R.id.cartaImagen);
        txtDescripcion = findViewById(R.id.txtPatrimonio);

        Intent intent = getIntent();
        String nombre = intent.getExtras().getString("nombre");

        cambiar(nombre);
    }

    //Metodo que cambia los detalles de la actividad con los datos del Patrimonio pulsado
    private void cambiar(String nombre)
    {
        switch (nombre)
        {
            case "ERMUKO ANDRA MARI ELIZA":
                img.setImageResource(R.drawable.p5_img1);
                txtDescripcion.setText(getString(R.string.texto1_p5));
                break;
            case "INDUSKETAK":
                img.setImageResource(R.drawable.p5_img2);
                txtDescripcion.setText(getString(R.string.texto2_p5));
                break;
            case "SANTA APOLONIAKO AZTARNA":
                img.setImageResource(R.drawable.p5_img3);
                txtDescripcion.setText(getString(R.string.texto3_p5));
                break;
            case "LEZEAGAKO SORGINA":
                img.setImageResource(R.drawable.p5_img4);
                txtDescripcion.setText(getString(R.string.texto4_p5));
                break;
            case "ETXEBARRI BASERRIA":
                img.setImageResource(R.drawable.p5_img5);
                txtDescripcion.setText(getString(R.string.texto5_p5));
                break;
            case "LAMUZA PARKEA":
                img.setImageResource(R.drawable.p5_img6);
                txtDescripcion.setText(getString(R.string.texto6_p5));
                break;
            case "DOLUMIN BARIKUA":
                img.setImageResource(R.drawable.p5_img7);
                txtDescripcion.setText(getString(R.string.texto1_a7));
                break;
        }
    }
}