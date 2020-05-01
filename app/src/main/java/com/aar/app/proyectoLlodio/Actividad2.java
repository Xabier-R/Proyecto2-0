package com.aar.app.proyectoLlodio;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.aar.app.proyectoLlodio.bbdd.ActividadesSQLiteHelper;
import com.aar.app.proyectoLlodio.offline.OfflineRegionListActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Actividad2 extends AppCompatActivity implements Fragmento_ayuda.OnFragmentInteractionListener {

    private HashMap<String, ArrayList> preguntas;
    private ArrayList<Integer> respuestas;
    private Integer numPregunta = 0;
    private Button btnNext;
    private LinearLayout linearAciertos;
    private TextView txtAcierto;



    private HashMap<Integer, String> mapaPreguntas;
    private HashMap<Integer, Integer> mapaRespuestas;
    private TextView txtPregunta;
    private RadioButton btnResp1;
    private RadioButton btnResp2;
    private int aciertos;
    private LinearLayout ayuda;
    private boolean isUp=true;


    //BBDD
    private ActividadesSQLiteHelper activiades;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.actividad2);


        //Abrimos la base de datos "DBUsuarios" en modo de escritura
        activiades = new ActividadesSQLiteHelper(this, "DBactividades", null, 1);
        db = activiades.getWritableDatabase();



        Fragment fragment = new Fragmento_ayuda(getString(R.string.ayuda2));
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.linearFragmento, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();

        ayuda = findViewById(R.id.linearFragmento);
        txtPregunta = findViewById(R.id.txtPregunta);
        btnResp1 = findViewById(R.id.btnRespuesta1);
        btnResp2 = findViewById(R.id.btnRespuesta2);
        btnNext = findViewById(R.id.btnnext);
        linearAciertos = findViewById(R.id.layourAciertos);
        txtAcierto = findViewById(R.id.txtAciertos);

        cargarPreguntas();
        siguientePregunta();


    }

    public void siguiente(View view) {

        verRespuestaElegida();

        if (btnNext.getText().equals("BUKATUTA"))
        {


            //Marco como realizada la actividad 2
            db.execSQL("UPDATE actividades SET realizada='si' WHERE actividad='actividad2'");
            db.close();

            Intent i = new Intent( Actividad2.this, OfflineRegionListActivity.class);
            i.putExtra("actividad", "3");
            startActivity(i);
            finish();
        }





        else {
            if (btnNext.getText().equals("egiaztatu") || numPregunta ==3) {
                linearAciertos.setVisibility(View.VISIBLE);
                txtAcierto.setText("Asmatu duzu   " + aciertos + "/4");
                btnNext.setText("BUKATUTA");

            }
            else {
                numPregunta++;
                if (numPregunta == preguntas.size()-1)
                    btnNext.setText("egiaztatu");
                siguientePregunta();

            }
        }
    }

    private void cargarPreguntas()
    {
        preguntas = new HashMap<String, ArrayList>();
        mapaPreguntas = new HashMap<Integer, String>();
        mapaRespuestas = new HashMap<Integer, Integer>();
        preguntas.put(getString(R.string.texto1_a2), new ArrayList<String>(Arrays.asList(getString(R.string.texto1_1_a2), getString(R.string.texto1_2_a2))));
        preguntas.put(getString(R.string.texto2_a2), new ArrayList<String>(Arrays.asList(getString(R.string.texto2_1_a2), getString(R.string.texto2_2_a2))));
        preguntas.put(getString(R.string.texto3_a2), new ArrayList<String>(Arrays.asList(getString(R.string.texto3_1_a2), getString(R.string.texto3_2_a2))));
        preguntas.put(getString(R.string.texto4_a2), new ArrayList<String>(Arrays.asList(getString(R.string.texto4_1_a2), getString(R.string.texto4_2_a2))));

        mapaPreguntas.put(0,getString(R.string.texto1_a2));
        mapaPreguntas.put(1,getString(R.string.texto2_a2));
        mapaPreguntas.put(2,getString(R.string.texto3_a2));
        mapaPreguntas.put(3,getString(R.string.texto4_a2));

        mapaRespuestas.put(0,2);
        mapaRespuestas.put(1,2);
        mapaRespuestas.put(2,1);
        mapaRespuestas.put(3,1);

    }

    private void siguientePregunta()
    {
        String pregunta="";
        ArrayList<String> respuestaActual = new ArrayList<>();
        String preguntaActual = mapaPreguntas.get(numPregunta);

        for (HashMap.Entry<String, ArrayList> entry : preguntas.entrySet())
        {
            if (entry.getKey().equals(preguntaActual))
            {
                pregunta = entry.getKey();
                respuestaActual = entry.getValue();
            }
        }

        txtPregunta.setText(pregunta);
        btnResp1.setText(respuestaActual.get(0));
        btnResp2.setText(respuestaActual.get(1));
    }

    private void verRespuestaElegida()
    {
        if (btnResp1.isChecked())
        {
            if (mapaRespuestas.get(numPregunta)==1)
                aciertos++;
        }

        if (btnResp2.isChecked())
            if (mapaRespuestas.get(numPregunta)==2)
                aciertos++;
    }



    public void slideUp(View view){
        View v  = findViewById(R.id.pant);
        view.setVisibility(View.VISIBLE);
        ObjectAnimator  encogerY = ObjectAnimator.ofFloat(view,"Y",v.getHeight()-80f,v.getHeight()-view.getHeight());
        encogerY.setDuration(700);
        encogerY.start();
    }

    // slide the view from its current position to below itself
    public void slideDown(View view){
        View v  = findViewById(R.id.pant);
        view.setVisibility(View.VISIBLE);
        ObjectAnimator  encogerY = ObjectAnimator.ofFloat(view,"Y",v.getHeight()-view.getHeight(),v.getHeight()-80f);
        encogerY.setDuration(700);
        encogerY.start();
    }

    public void actiAyuda(View view)
    {
        if(isUp==true)
        {
            slideDown(ayuda);
            isUp=false;
        }
        else
        {
            slideUp(ayuda);
            isUp=true;
        }

    }
    public void onBackPressed() {

        Intent i = new Intent(Actividad2.this, OfflineRegionListActivity.class);

        i.putExtra("actividad", "2");

        startActivity(i);
    }


    @Override
    public void onFragmentPulsado(ImageView imagen) {

    }
}