package com.aar.app.proyectoLlodio;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import org.jetbrains.annotations.NotNull;

public class Pantalla4 extends AppCompatActivity {
    private RelativeLayout Des;
    static boolean desarrollador;
    int cont=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla4);

        //set the statue bar background to transparent
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        Des = findViewById(R.id.Des);

        Des.setOnClickListener(v -> {
            cont++;
            contador();
        });


    }

    public boolean isDesarrollador() {
        return desarrollador;
    }


    public void contador()
    {
        if((cont==6)&&(desarrollador==false))
        {
            Toast.makeText(getApplicationContext(), "Modo desarrollador activado", Toast.LENGTH_SHORT).show();

            cont=0;
            desarrollador=true;

        }
        if((cont==6)&&(desarrollador==true))
        {
            Toast.makeText(getApplicationContext(), "Modo desarrollador desactivado", Toast.LENGTH_SHORT).show();

            cont=0;
            desarrollador=false;
        }


    }

}
