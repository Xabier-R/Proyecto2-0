package com.aar.app.proyectoLlodio.sopaLetras.features.gameover;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.aar.app.proyectoLlodio.R;
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





    public void confeti(KonfettiView konfettiView, Float taman)
    {

        Toast.makeText(this, taman.toString(), Toast.LENGTH_SHORT).show();
        konfettiView.build()
            .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA,Color.BLUE,Color.RED)
            .setDirection(0.0, 359.0)
            .setSpeed(1f, 4f)
            .setFadeOutEnabled(true)
            .setTimeToLive(20000L)
            .addShapes(Shape.RECT, Shape.CIRCLE)
            .addSizes(new Size(12, 5f))
            .setPosition(0f, taman*3, -50f, -50f)
            .streamFor(300, 15000L);
    }





}
