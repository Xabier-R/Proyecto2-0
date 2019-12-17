package com.aar.app.proyectoLlodio;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Pantalla2 extends AppCompatActivity implements SwipeInterface{

    ConstraintLayout padrelistener;
    LinearLayout linearpadre;
    TextView tvmenu;
    ImageView imgmenu;
    ObjectAnimator animatorRotation;
    long animationDuration = 1000;
    int contpantalla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.pantalla2);

        padrelistener = findViewById(R.id.padrelistener);
        linearpadre = findViewById(R.id.lineapadre);
        imgmenu = findViewById(R.id.imagemenu);
        tvmenu = findViewById(R.id.tvmenu);
        contpantalla=1;

        tvmenu.setTextColor(getResources().getColor(R.color.blanco));
        controlEventos();
    }

    private void controlEventos(){
        ActivitySwipeDetector activitySwipeDetector = new ActivitySwipeDetector(this);
        padrelistener.setOnTouchListener(activitySwipeDetector);
        imgmenu.setOnClickListener(lanzarfrommenu());
    }

    public View.OnClickListener lanzarfrommenu(){
        View.OnClickListener esteMetodo = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  cadena = tvmenu.getText().toString();
                String [] strar = cadena.split("/");
                Intent i;
                switch (strar[0]){
                    case "1":
                        i = new Intent(Pantalla2.this, Pantalla3.class);
                        startActivity(i);
                        finish();
                        break;
                    case "2":
                        i = new Intent(Pantalla2.this, Pantalla5.class);
                        startActivity(i);
                        break;
                    case "3":
                        //i = new Intent(Pantalla2.this, pantalla6.class);
                        //startActivity(i);
                        break;
                    case "4":
                        i = new Intent(Pantalla2.this, Pantalla4.class);
                        startActivity(i);
                        break;
                }
            }
        };
        return esteMetodo;
    }

    @Override
    public void top2bottom(View v) {}
    @Override
    public void bottom2top(View v) {}
    @Override
    public void left2right(View v) {
        flipAnimation2();
        contpantalla--;
        if(contpantalla<1){
            contpantalla=4;
        }
        cambiarpantalla();
    }
    @Override
    public void right2left(View v) {
        flipAnimation();
        contpantalla++;
        if(contpantalla>4){
            contpantalla=1;
        }
        cambiarpantalla();
    }

    public void flipAnimation(){
        animatorRotation = ObjectAnimator.ofFloat(imgmenu, "rotationY",360f, 0f);
        animatorRotation.setDuration(animationDuration);
        AnimatorSet animatorSetRotator = new AnimatorSet();
        animatorSetRotator.playTogether(animatorRotation);
        animatorSetRotator.start();
    }


    public void flipAnimation2(){
        animatorRotation = ObjectAnimator.ofFloat(imgmenu, "rotationY", 0f,360f);
        animatorRotation.setDuration(animationDuration);
        AnimatorSet animatorSetRotator = new AnimatorSet();
        animatorSetRotator.playTogether(animatorRotation);
        animatorSetRotator.start();
    }

    public void cambiarpantalla(){
        switch (contpantalla){
            case 1:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imgmenu.setImageResource(R.drawable.p2_img1);
                                tvmenu.setText("1/4");
                            }
                        });
                    }
                }).start();
                break;
            case 2:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imgmenu.setImageResource(R.drawable.p2_img2);
                                tvmenu.setText("2/4");
                            }
                        });
                    }
                }).start();
                break;
            case 3:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                imgmenu.setImageResource(R.drawable.p2_img3);
                                tvmenu.setText("3/4");
                            }
                        });
                    }
                }).start();
                break;
            case 4:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imgmenu.setImageResource(R.drawable.p2_img4);
                                tvmenu.setText("4/4");
                            }
                        });
                    }
                }).start();
                break;
        }
    }
}

