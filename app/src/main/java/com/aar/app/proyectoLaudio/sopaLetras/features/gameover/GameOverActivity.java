package com.aar.app.proyectoLaudio.sopaLetras.features.gameover;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.aar.app.proyectoLaudio.Actividad4_empezar;
import com.aar.app.proyectoLaudio.Pantalla2;
import com.aar.app.proyectoLaudio.Pantalla6;
import com.aar.app.proyectoLaudio.R;
import com.aar.app.proyectoLaudio.offline.OfflineRegionListActivity;

import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game_over);

        final KonfettiView konfettiView = findViewById(R.id.konfettiView);

        //Float taman = getWindow().getDecorView().getWidth() +0f;

        DisplayMetrics metrics = new DisplayMetrics();

        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {

            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            float width = metrics.xdpi;

            confeti(konfettiView, width);

        }




    }

    public void finalizar(View view)
    {
        Intent i = new Intent(GameOverActivity.this, Pantalla2.class);
        startActivity(i);
        finish();


    }



    public void confeti(KonfettiView konfettiView, Float taman)
    {

        konfettiView.build()
            .addColors(Color.YELLOW,Color.WHITE)
            .setDirection(0.0, 359.0)
            .setSpeed(1f, 3f)
            .setFadeOutEnabled(true)
            .setTimeToLive(16000L)
            .addShapes(Shape.RECT, Shape.CIRCLE)
            .addSizes(new Size(12, 5f))
            .setPosition(0f, taman*3, -50f, -50f)
            .streamFor(300, 13000L);

    }


    //metodo que lanza el menu al pulsar el boton atras
    public void onBackPressed() {


        Intent i = new Intent(GameOverActivity.this, Pantalla2.class);
        i.putExtra("actividad", "4");
        startActivity(i);
        finish();
    }


}
