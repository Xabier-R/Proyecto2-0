package com.aar.app.proyectoLlodio;

import androidx.appcompat.app.AppCompatActivity;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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
import android.widget.TextView;
import android.widget.VideoView;
import android.widget.ViewSwitcher;

import java.util.Timer;
import java.util.TimerTask;


public class Actividad5_empezar extends AppCompatActivity {
    private ImageView lobo, bocadillo;
    private ImageSwitcher imageViewImg;
    private ObjectAnimator animatorLobo,animatorBocadillo,animatorLoboRotation,animatorLobo1,animatorLobo2;
    private long animationLoboDuration = 1000;
    private long animationBocadilloDuration = 1500;
    private  TypeWriter tw;
    public static ScrollView scrollView;
    private Button buttonEmpezar;
    private VideoView video;
    private int[] imagenes =new int[]{R.drawable.a5_img1,R.drawable.a5_img0};
    private int posicion;
    private static final int DURACION = 9000;
    private Timer timer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.actividad5_empezar);

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


        animatorLoboRotation = ObjectAnimator.ofFloat(lobo, "rotation",0f, 360f);
        animatorLoboRotation.setDuration(animationLoboDuration);


        animatorLobo = ObjectAnimator.ofFloat(lobo, "x", 0.0f,(width-400));
        animatorLobo.setDuration(animationLoboDuration);
        AnimatorSet animatorSetX = new AnimatorSet();
        animatorSetX.playTogether(animatorLoboRotation, animatorLobo);
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
                ImageView imageView = new ImageView(Actividad5_empezar.this);
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

                long saltoLobo = 50;
                animatorLobo1 = ObjectAnimator.ofFloat(lobo, "y", (lobo.getY()-30f));
                animatorLobo2 = ObjectAnimator.ofFloat(lobo, "y", (lobo.getY()));

                animatorLobo1.setDuration(saltoLobo);
                animatorLobo2.setDuration(saltoLobo);
                AnimatorSet animatorSetY1 = new AnimatorSet();
                AnimatorSet animatorSetY2 = new AnimatorSet();
                animatorSetY1.play(animatorLobo1);
                animatorSetY1.start();
                animatorSetY2.setStartDelay(50);
                animatorSetY2.play(animatorLobo2);
                animatorSetY2.start();

                buttonEmpezar.setVisibility(View.INVISIBLE);



                Intent intent = new Intent(Actividad5_empezar.this, Actividad5.class);
                startActivity(intent);
                finish();

            }
        });


    }

    public void  sicronizarTexto1() {

        String texto1 =getString(R.string.texto1_a5);

        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.audioa_etxebarri_baserria);

        mediaPlayer.start();

        tw.setmTypeSpeed(60);

        tw.setText("");
        tw.pause(1500);
        tw.type(texto1).pause(1300)
                .run(new Runnable() {
                    @Override
                    public void run() {
                        // Finalize the text if user fiddled with it during animation.
                        tw.setText("Jarduera erabilgarria");
                        mediaPlayer.stop();
                        buttonEmpezar.setVisibility(View.VISIBLE);

                    }
                });

    }

    public void lanzarActividad(View view) {
        Intent intent = new Intent(Actividad5_empezar.this, Actividad5.class);
        startActivity(intent);
        finish();
    }


}