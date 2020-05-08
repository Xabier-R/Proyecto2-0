package com.aar.app.proyectoLlodio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Pantalla6_fotos extends AppCompatActivity{

    GridView gridView;
    private int[] arimg;
    private ArrayList<Foto> fotos;
    private CustomAdapter customFotos;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.pantalla6_fotos);

        // Vinculo el gridView para las fdtoso
        gridView = findViewById(R.id.grid);

        Bundle datos = this.getIntent().getExtras();
        arimg = datos.getIntArray("imagenesCarrousel");
        fotos = new ArrayList<Foto>();
        for (int i=0; i<arimg.length; i++) {
            fotos.add(new Foto("foto" + i, arimg[i]));
        }

        customFotos = new CustomAdapter();
        gridView.setAdapter(customFotos);

        //Listener que obtiene la foto del gridView pulsada para verla en tamaño completo
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), Pantalla6_carrousel.class);
                intent.putExtra("imagenesCarrousel",arimg);
                intent.putExtra("posicionFoto", i);
                startActivity(intent);
            }
        });





    }

    private class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return arimg.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = getLayoutInflater().inflate(R.layout.item_foto,null);
            //getting view in row_data
            ImageView image = view1.findViewById(R.id.imgLugar);

            image.setImageResource(arimg[i]);
            return view1;



        }
    }
}
