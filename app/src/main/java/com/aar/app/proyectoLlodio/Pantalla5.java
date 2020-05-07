package com.aar.app.proyectoLlodio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Pantalla5 extends AppCompatActivity {

    public ImageView imgen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla5);


        //set the statue bar background to transparent
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Cargo la lista con los Patrimonios
        RecyclerView recyclerView = findViewById(R.id.rv_lista);
        final List<Patrimonio> lista = new ArrayList<>();
        lista.add(new Patrimonio(R.drawable.p5_img1, "ERMUKO ANDRA MARI ELIZA"));
        lista.add(new Patrimonio(R.drawable.p5_img2, "INDUSKETAK"));
        lista.add(new Patrimonio(R.drawable.p5_img3, "SANTA APOLONIAKO AZTARNA"));
        lista.add(new Patrimonio(R.drawable.p5_img4, "LEZEAGAKO SORGINA"));
        lista.add(new Patrimonio(R.drawable.p5_img5, "ETXEBARRI BASERRIA"));
        lista.add(new Patrimonio(R.drawable.p5_img6, "LAMUZA PARKEA"));
        lista.add(new Patrimonio(R.drawable.p5_img7, "DOLUMIN BARIKUA"));


        //Creo el adaptador para la lista
        final Adaptador adaptador = new Adaptador(this, lista);
        recyclerView.setAdapter(adaptador);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adaptador.setOnItemClickListener(new Adaptador.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
            //Metodo para ver los detalles del Patrimonio pulsado
            @Override
            public void onDeleteClick(int position, ImageView img) {
                imgen = img;
                String nombre = lista.get(position).getNombre().toString();
                empezar(nombre);
            }
        });

    }

    //Metodo que lanza un actividad nueva con los detalles del Patrimonio pulsado
    private void empezar(String nombre)
    {
        Intent intent = new Intent(this, Pantalla5_patrimonio_detalle.class);
        intent.putExtra("nombre", nombre);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(Pantalla5.this, Pair.<View, String>create(imgen, "imagenPatrimonio"));
        startActivity(intent, options.toBundle());
    }
}