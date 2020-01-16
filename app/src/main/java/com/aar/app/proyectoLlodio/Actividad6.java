package com.aar.app.proyectoLlodio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.aar.app.proyectoLlodio.offline.OfflineRegionListActivity;

public class Actividad6 extends AppCompatActivity {

    private EditText a6_et1, a6_et2, a6_et3, a6_et4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.actividad6);

        a6_et1 = findViewById(R.id.a6_et1);
        a6_et2 = findViewById(R.id.a6_et2);
        a6_et3 = findViewById(R.id.a6_et3);
        a6_et4 = findViewById(R.id.a6_et4);
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

        String msj = "Erantzun zuzenak: "+cor+", Erantzun okerrak :"+incor;
        Toast.makeText(getApplicationContext(), msj,Toast.LENGTH_SHORT).show();


        if(cor==4)
        {


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
}
