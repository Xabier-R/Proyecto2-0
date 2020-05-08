package com.aar.app.proyectoLaudio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.VideoView;
import android.widget.ViewSwitcher;

import com.aar.app.proyectoLaudio.offline.OfflineRegionListActivity;


import java.util.Timer;
import java.util.TimerTask;

/**
 * Actividad que muestra la descripcion del primer punto
 */
public class Actividad1_empezar extends AppCompatActivity {
    private ImageView lobo, destello;
    private ImageSwitcher imageViewImg;
    private ObjectAnimator girar,encogerX,encogerY,destello1,destello2,destello3;
    private LinearLayout dialogoLobo;
    private  TypeWriter tw;
    private AnimatorSet animatorSet,animatorSet2,animatorSet5;
    private MediaPlayer mediaPlayer;

    public static ScrollView scrollView;
    private Button buttonEmpezar;
    private VideoView video;
    private int[] imagenes =new int[]{R.drawable.a1_img1,R.drawable.a1_img2};
    private int posicion;
    private static final int DURACION = 9000;
    private Timer timer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.pantalla_lobo);

        ConstraintLayout ConstraintLayout1 = (ConstraintLayout) findViewById(R.id.ConstraintLayout);
        ConstraintLayout1.setBackground(getResources().getDrawable(R.drawable.fondo));



        lobo = findViewById(R.id.lobo);
        destello = findViewById(R.id.destello);
        tw = (TypeWriter) findViewById(R.id.tv);
        scrollView = findViewById(R.id.scrollView);
        dialogoLobo = findViewById(R.id.dialogoLobo);
        buttonEmpezar = findViewById(R.id.buttonEmpezar);

        video=(VideoView) findViewById(R.id.videoView);
        imageViewImg = findViewById(R.id.ImageView);


        imageViewImg.setFactory(new ViewSwitcher.ViewFactory()
        {
            public View makeView()
            {
                ImageView imageView = new ImageView(Actividad1_empezar.this);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

                return imageView;
            }
        });


        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        imageViewImg.setInAnimation(fadeIn);
        imageViewImg.setOutAnimation(fadeOut);


        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask()
        {
            public void run()
            {
                runOnUiThread(new Runnable()
                {
                    public void run()
                    {
                        imageViewImg.setImageResource(imagenes[posicion]);
                        posicion++;
                        if (posicion == imagenes.length)
                            posicion = 0;
                    }
                });
            }
        }, 0, DURACION);

        //inicia el audio, texto y animacion de hablar
        sicronizarTexto1();
        hablar();

        //listener de la animacion de saltar la explicacion
        //salta la introduccion y carga la actividad
        animatorSet5 = new AnimatorSet();
        animatorSet5.addListener(new AnimatorSet.AnimatorListener(){
            @Override
            public void onAnimationStart(Animator animation, boolean isReverse) {

            }

            @Override
            public void onAnimationEnd(Animator animation, boolean isReverse) {


                Intent intent = new Intent(Actividad1_empezar.this, Puzzle.class);
                startActivity(intent);
                overridePendingTransition(R.anim.left_in, R.anim.left_out);

                mediaPlayer.stop();

            }

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                Intent intent = new Intent(Actividad1_empezar.this, Puzzle.class);
                startActivity(intent);
                overridePendingTransition(R.anim.left_in, R.anim.left_out);


                mediaPlayer.stop();

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }


        });
        //boton para lanzar la actividad
        buttonEmpezar.setOnClickListener(v -> {


            buttonEmpezar.setText("Puzzlea");
            buttonEmpezar.setVisibility(View.INVISIBLE);

            Intent intent = new Intent(Actividad1_empezar.this, Puzzle.class);
            startActivity(intent);


        });

    }

    //metodo que inicia el audio y los textos
    //sincroniza textos con el audio
    public void  sicronizarTexto1() {

        String texto1 =getString(R.string.texto1_a1);

        mediaPlayer = MediaPlayer.create(this, R.raw.audioa_ermuko_andra_mari_eliza);

        mediaPlayer.start();

        tw.setmTypeSpeed(55);
        tw.setText("");
        tw.pause(1500);
        tw.type(texto1).pause(1300)
            .run(new Runnable() {
                @Override
                public void run() {
                    // Finalize the text if user fiddled with it during animation.
                    tw.setText("Jarduera erabilgarria");
                    mediaPlayer.stop();
                    buttonEmpezar.setText("Puzzlea");
                    buttonEmpezar.setVisibility(View.VISIBLE);
                    pestanear();

                }
            });

    }


    //metodo  para iniciar animacion de pestañeo en el lobo
    public void pestanear() {

        lobo.setImageResource(R.drawable.animation_list2);

        AnimationDrawable loboParpadeo = (AnimationDrawable) lobo.getDrawable();
        loboParpadeo.start();
    }


    //metodo para iniciar la animacion de saltar la introduccion
    public void saltar(View view) {

        girar = ObjectAnimator.ofFloat(dialogoLobo, "rotation", 0f,360f);
        girar.setDuration(500);
        girar.setRepeatCount(3);
        encogerX= ObjectAnimator.ofFloat(dialogoLobo,"scaleX",0f);
        encogerX.setDuration(700);
        encogerY= ObjectAnimator.ofFloat(dialogoLobo,"scaleY",0f);
        encogerY.setDuration(700);
        animatorSet=new AnimatorSet();
        animatorSet.playTogether(girar,encogerX,encogerY);


        destello1= ObjectAnimator.ofFloat(destello,"scaleX",6f);
        destello1.setDuration(300);
        destello1.setStartDelay(700);
        destello2= ObjectAnimator.ofFloat(destello,"scaleY",6f);
        destello2.setDuration(300);
        destello2.setStartDelay(700);
        destello3=ObjectAnimator.ofFloat(destello,"alpha",0f);
        destello3.setStartDelay(1000);
        animatorSet2=new AnimatorSet();
        animatorSet2.playTogether(destello1,destello2,destello3);


        animatorSet5.playTogether(girar,encogerX,encogerY,animatorSet2);
        animatorSet5.start();
    }

    //metodo que inicia la animacion de hablar en el lobo
    public void hablar() {

        lobo.setImageResource(R.drawable.animation_list);

        AnimationDrawable loboParpadeo = (AnimationDrawable) lobo.getDrawable();
        loboParpadeo.start();
    }

    //metodo que lanza la pantalla anterior pulsar el boton atras
    public void onBackPressed() {

        Intent i = new Intent(Actividad1_empezar.this, OfflineRegionListActivity.class);
        i.putExtra("actividad", "1");
        startActivity(i);
        mediaPlayer.stop();
        finish();
    }

}



