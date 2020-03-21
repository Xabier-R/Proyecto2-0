package com.aar.app.proyectoLlodio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.aar.app.proyectoLlodio.offline.OfflineRegionListActivity;

public class Actividad2_empezar extends AppCompatActivity {

    private ImageView lobo, destello;
    private ImageSwitcher imageViewImg;
    private ObjectAnimator girar,encogerX,encogerY,destello1,destello2,destello3;
    private LinearLayout dialogoLobo;
    private  TypeWriter tw;
    private AnimatorSet animatorSet,animatorSet2,animatorSet5;
    private MediaPlayer mediaPlayer;
    public static ScrollView scrollView;
    private Button buttonEmpezar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.pantalla_lobo);

        ConstraintLayout ConstraintLayout1 = (ConstraintLayout) findViewById(R.id.ConstraintLayout);
        ConstraintLayout1.setBackground(getResources().getDrawable(R.drawable.a2_img1));


        lobo = findViewById(R.id.lobo);
        destello = findViewById(R.id.destello);
        dialogoLobo = findViewById(R.id.dialogoLobo);
        tw = (TypeWriter) findViewById(R.id.tv);
        scrollView = findViewById(R.id.scrollView);

        buttonEmpezar = findViewById(R.id.buttonEmpezar);

        scrollView.fullScroll(View.FOCUS_DOWN);
        tw.setMovementMethod(new ScrollingMovementMethod());

        sicronizarTexto1();
        hablar();
        animatorSet5 = new AnimatorSet();
        animatorSet5.addListener(new AnimatorSet.AnimatorListener(){
            @Override
            public void onAnimationStart(Animator animation, boolean isReverse) {

            }

            @Override
            public void onAnimationEnd(Animator animation, boolean isReverse) {
                Intent intent = new Intent(Actividad2_empezar.this, Actividad2.class);
                startActivityForResult(intent, 2_5);
                overridePendingTransition(R.anim.left_in, R.anim.left_out);

                mediaPlayer.stop();
                finish();
            }

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                Intent intent = new Intent(Actividad2_empezar.this, Actividad2.class);
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

                buttonEmpezar.setVisibility(View.INVISIBLE);

                Intent intent = new Intent(Actividad2_empezar.this, Actividad2.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void  sicronizarTexto1() {

        String texto1 = getString(R.string.texto0_a2);

        mediaPlayer = MediaPlayer.create(this, R.raw.audioa_indusketak);
        mediaPlayer.start();

        tw.setmTypeSpeed(66);
        tw.setText("");
        tw.pause(2500);
        tw.type(texto1).pause(1500)
                .run(new Runnable() {
                    @Override
                    public void run() {
                        // Finalize the text if user fiddled with it during animation.
                        tw.setText("Jarduera erabilgarria");
                        mediaPlayer.stop();
                        buttonEmpezar.setText("Galderak");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 2_5) {
            if(resultCode == Activity.RESULT_OK){

                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();


            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }

    }

    public void onBackPressed() {

        Intent i = new Intent(Actividad2_empezar.this, OfflineRegionListActivity.class);
        i.putExtra("actividad", "2");
        startActivity(i);
        mediaPlayer.stop();
        finish();
    }

}
