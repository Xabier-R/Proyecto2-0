package com.aar.app.proyectoLlodio;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
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
    private ObjectAnimator animatorLobo,animatorBocadillo, patadaLobo,patadaLobo2,Xbocadillo,Ybocadillo,Rbocadillo,Xscroll,Yscroll,Rscroll;
    private long animationLoboDuration = 1000;
    private long animationBocadilloDuration = 1500;
    private  TypeWriter tw;
    private AnimatorSet animatorSet5;
    public static ScrollView scrollView;
    int lanzadas=1;


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

        animatorSet5 = new AnimatorSet();
        animatorSet5.addListener(new AnimatorSet.AnimatorListener(){
            @Override
            public void onAnimationStart(Animator animation, boolean isReverse) {

            }

            @Override
            public void onAnimationEnd(Animator animation, boolean isReverse) {
                Intent i = new Intent(Pantalla1.this, Pantalla2.class);
                startActivity(i);
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                mediaPlayer.stop();
                mediaPlayer2.stop();
                finish();
            }

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                //HAY QUE FINALIZAR EL TW!!!!!!!!

                lanzarActividad();

                mediaPlayer.stop();
                mediaPlayer2.stop();
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }


        });

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
                            mediaPlayer2.stop();


                            //Inicio del menu cuando termina el audio
                            if(lanzadas<1) {
                                lanzarActividad();
                            }
                        }
                    });

    }




    public void lanzarActividad()
    {
            Intent i=new Intent(Pantalla1.this,Pantalla2.class);
            startActivity(i);
            overridePendingTransition(R.anim.left_in, R.anim.left_out);
        }



    public void saltar(View view) {
        patadaLobo = ObjectAnimator.ofFloat(lobo, "rotation", 0f,-30f);
        patadaLobo.setDuration(300);
        patadaLobo.setStartDelay(50);

        patadaLobo2 = ObjectAnimator.ofFloat(lobo, "rotation", -30f,50f);
        patadaLobo2.setDuration(200);
        patadaLobo2.setStartDelay(50);

        AnimatorSet animatorSet3= new AnimatorSet();
        animatorSet3.playSequentially(patadaLobo,patadaLobo2);

        Xscroll = ObjectAnimator.ofFloat(scrollView, "translationX", 0f,-800f);
        Xscroll.setDuration(450);
        Xscroll.setStartDelay(50);

        Yscroll = ObjectAnimator.ofFloat(scrollView , "translationY", 0f,-800f);
        Yscroll.setDuration(450);
        Yscroll.setStartDelay(50);

        Rscroll = ObjectAnimator.ofFloat(scrollView, "rotation", 0f,360f);
        Rscroll.setDuration(350);
        Rscroll.setRepeatCount(3);

        Xbocadillo = ObjectAnimator.ofFloat(bocadillo, "translationX", 0f,-800f);
        Xbocadillo.setDuration(450);
        Xbocadillo.setStartDelay(50);

        Ybocadillo = ObjectAnimator.ofFloat(bocadillo , "translationY", 0f,-800f);
        Ybocadillo.setDuration(450);
        Ybocadillo.setStartDelay(50);

        Rbocadillo = ObjectAnimator.ofFloat(bocadillo, "rotation", 0f,360f);
        Rbocadillo.setDuration(350);
        Rbocadillo.setRepeatCount(3);

        AnimatorSet animatorSet4 = new AnimatorSet();
        animatorSet4.playTogether(Xbocadillo,Ybocadillo,Rbocadillo,Xscroll,Yscroll,Rscroll);


        animatorSet5.playSequentially(animatorSet3,animatorSet4);
        animatorSet5.start();
    }
}