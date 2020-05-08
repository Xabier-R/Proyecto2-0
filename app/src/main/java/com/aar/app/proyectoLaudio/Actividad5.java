package com.aar.app.proyectoLaudio;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aar.app.proyectoLaudio.bbdd.ActividadesSQLiteHelper;
import com.aar.app.proyectoLaudio.offline.OfflineRegionListActivity;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class Actividad5 extends AppCompatActivity implements Fragmento_ayuda.OnFragmentInteractionListener {

    private Resources res;
    private TabHost tabs;
    private EditText titulo;
    private EditText descripcion;
    private RecyclerView recyclerView;
    public AdaptadorCuento adaptadorCuento;

    private ImageView img;
    private LinearLayout linearFoto;
    private byte [] bipmapdata;
    private String fotoCodificada;
    private boolean fotoSacada=false;
    private LinearLayout ayuda;
    private boolean isUp=true;

    Bitmap bmp;
    Intent i;
    final static int cons =0;

    //BBDD
    private ActividadesSQLiteHelper activiades;
    private SQLiteDatabase db1;

    private SQLiteDatabase db;
    private CuentoSqlite cuentoSqlite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.actividad5);

        Fragment fragment = new Fragmento_ayuda(getString(R.string.ayuda5));
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.linearFragmento, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();


        ayuda = findViewById(R.id.linearFragmento);

        //Abrimos la base de datos "DBactividades" en modo de escritura
        activiades = new ActividadesSQLiteHelper(this, "DBactividades", null, 1);
        db1 = activiades.getWritableDatabase();

        bipmapdata = new byte[3];

        //Abrimos la base de datos "DBCuentos" en modo de escritura
        cuentoSqlite = new CuentoSqlite(this, "DBCuentos", null, 1);
        db = cuentoSqlite.getWritableDatabase();


        res = getResources();
        tabs = findViewById(android.R.id.tabhost);
        tabs.setup();


        //Vinculo la tab1 con la pestaña
        TabHost.TabSpec spec = tabs.newTabSpec("mitab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Ipuina idatzi", res.getDrawable((android.R.drawable.ic_menu_camera)));
        tabs.addTab(spec);

        //Vinculo la tab2 con la pestaña
        spec = tabs.newTabSpec("mitab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Ipuina irakurri", res.getDrawable(android.R.drawable.ic_dialog_map));
        tabs.addTab(spec);

        tabs.setCurrentTab(0);
        tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String tabId) {
                if (tabId.equals("mitab2"))
                {
                    leerCuentos();
                }

            }
        });

        titulo = findViewById(R.id.titulo);
        descripcion = findViewById(R.id.cuento);
        linearFoto = findViewById(R.id.linearImagen);


        checkCameraPermission();
    }

    //Metodo que obtiene todos los cuentos que hay en la base de datos y los carga en la lista
    public void leerCuentos() {
        final ArrayList<Cuento> cuentos = new ArrayList<Cuento>();
        Cursor c = db.rawQuery("SELECT titulo, descripcion, foto FROM Cuento", null);

        if (c.moveToFirst()){
            //Recorremos el cursor hasta que no haya más registros.
            do {
                String tituloCuento = c.getString(0);
                String descripcionCuento = c.getString(1);
                String foto = c.getString(2);
                cuentos.add(new Cuento(tituloCuento, descripcionCuento, foto));
            }while (c.moveToNext());
        }

        recyclerView = findViewById(R.id.rv_lista);
        adaptadorCuento = new AdaptadorCuento(this, cuentos);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adaptadorCuento);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        adaptadorCuento.setOnItemClickListener(new AdaptadorCuento.OnItemClickListener() {
            @Override
            public void onbtnPulsado(int position) {
                actualizarBase(position);
            }

            @Override
            public void onCartaPulsada(int position) {
                verCuentoSeleccionado(position);
            }
        });



    }


    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }


        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            Cuento cuento = adaptadorCuento.mdata.get(position);
            if (ItemTouchHelper.LEFT == direction) {
                actualizarBase(position);
                Snackbar.make(recyclerView, cuento.getTitulo() + " ezabatu da", Snackbar.LENGTH_LONG).show();
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            //.addSwipeLeftActionIcon(R.drawable.borrarmedio)
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(Actividad5.this, R.color.borrar))
                    .addSwipeLeftActionIcon(R.drawable.borrar)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    //Metodo que borra un cuento de la BBDD y de la lista
    private void actualizarBase(int pos)
    {
        Cuento cuento = adaptadorCuento.mdata.get(pos);
        String[] args = new String[]{cuento.getTitulo()};
        db.execSQL("DELETE FROM Cuento WHERE titulo=?", args);
        adaptadorCuento.mdata.remove(pos);
        adaptadorCuento.notifyItemRemoved(pos);
        //adaptadorCuento.notifyItemRangeChanged(pos,adaptadorCuento.mdata.size());
    }

    //Metodo que guarda un cuento en la BBDD
    public void guardarCuento(View view) {
        boolean puede = comprobar();

        if (puede)
        {
            String tituloCuento = titulo.getText().toString();
            String descripcionCuento = descripcion.getText().toString();


            //encode image to base64 string
            BitmapDrawable drawable = (BitmapDrawable) linearFoto.getBackground();
            Bitmap bitmap = drawable.getBitmap();
            String encodedImage = encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 100);


            ContentValues nuevoCuento = new ContentValues();
            nuevoCuento.put("titulo", tituloCuento);
            nuevoCuento.put("descripcion",  descripcionCuento);
            nuevoCuento.put("foto",  encodedImage);

            db.insert("Cuento", null, nuevoCuento);
            limpiar();
            Toast.makeText(getApplicationContext(), "Ipuina gordeta", Toast.LENGTH_SHORT).show();


            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
            dialogo1.setTitle("");
            dialogo1.setMessage("¿ Beste ipuin bat gorde nahi duzu ?");
            dialogo1.setCancelable(false);
            dialogo1.setPositiveButton("BAI", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    aceptar();
                }
            });
            dialogo1.setNegativeButton("EZ", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    cancelar();
                }
            });
            dialogo1.show();



        }
        else
            Toast.makeText(getApplicationContext(), "Eremu guztiak bete", Toast.LENGTH_SHORT).show();

    }


    public void aceptar() {


    }

    //Metodo que marca la actividad como realizada en la BBDD y vuelva al mapa
    public void cancelar() {
        //Marco como realizada la actividad 2
        db1.execSQL("UPDATE actividades SET realizada='si' WHERE actividad='actividad5'");
        db1.close();

        Intent i = new Intent(Actividad5.this, OfflineRegionListActivity.class);
        i.putExtra("actividad", "6");
        startActivity(i);
        finish();
    }



    //Metodo para comprobar que no guardamos un cuento sin titulo, descripcion o foto
    private boolean comprobar()
    {
        if ( (titulo.getText().toString().equals("")== false) && (descripcion.getText().toString().equals("")==false) && (fotoSacada==true)) {
            return true;
        }
        return false;
    }


    //Metodo que limpia todos lo campos despues de guardar un cuento
    private void limpiar()
    {
        titulo.setText("");
        descripcion.setText("");

        Drawable drawable = getResources().getDrawable(R.drawable.a5_img4);
        linearFoto.setBackground(drawable);
        fotoSacada=false;
    }

    //Accede a la camara si tiene permisos, si no tiene saca un jdialog
    public void sacarFoto(View view) {
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 225);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Argazkiak ateratzeko baimena behar duzu")
                    .setTitle("Oharra");
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else
        {
            i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(i, cons);
        }

    }

    //Comprueba los permisos de la camara
    private void checkCameraPermission()
    {
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 225);
        } else {

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (resultCode== Activity.RESULT_OK){
            Bundle ext = data.getExtras();
            bmp = (Bitmap)ext.get("data");
            BitmapDrawable ob = new BitmapDrawable(getResources(), bmp);
            linearFoto.setBackground(ob);
            fotoSacada=true;
        }
    }

    //Metodo para ver detalladamente un cuento escrito
    private void verCuentoSeleccionado(int position)
    {
        String tituloCuento = adaptadorCuento.mdata.get(position).getTitulo();
        String descripcionCuento = adaptadorCuento.mdata.get(position).getDescripcion();
        String fotoCuento = adaptadorCuento.mdata.get(position).getFotoSacada();

        Intent intent = new Intent(this, Actividad5_cuento.class);

        intent.putExtra("titulo", tituloCuento);
        intent.putExtra("descripcion", descripcionCuento);
        intent.putExtra("fotocodificada", fotoCuento);

        startActivity(intent);
    }

    //Metodo que codifica la foto saca en Base64 para guardarla en la BBDD
    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    //Metodo que descodifica la foto en Base64 a un Bitmap
    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input,0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    //metodo para mostrar el fragmento de ayuda
    public void slideUp(View view){
        View v  = findViewById(R.id.pant);
        view.setVisibility(View.VISIBLE);
        ObjectAnimator encogerY = ObjectAnimator.ofFloat(view,"Y",v.getHeight()-80f,v.getHeight()-view.getHeight());
        encogerY.setDuration(700);
        encogerY.start();
    }

    // Metodo para ocultar el fragmento de ayuda
    public void slideDown(View view){
        View v  = findViewById(R.id.pant);
        view.setVisibility(View.VISIBLE);
        ObjectAnimator  encogerY = ObjectAnimator.ofFloat(view,"Y",v.getHeight()-view.getHeight(),v.getHeight()-80f);
        encogerY.setDuration(700);
        encogerY.start();
    }

    //Metodo que comprueba si el fragmento de ayuda se muestra
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

    //metodo que lanza la pantalla anterior pulsar el boton atras
    public void onBackPressed() {
        Intent i = new Intent(Actividad5.this, OfflineRegionListActivity.class);
        i.putExtra("actividad", "5");
        startActivity(i);
        finish();
    }


    @Override
    public void onFragmentPulsado(ImageView imagen) {

    }
}