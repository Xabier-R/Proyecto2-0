package com.aar.app.proyectoLlodio;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
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

import com.aar.app.proyectoLlodio.bbdd.ActividadesSQLiteHelper;
import com.aar.app.proyectoLlodio.offline.OfflineRegionDetailActivity;
import com.aar.app.proyectoLlodio.offline.OfflineRegionListActivity;
import com.aar.app.proyectoLlodio.traduccion.LocaleHelper;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;

import java.util.ArrayList;

/**
 * Menu de la aplicacion
 */
public class Pantalla2 extends AppCompatActivity{

    ConstraintLayout padrelistener;
    TextView tvmenu;
    ImageView imgmenu;
    private FeatureCoverFlow coverFlow;
    private CoverFlowAdapter adapter;

    private ArrayList<Game> games;


    //BBDD
    private ActividadesSQLiteHelper activiades;
    private SQLiteDatabase db1;
    private BoomMenuButton bmb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.pantalla2);

        bmb = findViewById(R.id.bmb);

        HamButton.Builder bmbSpanish = new HamButton.Builder()
                .normalText("Español")
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        cambiarIdioma("es");
                    }
                });

        HamButton.Builder bmbEuskera = new HamButton.Builder()
                .normalText("Euskera")
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        cambiarIdioma("eu");
                    }
                });


        bmb.addBuilder(bmbSpanish);
        bmb.addBuilder(bmbEuskera);


        coverFlow = (FeatureCoverFlow) findViewById(R.id.coverflow);
        coverFlow.setBackground(getResources().getDrawable(R.drawable.fondomenu));

        settingDummyData();
        adapter = new CoverFlowAdapter(this, games);
        coverFlow.setAdapter(adapter);
        coverFlow.setOnScrollPositionListener(onScrollListener());

        //Abrimos la base de datos "DBactividades" en modo de escritura
        activiades = new ActividadesSQLiteHelper(this, "DBactividades", null, 1);
        db1 = activiades.getWritableDatabase();






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
                    Intent i = new Intent(Pantalla2.this, Pantalla1.class);
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

    //metodo para crear los iconos del menu
    private void settingDummyData() {
        games = new ArrayList<>();

        String item1 = getResources().getString(R.string.texto1_p2);
        String item2 = getResources().getString(R.string.texto2_p2);
        String item3 = getResources().getString(R.string.texto3_p2);
        String item4 = getResources().getString(R.string.texto4_p2);

        games.add(new Game(R.drawable.p2_img4, item1));
        games.add(new Game(R.drawable.p2_img3, item2));
        games.add(new Game(R.drawable.p2_img1_2, item3));
        games.add(new Game(R.drawable.p2_img2, item4));
    }

    // metodo para cambiar el idioma de la aplicacion
    private void cambiarIdioma(String idioma) {
        if (idioma.equals("es")) {
            db1.execSQL("UPDATE idiomas SET idioma='es'");
            LocaleHelper.setLocale(this,"es");
        }
        else {
            db1.execSQL("UPDATE idiomas SET idioma='eu'");
            LocaleHelper.setLocale(this,"eu");
        }
        finish();
        Intent i = new Intent(this, Pantalla2.class);
        startActivity(i);
    }





}



