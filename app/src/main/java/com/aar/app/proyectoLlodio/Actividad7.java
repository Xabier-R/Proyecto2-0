package com.aar.app.proyectoLlodio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
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
import android.widget.MediaController;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.VideoView;
import android.widget.ViewSwitcher;

import com.aar.app.proyectoLlodio.offline.OfflineRegionListActivity;
import com.aar.app.proyectoLlodio.sopaLetras.features.gameplay.GamePlayActivity;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindArray;

public class Actividad7 extends AppCompatActivity {
    private ImageView lobo, destello;
    private ImageSwitcher imageViewImg;
    private ObjectAnimator girar,encogerX,encogerY,destello1,destello2,destello3;
    private LinearLayout dialogoLobo;
    private  TypeWriter tw;
    private AnimatorSet animatorSet,animatorSet2,animatorSet5;
    public static ScrollView scrollView;
    private Button buttonEmpezar;
    private VideoView video;
    @BindArray(R.array.game_round_dimension_values)
    int[] mGameRoundDimVals;
    private int[] imagenes =new int[]{R.drawable.a7_img1,R.drawable.a7_img2, R.drawable.a7_img3};
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
                ImageView imageView = new ImageView(Actividad7.this);
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


        hablar();
        sicronizarTexto1();

        animatorSet5 = new AnimatorSet();
        animatorSet5.addListener(new AnimatorSet.AnimatorListener(){
            @Override
            public void onAnimationStart(Animator animation, boolean isReverse) {

            }

            @Override
            public void onAnimationEnd(Animator animation, boolean isReverse) {
                Intent intent = new Intent(Actividad7.this, GamePlayActivity.class);
                intent.putExtra(GamePlayActivity.EXTRA_ROW_COUNT, 10);
                intent.putExtra(GamePlayActivity.EXTRA_COL_COUNT, 10);
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

                Intent intent = new Intent(Actividad7.this, GamePlayActivity.class);
                intent.putExtra(GamePlayActivity.EXTRA_ROW_COUNT, 10);
                intent.putExtra(GamePlayActivity.EXTRA_COL_COUNT, 10);
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

                buttonEmpezar.setText("Hitz-salda");
                buttonEmpezar.setVisibility(View.INVISIBLE);


                //int dim = mGameRoundDimVals[ spinner.getSelectedItemPosition() ];

                Intent intent = new Intent(Actividad7.this, GamePlayActivity.class);
                intent.putExtra(GamePlayActivity.EXTRA_ROW_COUNT, 10);
                intent.putExtra(GamePlayActivity.EXTRA_COL_COUNT, 10);
                startActivity(intent);
                finish();

            }
        });


    }

    public void  sicronizarTexto1() {

        String texto1 =getString(R.string.texto1_a7);

        mediaPlayer = MediaPlayer.create(this, R.raw.audioa_dolumin_barikua);

        mediaPlayer.start();

        tw.setmTypeSpeed(61);
        tw.setText("");
        tw.pause(1900);
        tw.type(texto1).pause(1300)
            .run(new Runnable() {
                @Override
                public void run() {
                    // Finalize the text if user fiddled with it during animation.

                    mediaPlayer.stop();

                    imageViewImg.setVisibility(View.INVISIBLE);


                    //VIDEO
                    video.setVisibility(View.VISIBLE);
                    String path = "android.resource://" + getPackageName() + "/" + R.raw.bideoa_dolumin_barikua;
                    video.setVideoURI(Uri.parse(path));

                    MediaController mediaController = new MediaController(Actividad7.this);
                    mediaController.setAnchorView(video);

                    video.setMediaController(mediaController);
                    video.start();
                    tw.setText("Jarduera erabilgarria");
                    buttonEmpezar.setText("Letra-zopa");
                    buttonEmpezar.setVisibility(View.VISIBLE);
                    pestanear();
                }
            });



    }

    public void pestanear() {

        lobo.setImageResource(R.drawable.animation_list2);

        AnimationDrawable loboParpadeo = (AnimationDrawable) lobo.getDrawable();
        loboParpadeo.start();
    }



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

    public void hablar() {

        lobo.setImageResource(R.drawable.animation_list);

        AnimationDrawable loboParpadeo = (AnimationDrawable) lobo.getDrawable();
        loboParpadeo.start();
    }

    public void onBackPressed() {

        mediaPlayer.stop();
        Intent i = new Intent(Actividad7.this, OfflineRegionListActivity.class);
        i.putExtra("actividad", "7");
        startActivity(i);
        finish();
    }

}