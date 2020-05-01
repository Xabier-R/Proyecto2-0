package com.aar.app.proyectoLlodio;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.aar.app.proyectoLlodio.bbdd.ActividadesSQLiteHelper;
import com.aar.app.proyectoLlodio.offline.OfflineRegionListActivity;

public class Actividad6 extends AppCompatActivity implements Fragmento_ayuda.OnFragmentInteractionListener{

    private EditText a6_et1, a6_et2, a6_et3, a6_et4;
    //BBDD
    private ActividadesSQLiteHelper activiades;
    private SQLiteDatabase db;

    private LinearLayout ayuda;
    private boolean isUp=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.actividad6);
        Fragment fragment = new Fragmento_ayuda(getString(R.string.ayuda6));
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.linearFragmento, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();

        ayuda = findViewById(R.id.linearFragmento);
        a6_et1 = findViewById(R.id.a6_et1);
        a6_et2 = findViewById(R.id.a6_et2);
        a6_et3 = findViewById(R.id.a6_et3);
        a6_et4 = findViewById(R.id.a6_et4);



        //Abrimos la base de datos "DBUsuarios" en modo de escritura
        activiades = new ActividadesSQLiteHelper(this, "DBactividades", null, 1);
        db = activiades.getWritableDatabase();

    }

    public void reset(View view) {
        a6_et1.setText("");
        a6_et2.setText("");
        a6_et3.setText("");
        a6_et4.setText("");
    }

    public void comprobar(View view) {

        int cor=0;
        int incor=0;


        if(a6_et1.getText().toString().equalsIgnoreCase("Kultur etxea")){cor++;}else{incor++;}
        if(a6_et2.getText().toString().equalsIgnoreCase("Skate parkea")){cor++;}else{incor++;}
        if(a6_et3.getText().toString().equalsIgnoreCase("Jauregia")){cor++;}else{incor++;}
        if(a6_et4.getText().toString().equalsIgnoreCase("Lorategia")){cor++;}else{incor++;}

        String msj = "Erantzun zuzenak: "+cor+", Erantzun okerrak: "+incor;
        Toast.makeText(getApplicationContext(), msj,Toast.LENGTH_SHORT).show();


        if(cor==4)
        {


            //Marco como realizada la actividad 2
            db.execSQL("UPDATE actividades SET realizada='si' WHERE actividad='actividad6'");
            db.close();

            Intent i = new Intent(Actividad6.this, OfflineRegionListActivity.class);
            i.putExtra("actividad", "7");
            startActivity(i);

        }



    }

    public void onBackPressed() {

        Intent i = new Intent(Actividad6.this, OfflineRegionListActivity.class);
        i.putExtra("actividad", "6");
        startActivity(i);
        finish();
    }

    @Override
    public void onFragmentPulsado(ImageView imagen) {

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


}
