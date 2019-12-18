package com.aar.app.proyectoLlodio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
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
import android.widget.MediaController;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.VideoView;
import android.widget.ViewSwitcher;

import com.aar.app.proyectoLlodio.sopaLetras.features.gameplay.GamePlayActivity;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindArray;

public class Actividad7 extends AppCompatActivity {
    private ImageView lobo, bocadillo;
    private ImageSwitcher imageViewImg;
    private ObjectAnimator animatorLobo,animatorBocadillo,animatorLobo1,animatorLobo2,patadaLobo,
            patadaLobo2,animatorLoboPausa,animatorLoboV,Xbocadillo,Ybocadillo,Rbocadillo,Xscroll,Yscroll,Rscroll;
    private AnimatorSet animatorSet5;
    private AnimatorSet animatorSet1,animatorSet2;
    private long animationLoboDuration = 1000;
    private long animationBocadilloDuration = 1500;
    private long animationImagesDuration = 3000;
    private  TypeWriter tw;
    public static ScrollView scrollView;
    private Button buttonEmpezar;
    private VideoView video;
    //private Spinner spinner;
    @BindArray(R.array.game_round_dimension_values)
    int[] mGameRoundDimVals;
    //private TextView textView;
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

        tw.setmTypeSpeed(55);
        tw.setText("");
        tw.pause(1500);
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
                    buttonEmpezar.setText("Hitz Saldia");
                    buttonEmpezar.setVisibility(View.VISIBLE);
                    animacion();
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