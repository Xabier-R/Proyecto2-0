package com.aar.app.proyectoLlodio;

import android.Manifest;
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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aar.app.proyectoLlodio.offline.OfflineRegionListActivity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Actividad5 extends AppCompatActivity {

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

    Bitmap bmp;
    Intent i;
    final static int cons =0;

    private SQLiteDatabase db;
    private CuentoSqlite cuentoSqlite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad5);


        //set the statue bar background to transparent
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        bipmapdata = new byte[3];

        //Abrimos la base de datos "DBUsuarios" en modo de escritura
        cuentoSqlite = new CuentoSqlite(this, "DBCuentos", null, 1);
        db = cuentoSqlite.getWritableDatabase();



        res = getResources();
        tabs = findViewById(android.R.id.tabhost);
        tabs.setup();


        //Vinculo la tab1 con la pestaña
        TabHost.TabSpec spec = tabs.newTabSpec("mitab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Escribir cuento", res.getDrawable((android.R.drawable.ic_menu_camera)));
        tabs.addTab(spec);

        //Vinculo la tab2 con la pestaña
        spec = tabs.newTabSpec("mitab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Leer cuento", res.getDrawable(android.R.drawable.ic_dialog_map));
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

        recyclerView.setAdapter(adaptadorCuento);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SwipeableRecyclerViewTouchListener swipeTouchListener = new SwipeableRecyclerViewTouchListener(recyclerView,
                new SwipeableRecyclerViewTouchListener.SwipeListener() {
                    int posicion;
                    @Override
                    public boolean canSwipeLeft(int position) {
                        this.posicion=position;
                        return true;
                    }

                    @Override
                    public boolean canSwipeRight(int position) {
                        this.posicion=position;
                        return false;
                    }

                    @Override
                    public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
                        System.out.println("La posicion es " + posicion);
                        actualizarBase(posicion);
                    }

                    @Override
                    public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                        System.out.println("La posicion es " + posicion);
                        actualizarBase(posicion);
                    }
                });



        adaptadorCuento.setOnItemClickListener(new AdaptadorCuento.OnItemClickListener() {
            @Override
            public void onbtnPulsado(int position) {
                actualizarBase(position);
            }

            @Override
            public void onCartaPulsada(int position, ImageView imagen) {
                verCuentoSeleccionado(position, imagen);
            }
        });

        recyclerView.addOnItemTouchListener(swipeTouchListener);

    }

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
            Toast.makeText(getApplicationContext(), "Bete eremu guztiak", Toast.LENGTH_SHORT).show();

    }


    public void aceptar() {


    }

    public void cancelar() {
        Intent i = new Intent(Actividad5.this, OfflineRegionListActivity.class);
        i.putExtra("actividad", "6");
        startActivity(i);
        finish();
    }

    private boolean comprobar()
    {
        if ( (titulo.getText().toString().equals("")== false) && (descripcion.getText().toString().equals("")==false))
        {
            return true;
        }
        return false;
    }

    private void actualizarBase(int pos)
    {
        System.out.println("El tamaño de la lista es " + adaptadorCuento.mdata.size());
        Cuento cuento = adaptadorCuento.mdata.get(pos);
        String[] args = new String[]{cuento.getTitulo()};
        db.execSQL("DELETE FROM Cuento WHERE titulo=?", args);
        adaptadorCuento.mdata.remove(pos);
        adaptadorCuento.notifyItemRemoved(pos);
        adaptadorCuento.notifyItemRangeChanged(pos,adaptadorCuento.mdata.size());
    }

    private void limpiar()
    {
        titulo.setText("");
        descripcion.setText("");

        Drawable drawable = getResources().getDrawable(R.drawable.a5_img4);
        linearFoto.setBackground(drawable);
    }

    //Accede a la camara si tiene permisos, si no tiene saca un jdialog
    public void sacarFoto(View view) {
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 225);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Se necesitan permisos de camara")
                    .setTitle("Permisos Necesarios");
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
            Log.i("Mensaje", "No se tiene permiso para la camara!.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 225);
        } else {
            Log.i("Mensaje", "Tienes permiso para usar la camara.");
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
        }
    }

    private void verCuentoSeleccionado(int position, ImageView imageView)
    {
        String tituloCuento = adaptadorCuento.mdata.get(position).getTitulo();
        String descripcionCuento = adaptadorCuento.mdata.get(position).getDescripcion();
        String fotoCuento = adaptadorCuento.mdata.get(position).getFotoSacada();

        Intent intent = new Intent(this, Actividad5_cuento.class);

        intent.putExtra("titulo", tituloCuento);
        intent.putExtra("descripcion", descripcionCuento);
        intent.putExtra("fotocodificada", fotoCuento);

        Pair<View, String> p1 = Pair.create((View)titulo, "txtTitulo");
        Pair<View, String> p2 = Pair.create((View)descripcion, "txtDescripcion");

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, p1, p2);
        startActivity(intent, options.toBundle());
    }


    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input,0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }


    public void onBackPressed() {

        Intent i = new Intent(Actividad5.this, OfflineRegionListActivity.class);
        i.putExtra("actividad", "5");
        startActivity(i);
        finish();
    }


}