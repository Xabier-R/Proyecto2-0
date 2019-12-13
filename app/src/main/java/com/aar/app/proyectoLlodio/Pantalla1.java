package com.aar.app.proyectoLlodio;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Pantalla1 extends AppCompatActivity {
    private ImageView lobo,bocadillo;
    private TextView texto;
    private ObjectAnimator animatorLobo,animatorBocadillo;
    private long animationLoboDuration = 1000;
    private long animationBocadilloDuration = 1500;
    private  TypeWriter tw;
    public static ScrollView scrollView;

    //AUDIOS
    private MediaPlayer mediaPlayer;
    private MediaPlayer mediaPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.pantalla_lobo);

        lobo = findViewById(R.id.lobo);
        bocadillo = findViewById(R.id.bocadillo);
        tw = (TypeWriter) findViewById(R.id.tv);
        scrollView = findViewById(R.id.scrollView);


        DisplayMetrics metrics = new DisplayMetrics();

        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {

            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            float width = metrics.xdpi;

            /*animatorLobo = ObjectAnimator.ofFloat(lobo, "translationX", width,0);
            animatorLobo.setDuration(animationLoboDuration);
            AnimatorSet animatorSetX = new AnimatorSet();
            animatorSetX.playTogether(animatorLobo);
            animatorSetX.start();

            animatorBocadillo = ObjectAnimator.ofFloat(bocadillo, View.ALPHA,0.0f, 1.0f);
            animatorBocadillo.setDuration(animationBocadilloDuration);
            AnimatorSet animatorSetAlpha = new AnimatorSet();
            animatorSetAlpha.playTogether(animatorBocadillo);
            animatorSetAlpha.start();*/
        } else {

            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int width = metrics.widthPixels;
            animatorLobo = ObjectAnimator.ofFloat(lobo, "translationX", width,0);
            animatorLobo.setDuration(animationLoboDuration);
            AnimatorSet animatorSetX = new AnimatorSet();
            animatorSetX.playTogether(animatorLobo);
            animatorSetX.start();


            animatorBocadillo = ObjectAnimator.ofFloat(bocadillo, View.ALPHA,0.0f, 1.0f);
            animatorBocadillo.setDuration(animationBocadilloDuration);
            AnimatorSet animatorSetAlpha = new AnimatorSet();
            animatorSetAlpha.playTogether(animatorBocadillo);
            animatorSetAlpha.start();
        }

        sicronizarTexto1();



    }

    public void  sicronizarTexto1()
    {
        mediaPlayer = MediaPlayer.create(this, R.raw.audio_sarrera);
        mediaPlayer2 = MediaPlayer.create(this, R.raw.audioa_laudio_ezagutu);
        mediaPlayer.start();


        String texto1 =getString(R.string.texto1_p1);
        String texto2 =getString(R.string.texto2_p1);
        String texto3 =getString(R.string.texto3_p1);
        String texto4 =getString(R.string.texto4_p1);
        String texto5 =getString(R.string.texto5_p1);
        String texto6 =getString(R.string.texto6_p1);
        String texto7 =getString(R.string.texto7_p1);

        tw.setText("");
        tw.pause(1500);
        tw.type(texto1).pause(1300)
                .run(new Runnable() {
                    @Override
                    public void run() {
                        // Finalize the text if user fiddled with it during animation.
                        tw.setText("");
                    }
                });

        tw.type(texto2).pause(1300)
                .run(new Runnable() {
                    @Override
                    public void run() {
                        // Finalize the text if user fiddled with it during animation.
                        tw.setText("");
                    }
                });
        tw.type(texto3).pause(400)
                .run(new Runnable() {
                    @Override
                    public void run() {
                        // Finalize the text if user fiddled with it during animation.
                        tw.setText("");
                    }
                });
        tw.type(texto4).pause(200)

                .run(new Runnable() {
                    @Override
                    public void run() {
                        // Finalize the text if user fiddled with it during animation.
                        tw.setText("");
                    }
                });
        tw.type(texto5).pause(800)
                .run(new Runnable() {
                    @Override
                    public void run() {
                        // Finalize the text if user fiddled with it during animation.
                        tw.setText("");
                    }
                });
        tw.type(texto6).pause(200)
                .run(new Runnable() {
                    @Override
                    public void run() {
                        // Finalize the text if user fiddled with it during animation.
                        tw.setText("");
                        mediaPlayer.stop();
                        mediaPlayer2.start();
                    }
                });


        tw.pause(2500);
        tw.type(texto7).pause(10000)
                .run(new Runnable() {
                    @Override
                    public void run() {
                        // Finalize the text if user fiddled with it during animation.
                        tw.setText("");
                        mediaPlayer2.stop(); /// SE CONFUNDE EN EL AUDIO Y SE ADELANTA EL TEXTO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


                        //Inicio del menu cuando termina el audio
                        Intent i=new Intent(Pantalla1.this,Pantalla2.class);
                        startActivity(i);

                    }
                });

    }

    public void saltar(View view) {
        Intent i = new Intent(Pantalla1.this, Pantalla2.class);
        startActivity(i);
        mediaPlayer.stop();
        mediaPlayer2.stop();
        finish();
    }
}