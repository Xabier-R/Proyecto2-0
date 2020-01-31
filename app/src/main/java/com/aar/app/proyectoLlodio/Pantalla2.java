package com.aar.app.proyectoLlodio;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.aar.app.proyectoLlodio.offline.OfflineRegionDetailActivity;
import com.aar.app.proyectoLlodio.offline.OfflineRegionListActivity;

import java.util.ArrayList;

public class Pantalla2 extends AppCompatActivity{

    ConstraintLayout padrelistener;
    TextView tvmenu;
    ImageView imgmenu;
    private FeatureCoverFlow coverFlow;
    private CoverFlowAdapter adapter;
    private ArrayList<Game> games;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.pantalla2);

        coverFlow = (FeatureCoverFlow) findViewById(R.id.coverflow);
        coverFlow.setBackground(getResources().getDrawable(R.drawable.fondomenu));

        settingDummyData();
        adapter = new CoverFlowAdapter(this, games);
        coverFlow.setAdapter(adapter);
        coverFlow.setOnScrollPositionListener(onScrollListener());



        coverFlow.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {

                if (arg2 == 0) {
                    Intent i = new Intent(Pantalla2.this, Pantalla4.class);
                    startActivity(i);

                } else if (arg2 == 1) {
                    Intent i = new Intent(Pantalla2.this, Pantalla6.class);
                    startActivity(i);

                }else if (arg2 == 2) {
                    Intent i = new Intent(Pantalla2.this, OfflineRegionListActivity.class);
                    startActivity(i);

                }else if (arg2 == 3) {
                    Intent i = new Intent(Pantalla2.this, Pantalla5.class);
                    startActivity(i);
                }
            }
        });
    }


    private FeatureCoverFlow.OnScrollPositionListener onScrollListener() {
        return new FeatureCoverFlow.OnScrollPositionListener() {
            @Override
            public void onScrolledToPosition(int position) {
                Log.v("MainActiivty", "position: " + position);
            }

            @Override
            public void onScrolling() {
                Log.i("MainActivity", "scrolling");
            }
        };
    }

    private void settingDummyData() {
        games = new ArrayList<>();
        games.add(new Game(R.drawable.p2_img4, "Egileak"));
        games.add(new Game(R.drawable.p2_img3, "Galeria"));
        games.add(new Game(R.drawable.p2_img1_2, "Mapa"));
        games.add(new Game(R.drawable.p2_img2, "Ondareak"));
    }



}



