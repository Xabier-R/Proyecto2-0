package com.aar.app.proyectoLlodio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
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
import android.widget.ScrollView;
import android.widget.VideoView;
import android.widget.ViewSwitcher;

import java.util.Timer;
import java.util.TimerTask;


public class Actividad1_empezar extends AppCompatActivity {
    private ImageView lobo, bocadillo;
    private ImageSwitcher imageViewImg;
    private ObjectAnimator animatorLobo,animatorBocadillo,animatorLobo1,animatorLobo2,animatorLoboPausa,animatorLoboV;
    private AnimatorSet animatorSet1,animatorSet2;
    private long animationLoboDuration = 1000;
    private long animationBocadilloDuration = 1500;
    private  TypeWriter tw;

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
        bocadillo = findViewById(R.id.bocadillo);
        tw = (TypeWriter) findViewById(R.id.tv);
        scrollView = findViewById(R.id.scrollView);

        buttonEmpezar = findViewById(R.id.buttonEmpezar);

        video=(VideoView) findViewById(R.id.videoView);
        imageViewImg = findViewById(R.id.ImageView);

        DisplayMetrics metrics = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;


        getWindowManager().getDefaultDisplay().getMetrics(metrics);
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




        sicronizarTexto1();

        buttonEmpezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                buttonEmpezar.setText("Puzzlea");
                buttonEmpezar.setVisibility(View.INVISIBLE);

                Intent intent = new Intent(Actividad1_empezar.this, Puzzle.class);
                startActivity(intent);
                finish();

            }
        });
        animatorSet1 = new AnimatorSet();
        animatorSet1.addListener(new AnimatorSet.AnimatorListener(){
            @Override
            public void onAnimationStart(Animator animation, boolean isReverse) {

            }

            @Override
            public void onAnimationEnd(Animator animation, boolean isReverse) {

            }

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animacion();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }


        });
    }

    public void  sicronizarTexto1() {

        String texto1 =getString(R.string.texto1_a1);

        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.audioa_ermuko_andra_mari_eliza);

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
                        animacion();

                    }
                });

    }



    public void animacion()
    {

        int saltoLobo =100;
        animatorLobo1 = ObjectAnimator.ofFloat(lobo, "y", (lobo.getY()-450f));
        animatorLobo1.setDuration(saltoLobo);


        animatorLobo2 = ObjectAnimator.ofFloat(lobo, "y", (lobo.getY()));
        animatorLobo2.setDuration(saltoLobo);
        animatorLobo2.setStartDelay(50);


        animatorLoboV = ObjectAnimator.ofFloat(lobo, "rotation", 0f,360f);
        animatorLoboV.setDuration(300);
        animatorLoboV.setStartDelay(50);

        animatorLoboPausa = ObjectAnimator.ofFloat(lobo, "x", (lobo.getX()));
        animatorLoboPausa.setStartDelay(100);
        animatorLoboPausa.setDuration(2500);

        animatorSet2=new AnimatorSet();

        animatorSet2.playTogether(animatorLobo1,animatorLoboV);


        animatorSet1.setStartDelay(200);
        animatorSet1.playSequentially(animatorSet2,animatorLobo2,animatorLoboPausa);
        animatorSet1.start();



    }




    public void saltar(View view) {
        Intent intent = new Intent(Actividad1_empezar.this, Puzzle.class);
        startActivity(intent);
        finish();
    }



}