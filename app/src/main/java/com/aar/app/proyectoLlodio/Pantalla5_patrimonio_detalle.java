package com.aar.app.proyectoLlodio;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Pantalla5_patrimonio_detalle extends AppCompatActivity {

    ImageView img;
    TextView txtDescripcion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.pantalla5_patrimonio_detalle);
        img = findViewById(R.id.cartaImagen);
        txtDescripcion = findViewById(R.id.textDescripcion);

        Intent intent = getIntent();
        String nombre = intent.getExtras().getString("nombre");

        cambiar(nombre);
    }

    private void cambiar(String nombre)
    {
        switch (nombre)
        {
            case "Actividad 1":
                img.setImageResource(R.drawable.p5_img1);
                txtDescripcion.setText(getString(R.string.texto1_p5));
                break;
            case "Actividad 2":
                img.setImageResource(R.drawable.p5_img2);
                txtDescripcion.setText(getString(R.string.texto2_p5));
                break;
            case "Actividad 3":
                img.setImageResource(R.drawable.p5_img3);
                txtDescripcion.setText(getString(R.string.texto3_p5));
                break;
            case "Actividad 4":
                img.setImageResource(R.drawable.p5_img4);
                txtDescripcion.setText(getString(R.string.texto4_p5));
                break;
            case "Actividad 5":
                img.setImageResource(R.drawable.p5_img5);
                txtDescripcion.setText(getString(R.string.texto5_p5));
                break;
            case "Actividad 6":
                img.setImageResource(R.drawable.p5_img6);
                txtDescripcion.setText(getString(R.string.texto6_p5));
                break;
            case "Actividad 7":
                img.setImageResource(R.drawable.p5_img7);
                txtDescripcion.setText(getString(R.string.texto7_p5));
                break;
        }
    }
}
