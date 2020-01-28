package com.aar.app.proyectoLlodio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.aar.app.proyectoLlodio.bbdd.ActividadesSQLiteHelper;
import com.aar.app.proyectoLlodio.offline.OfflineRegionListActivity;

public class Actividad3 extends AppCompatActivity {

    private ImageView lobo,bocadillo;
    private TextView texto;
    private ObjectAnimator animatorLobo,animatorBocadillo,animatorLobo1,animatorLobo2,animatorLoboPausa,animatorLoboV;
    private AnimatorSet animatorSet1,animatorSet2;
    private long animationLoboDuration = 1000;
    private long animationBocadilloDuration = 1500;
    private  TypeWriter tw;
    public static ScrollView scrollView;
    private Button buttonEmpezar;
    private  MediaPlayer mediaPlayer, mediaPlayer2;
    //BBDD
    private ActividadesSQLiteHelper activiades;
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.pantalla_lobo);
        ConstraintLayout ConstraintLayout1 = (ConstraintLayout) findViewById(R.id.ConstraintLayout);
        ConstraintLayout1.setBackground(getResources().getDrawable(R.drawable.a3_img1));
        lobo = findViewById(R.id.lobo);
        bocadillo = findViewById(R.id.bocadillo);
        tw = (TypeWriter) findViewById(R.id.tv);
        scrollView = findViewById(R.id.scrollView);

        buttonEmpezar = findViewById(R.id.buttonEmpezar);

        //Abrimos la base de datos "DBUsuarios" en modo de escritura
        activiades = new ActividadesSQLiteHelper(this, "DBactividades", null, 1);
        db = activiades.getWritableDatabase();

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

        scrollView.fullScroll(View.FOCUS_DOWN);
        tw.setMovementMethod(new ScrollingMovementMethod());

        sicronizarTexto1();
        mediaPlayer2 = MediaPlayer.create(this, R.raw.audioa_kondaira);
        buttonEmpezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String texto =getString(R.string.texto2_a3);

                animatorSet1.cancel();
                long saltoLobo = 50;
                animatorLobo2 = ObjectAnimator.ofFloat(lobo, "y", (lobo.getY()));
                animatorLobo1 = ObjectAnimator.ofFloat(lobo, "y", (lobo.getY()-30f));

                animatorLobo1.setDuration(saltoLobo);
                animatorLobo2.setDuration(saltoLobo);
                AnimatorSet animatorSetY1 = new AnimatorSet();
                AnimatorSet animatorSetY2 = new AnimatorSet();
                animatorSetY1.play(animatorLobo1);
                animatorSetY1.start();
                animatorSetY2.setStartDelay(50);
                animatorSetY2.play(animatorLobo2);
                animatorSetY2.start();

                buttonEmpezar.setText("Kondaira");
                buttonEmpezar.setVisibility(View.INVISIBLE);


                mediaPlayer2.start();
                tw.setmTypeSpeed(66);
                tw.setText("");
                tw.pause(1600);
                tw.type(texto).pause(1800)
                    .run(new Runnable() {
                        @Override
                        public void run() {
                            // Finalize the text if user fiddled with it during animation.
                            mediaPlayer2.stop();

                            //Marco como realizada la actividad 2
                            db.execSQL("UPDATE actividades SET realizada='si' WHERE actividad='actividad3'");
                            db.close();

                            Intent i = new Intent( Actividad3.this, OfflineRegionListActivity.class);
                            i.putExtra("actividad", "4");
                            startActivity(i);



                        }
                    });
            }
        });

        animatorSet1 = new AnimatorSet();
        animatorSet1.addListener(new AnimatorSet.AnimatorListener(){
            @Override
            public void onAnimationStart(Animator animation, boolean isReverse) {

            }

            @Override
            public void onAnimationEnd(Animator animation, boolean isReverse) {
                animacion();
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

        animatorSet1 = new AnimatorSet();
        animatorSet1.addListener(new AnimatorSet.AnimatorListener(){
            @Override
            public void onAnimationStart(Animator animation, boolean isReverse) {

            }

            @Override
            public void onAnimationEnd(Animator animation, boolean isReverse) {
                animacion();
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

        String texto1 =getString(R.string.texto1_a3);

        mediaPlayer = MediaPlayer.create(this, R.raw.audioa_santa_apolonia);

        mediaPlayer.start();

        tw.setmTypeSpeed(60);
        tw.setText("");
        tw.pause(1500);
        tw.type(texto1).pause(1600)
            .run(new Runnable() {
                @Override
                public void run() {
                    // Finalize the text if user fiddled with it during animation.
                    tw.setText("Jarduera erabilgarria");
                    buttonEmpezar.setText("Kondaira");
                    mediaPlayer.stop();
                    buttonEmpezar.setVisibility(View.VISIBLE);
                    animacion();
                }
            });



    }


    public void saltar(View view) {



        //Marco como realizada la actividad 2
        db.execSQL("UPDATE actividades SET realizada='si' WHERE actividad='actividad3'");
        db.close();


        Intent i = new Intent(Actividad3.this, OfflineRegionListActivity.class);
        i.putExtra("actividad", "4");
        startActivity(i);
        mediaPlayer.stop();
        finish();




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

    public void onBackPressed() {

        Intent i = new Intent(Actividad3.this, OfflineRegionListActivity.class);
        i.putExtra("actividad", "3");
        startActivity(i);
        mediaPlayer.stop();
        mediaPlayer2.stop();
        finish();
    }
}
