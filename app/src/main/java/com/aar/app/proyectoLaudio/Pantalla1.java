package com.aar.app.proyectoLaudio;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;


import androidx.appcompat.app.AppCompatActivity;

import com.aar.app.proyectoLaudio.bbdd.ActividadesSQLiteHelper;
import com.aar.app.proyectoLaudio.offline.OfflineRegionListActivity;
import com.aar.app.proyectoLaudio.traduccion.LocaleHelper;

/**
 * Pantalla inicial
 */
public class Pantalla1 extends AppCompatActivity {
    private ImageView lobo,destello;
    private ObjectAnimator girar,encogerX,encogerY,destello1,destello2,destello3;
    private LinearLayout dialogoLobo;
    private  TypeWriter tw;
    private AnimatorSet animatorSet,animatorSet2,animatorSet5;
    public static ScrollView scrollView;
    private int lanzadas=1;


    //AUDIOS
    private MediaPlayer mediaPlayer;
    private MediaPlayer mediaPlayer2;

    //BBDD
    private ActividadesSQLiteHelper actividadesSQLiteHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.pantalla_lobo);

        //Abrimos la base de datos "DBactividades" en modo de escritura
        actividadesSQLiteHelper = new ActividadesSQLiteHelper(this, "DBactividades", null, 1);
        db = actividadesSQLiteHelper.getWritableDatabase();

        recuperarIdioma();


        lobo = findViewById(R.id.lobo);
        destello = findViewById(R.id.destello);
        tw = (TypeWriter) findViewById(R.id.tv);
        scrollView = findViewById(R.id.scrollView);
        dialogoLobo = findViewById(R.id.dialogoLobo);


        mediaPlayer = MediaPlayer.create(this, R.raw.audio_sarrera);
        mediaPlayer2 = MediaPlayer.create(this, R.raw.audioa_laudio_ezagutu);

        //inicia el audio, texto y la animacion de hablar
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

                mediaPlayer.stop();
                if(lanzadas<=1) {
                    mediaPlayer2.stop();
                    lanzarActividad();
                }



                finish();
                tw.cancelPendingInputEvents();
            }

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                mediaPlayer.stop();
                if(lanzadas<=1) {
                    mediaPlayer2.stop();
                    lanzarActividad();
                }



                finish();
                tw.cancelPendingInputEvents();

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }


        });

    }

    //metodo que recupera de la base de datos el idioma guardado
    private void recuperarIdioma() {
        Cursor c = db.rawQuery("SELECT idioma FROM idiomas", null);
        c.moveToFirst();
        String idioma = c.getString(0);
        LocaleHelper.setLocale(this,idioma);
    }

    //metodo que inicia y sincroniza el texto y audio
    public void  sicronizarTexto1()
    {

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
                        tw.setText("");
                    }
                });

        tw.type(texto2).pause(1300)
                .run(() -> {
                    tw.setText("");
                });
        tw.type(texto3).pause(400)
                .run(() -> {
                    tw.setText("");
                });
        tw.type(texto4).pause(200)
                .run(() -> {
                    tw.setText("");
                });
        tw.type(texto5).pause(800)
                .run(() -> {
                    tw.setText("");
                });
        tw.type(texto6).pause(200)
                .run(() -> {
                    tw.setText("");
                    mediaPlayer.stop();
                    if(lanzadas<=1) {

                        mediaPlayer2.start();
                    }

                });


        tw.pause(2500);
        tw.type(texto7).pause(8000)
                .run(new Runnable() {
                    @Override
                    public void run() {
                        tw.setText("");

                        //Inicio del menu cuando termina el audio
                        if(lanzadas<=1) {

                            // lanzarActividad();
                            animatorSet5.start();
                        }
                    }
                });

    }

    //Metodo para lanzar la actividad una vez termine el audio
    public void lanzarActividad()
    {
        mediaPlayer.stop();
        mediaPlayer2.stop();
        lanzadas++;
        Intent i=new Intent(Pantalla1.this, OfflineRegionListActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
        finish();
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
}