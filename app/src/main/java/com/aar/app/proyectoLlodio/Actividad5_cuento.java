package com.aar.app.proyectoLlodio;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.function.BiPredicate;

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

        //set the statue bar background to transparent
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        Intent intent = getIntent();
        String titulo = intent.getExtras().getString("titulo");
        String descripcion = intent.getExtras().getString("descripcion");
        String imagenCodificada = intent.getExtras().getString("fotocodificada");
        Bitmap bitmap = decodeBase64(imagenCodificada);


        imgLibro.setImageBitmap(bitmap);
        txtTitulo.setText(titulo);
        txtDescripcion.setText(descripcion);

    }

    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input,0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
}
