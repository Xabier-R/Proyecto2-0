package com.aar.app.proyectoLlodio;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.aar.app.proyectoLlodio.bbdd.ActividadesSQLiteHelper;
import com.aar.app.proyectoLlodio.offline.OfflineRegionListActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Actividad4 extends AppCompatActivity{

    private TextView option1, option2, option3, option4, option5, option6, option7;
    private EditText choice1, choice2, choice3, choice4, choice5, choice6, choice7;
    private Map<String, String> maprpta;
    public CharSequence dragData;
    public ArrayList<EditText> listaEts;
    //BBDD
    private ActividadesSQLiteHelper activiades;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.actividad4);

        //Abrimos la base de datos "DBUsuarios" en modo de escritura
        activiades = new ActividadesSQLiteHelper(this, "DBactividades", null, 1);
        db = activiades.getWritableDatabase();

        listaEts = new ArrayList<EditText>();

        //Cargar el mapa
        maprpta = new HashMap<>();
        maprpta.put("choice_1", "errekara");
        maprpta.put("choice_2", "sorginzulo");
        maprpta.put("choice_3", "ibilbide");
        maprpta.put("choice_4", "sorginik");
        maprpta.put("choice_5", "zubi");
        maprpta.put("choice_6", "madarikazioak");
        maprpta.put("choice_7", "Lezeagako");

        //views to drop onto
        choice1 = findViewById(R.id.choice_1);
        choice2 = findViewById(R.id.choice_2);
        choice3 = findViewById(R.id.choice_3);
        choice4 = findViewById(R.id.choice_4);
        choice5 = findViewById(R.id.choice_5);
        choice6 = findViewById(R.id.choice_6);
        choice7 = findViewById(R.id.choice_7);

        //views to drag
        option1 = findViewById(R.id.option_1);
        option2 = findViewById(R.id.option_2);
        option3 = findViewById(R.id.option_3);
        option4 = findViewById(R.id.option_4);
        option5 = findViewById(R.id.option_5);
        option6 = findViewById(R.id.option_6);
        option7 = findViewById(R.id.option_7);

        //set touch listeners
        option1.setOnTouchListener(new ChoiceTouchListener());
        option2.setOnTouchListener(new ChoiceTouchListener());
        option3.setOnTouchListener(new ChoiceTouchListener());
        option4.setOnTouchListener(new ChoiceTouchListener());
        option5.setOnTouchListener(new ChoiceTouchListener());
        option6.setOnTouchListener(new ChoiceTouchListener());
        option7.setOnTouchListener(new ChoiceTouchListener());

        //set drag listeners
        choice1.setOnDragListener(new ChoiceDragListener());
        choice2.setOnDragListener(new ChoiceDragListener());
        choice3.setOnDragListener(new ChoiceDragListener());
        choice4.setOnDragListener(new ChoiceDragListener());
        choice5.setOnDragListener(new ChoiceDragListener());
        choice6.setOnDragListener(new ChoiceDragListener());
        choice7.setOnDragListener(new ChoiceDragListener());

    }

    private final class ChoiceTouchListener implements OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                DragShadowBuilder shadowBuilder = new DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                return true;
            } else {
                return false;
            }
        }
    }

    @SuppressLint("NewApi")
    private class ChoiceDragListener implements OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    View view = (View) event.getLocalState();

                    EditText spacetofill = (EditText) v;
                    TextView cadenarpta = (TextView) view;

                    String strfill = getResources().getResourceEntryName(spacetofill.getId());
                    //El texto del elemento arrastrado es igual al valor obtenido del mapa, pasandole como parametro el idname del elemento a ser rellenado.

                    if( cadenarpta.getText().toString().equals(maprpta.get(strfill)) )
                    {
                        view.setVisibility(View.INVISIBLE);

                        spacetofill.setText(cadenarpta.getText().toString());
                        spacetofill.setGravity(Gravity.CENTER_HORIZONTAL);
                        spacetofill.setTypeface(Typeface.DEFAULT_BOLD);

                        Object tag = spacetofill.getTag();
                        if(tag!=null)
                        {
                            int existingID = (Integer)tag;
                            findViewById(existingID).setVisibility(View.VISIBLE);
                        }

                        spacetofill.setTag(cadenarpta.getId());
                        spacetofill.setOnDragListener(null);
                        spacetofill.setCompoundDrawables(null, null, null, null);

                        listaEts.add(spacetofill);

                    }else{
                        spacetofill.setCompoundDrawables(null, null, getResources().getDrawable(R.drawable.warning), null);
                    }

                    onComplete();
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    break;
                default:
                    break;
            }
            return true;
        }
    }

    public void reset(View view)
    {
        option1.setVisibility(TextView.VISIBLE);
        option2.setVisibility(TextView.VISIBLE);
        option3.setVisibility(TextView.VISIBLE);
        option4.setVisibility(TextView.VISIBLE);
        option5.setVisibility(TextView.VISIBLE);
        option6.setVisibility(TextView.VISIBLE);
        option7.setVisibility(TextView.VISIBLE);

        choice1.setText("");
        choice2.setText("");
        choice3.setText("");
        choice4.setText("");
        choice5.setText("");
        choice6.setText("");
        choice7.setText("");

        choice1.setTag(null);
        choice2.setTag(null);
        choice3.setTag(null);
        choice4.setTag(null);
        choice5.setTag(null);
        choice6.setTag(null);
        choice7.setTag(null);

        choice1.setTypeface(Typeface.DEFAULT);
        choice2.setTypeface(Typeface.DEFAULT);
        choice3.setTypeface(Typeface.DEFAULT);
        choice4.setTypeface(Typeface.DEFAULT);
        choice5.setTypeface(Typeface.DEFAULT);
        choice6.setTypeface(Typeface.DEFAULT);
        choice7.setTypeface(Typeface.DEFAULT);

        choice1.setOnDragListener(new ChoiceDragListener());
        choice2.setOnDragListener(new ChoiceDragListener());
        choice3.setOnDragListener(new ChoiceDragListener());
        choice4.setOnDragListener(new ChoiceDragListener());
        choice5.setOnDragListener(new ChoiceDragListener());
        choice6.setOnDragListener(new ChoiceDragListener());
        choice7.setOnDragListener(new ChoiceDragListener());
        listaEts.clear();
    }

    public void onComplete(){
        if(listaEts.size()==7){

            //Marco como realizada la actividad 2
            db.execSQL("UPDATE actividades SET realizada='si' WHERE actividad='actividad4'");
            db.close();

            Toast.makeText(this,"Oso ondo", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Actividad4.this, OfflineRegionListActivity.class);
            i.putExtra("actividad", "5");
            startActivity(i);


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }


    public void onBackPressed() {

        Intent i = new Intent(Actividad4.this, OfflineRegionListActivity.class);
        i.putExtra("actividad", "4");
        startActivity(i);
        finish();
    }

}
