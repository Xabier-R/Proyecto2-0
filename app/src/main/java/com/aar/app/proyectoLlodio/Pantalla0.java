package com.aar.app.proyectoLlodio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Pantalla inicial de la aplicacion
 */
public class Pantalla0 extends AppCompatActivity {

    Timer timer;
    LinearLayout l1,l2;
    Animation uptodown,downtoup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.pantalla0);

        //Vinculo los LinearLayouts
        l1 = findViewById(R.id.l1);
        l2 = findViewById(R.id.l2);

        //Animacion de los LinearLayouts
        uptodown = AnimationUtils.loadAnimation(this,R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup);

        l1.setAnimation(uptodown);
        l2.setAnimation(downtoup);

        //Despues de 4 segundos lanza la Pantalla2
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(Pantalla0.this, Pantalla2.class);
                startActivity(intent);
                finish();
            }
        },4000);




    }



}
