package com.aar.app.proyectoLlodio;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Pantalla6 extends AppCompatActivity {

    AdaptadorCarpetas adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.pantalla6);

        // setup reclycerview with adapter
        RecyclerView recyclerView = findViewById(R.id.rv_lista_carpeta);
        final List<Carpeta> lista = new ArrayList<>();
        lista.add(new Carpeta("ERMUKO ANDRA MARI ELIZA"));
        lista.add(new Carpeta("INDUSKETAK"));
        lista.add(new Carpeta("SANTA APOLONIAKO AZTARNA"));
        lista.add(new Carpeta("LEZEAGAKO SORGINA"));
        lista.add(new Carpeta("ETXEBARRI BASERRIA"));
        lista.add(new Carpeta("LAMUZA PARKEA"));
        lista.add(new Carpeta("DOLUMIN BARIKUA"));

        adaptador = new AdaptadorCarpetas(this, lista);

        recyclerView.setAdapter(adaptador);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adaptador.setOnItemClickListener(new AdaptadorCarpetas.OnItemClickListener() {

            @Override
            public void onCarpetaPulsada(int position) {
                controlCarpetaPulsada(position);
            }
        });
    }

    private void controlCarpetaPulsada(int position)
    {

        String nombreCarpeta = adaptador.mdata.get(position).getLugar();
        switch(nombreCarpeta) {
            case "ERMUKO ANDRA MARI ELIZA":
                Intent intent = new Intent(Pantalla6.this, Pantalla6_fotos.class);
                int[] imagenes = {R.drawable.a1_img1, R.drawable.a1_img2, R.drawable.a1_img3};
                intent.putExtra("imagenesCarrousel", imagenes);
                startActivity(intent);
                break;
            case "INDUSKETAK":
                Intent intent1 = new Intent(Pantalla6.this, Pantalla6_fotos.class);
                int[] imagenes1 = {R.drawable.a2_img1};
                intent1.putExtra("imagenesCarrousel", imagenes1);
                startActivity(intent1);
                break;
            case "SANTA APOLONIAKO AZTARNA":
                Intent intent3 = new Intent(Pantalla6.this, Pantalla6_fotos.class);
                int[] imagenes3 = {R.drawable.a3_img1, R.drawable.a3_img2, R.drawable.a3_img3, R.drawable.a3_img4};
                intent3.putExtra("imagenesCarrousel", imagenes3);
                startActivity(intent3);
                break;
            case "LEZEAGAKO SORGINA":
                Intent intent4 = new Intent(Pantalla6.this, Pantalla6_fotos.class);
                int[] imagenes4 = {R.drawable.a4_img1, R.drawable.a4_img2, R.drawable.a4_img3};
                intent4.putExtra("imagenesCarrousel", imagenes4);
                startActivity(intent4);
                break;
            case "ETXEBARRI BASERRIA":
                Intent intent5 = new Intent(Pantalla6.this, Pantalla6_fotos.class);
                int[] imagenes5 = {R.drawable.a5_img0, R.drawable.a5_img1};
                intent5.putExtra("imagenesCarrousel", imagenes5);
                startActivity(intent5);
                break;
            case "LAMUZA PARKEA":
                Intent intent6 = new Intent(Pantalla6.this, Pantalla6_fotos.class);
                int[] imagenes6 = {R.drawable.a6_img1, R.drawable.a6_img2, R.drawable.a6_img3, R.drawable.a6_img4, R.drawable.a6_img5};
                intent6.putExtra("imagenesCarrousel", imagenes6);
                startActivity(intent6);
                break;
            case "DOLUMIN BARIKUA":
                Intent intent7 = new Intent(Pantalla6.this, Pantalla6_fotos.class);
                int[] imagenes7 = {R.drawable.a7_img1, R.drawable.a7_img2, R.drawable.a7_img3};
                intent7.putExtra("imagenesCarrousel", imagenes7);
                startActivity(intent7);
                break;
            default:
                // code block

        }
    }
}
