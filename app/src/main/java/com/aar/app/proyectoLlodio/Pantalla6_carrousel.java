package com.aar.app.proyectoLlodio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;


public class Pantalla6_carrousel extends AppCompatActivity {

    CarouselView carouselView;
    private int[] arimg;
    private int posicionFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.pantalla6_carrousel);

        // PREPARAR CAROUSEL
        Bundle datos = this.getIntent().getExtras();
        arimg = datos.getIntArray("imagenesCarrousel");
        posicionFoto = datos.getInt("posicionFoto");


        carouselView = findViewById(R.id.carousel);
        carouselView.setPageCount(arimg.length);
        carouselView.setCurrentItem(posicionFoto);
        carouselView.setImageListener(carouselImageListener());

    }

    // METODOS DEL CAROUSEL
    private ImageListener carouselImageListener(){
        ImageListener esteMetodo = new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(arimg[position]);
            }
        };
        return esteMetodo;
    }


}
