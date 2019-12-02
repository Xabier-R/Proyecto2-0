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
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.pantalla5);


        // setup reclycerview with adapter
        RecyclerView recyclerView = findViewById(R.id.rv_lista);
        final List<Patrimonio> lista = new ArrayList<>();
        lista.add(new Patrimonio(R.drawable.p5_img1, "Historia- eta arte-ondarea 1", getString(R.string.texto1_p5)));
        lista.add(new Patrimonio(R.drawable.p5_img2, "Historia- eta arte-ondarea 2", getString(R.string.texto2_p5)));
        lista.add(new Patrimonio(R.drawable.p5_img3, "Historia- eta arte-ondarea 3", getString(R.string.texto3_p5)));
        lista.add(new Patrimonio(R.drawable.p5_img4, "Historia- eta arte-ondarea 4", getString(R.string.texto4_p5)));
        lista.add(new Patrimonio(R.drawable.p5_img5, "Historia- eta arte-ondarea 5", getString(R.string.texto5_p5)));
        lista.add(new Patrimonio(R.drawable.p5_img6, "Historia- eta arte-ondarea 6", getString(R.string.texto6_p5)));
        lista.add(new Patrimonio(R.drawable.p5_img7, "Historia- eta arte-ondarea 7", getString(R.string.texto7_p5)));

        final Adaptador adaptador = new Adaptador(this, lista);

        recyclerView.setAdapter(adaptador);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adaptador.setOnItemClickListener(new Adaptador.OnItemClickListener() {
            @Override
            public void onbtnPulsado(int position, ImageView img) {
                imgen = img;
                String nombre = lista.get(position).getNombre().toString();
                empezar(nombre);
            }
        });
    }

    private void empezar(String nombre)
    {
        Intent intent = new Intent(this, Pantalla5_patrimonio_detalle.class);
        intent.putExtra("nombre", nombre);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(Pantalla5.this, Pair.
                <View, String>create(imgen, "imagenPatrimonio"));
        startActivity(intent, options.toBundle());
    }
}
