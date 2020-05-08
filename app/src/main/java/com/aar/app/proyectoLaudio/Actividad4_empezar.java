package com.aar.app.proyectoLaudio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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
import android.widget.TextView;
import android.widget.VideoView;
import android.widget.ViewSwitcher;

import com.aar.app.proyectoLaudio.offline.OfflineRegionListActivity;

import java.util.Timer;
import java.util.TimerTask;


public class Actividad4_empezar extends AppCompatActivity {
    private ImageView lobo, destello;
    private ImageSwitcher imageViewImg;
    private ObjectAnimator girar,encogerX,encogerY,destello1,destello2,destello3;
    private LinearLayout dialogoLobo;
    private  TypeWriter tw;
    private AnimatorSet animatorSet,animatorSet2,animatorSet5;
    public static ScrollView scrollView;
    private Button buttonEmpezar;
    private VideoView video;
    private int[] imagenes =new int[]{R.drawable.a4_img2,R.drawable.a4_img1,R.drawable.a4_img3};
    private int posicion;
    private static final int DURACION = 9000;
    private Timer timer = null;
    private MediaPlayer mediaPlayer;

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
        dialogoLobo = findViewById(R.id.dialogoLobo);
        tw = (TypeWriter) findViewById(R.id.tv);
        scrollView = findViewById(R.id.scrollView);

        buttonEmpezar = findViewById(R.id.buttonEmpezar);

        video=(VideoView) findViewById(R.id.videoView);
        imageViewImg = findViewById(R.id.ImageView);


        imageViewImg.setFactory(new ViewSwitcher.ViewFactory()
        {
            public View makeView()
            {
                ImageView imageView = new ImageView(Actividad4_empezar.this);
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
                runOnUiThread(() -> {
                    imageViewImg.setImageResource(imagenes[posicion]);
                    posicion++;
                    if (posicion == imagenes.length)
                        posicion = 0;
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
                Intent intent = new Intent(Actividad4_empezar.this, Actividad4.class);
                startActivity(intent);
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                mediaPlayer.stop();
                finish();
            }

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                Intent intent = new Intent(Actividad4_empezar.this, Actividad4.class);
                startActivity(intent);
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                mediaPlayer.stop();
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

        });


        buttonEmpezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mediaPlayer.stop();
                Intent intent = new Intent(Actividad4_empezar.this, Actividad4.class);
                startActivity(intent);
                finish();

            }
        });


    }

    //metodo que inicia el audio y los textos
    //sincroniza textos con el audio
    public void  sicronizarTexto1() {

        String texto1 =getString(R.string.texto0_a4);

        mediaPlayer = MediaPlayer.create(this, R.raw.audioa_lezeagako_sorgina);

        mediaPlayer.start();

        tw.setmTypeSpeed(58);

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
                    buttonEmpezar.setText("Hitz bete");
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

        mediaPlayer.stop();
        Intent i = new Intent(Actividad4_empezar.this, OfflineRegionListActivity.class);
        i.putExtra("actividad", "4");
        startActivity(i);
        finish();
    }
}