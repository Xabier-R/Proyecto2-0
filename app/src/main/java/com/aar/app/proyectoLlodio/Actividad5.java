package com.aar.app.proyectoLlodio;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Actividad5 extends AppCompatActivity {

    private Resources res;
    private TabHost tabs;
    private EditText titulo;
    private EditText descripcion;
    private RecyclerView recyclerView;
    AdaptadorCuento adaptadorCuento;
    private LinearLayout foto;


    Bitmap bmp;
    Intent i;
    final static int cons =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad5);

        //set the statue bar background to transparent
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

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
        foto = findViewById(R.id.linearImagen);

        checkCameraPermission();

    }

    public void leerCuentos() {
        final ArrayList<Cuento> cuentos = new ArrayList<Cuento>();
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput("cuentos.txt")));
            String linea = br.readLine();
            while (linea!=null)
            {
                String [] trozos = linea.split(",");
                cuentos.add(new Cuento(trozos[0], trozos[1]));
                linea = br.readLine();
            }
            recyclerView = findViewById(R.id.rv_lista);
            adaptadorCuento = new AdaptadorCuento(this, cuentos);

            recyclerView.setAdapter(adaptadorCuento);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            adaptadorCuento.setOnItemClickListener(new AdaptadorCuento.OnItemClickListener() {
                @Override
                public void onbtnPulsado(int position) {
                    Toast.makeText(getApplicationContext(), "Borrado", Toast.LENGTH_SHORT).show();

                    //Remuevo la posicion seleccionada
                    adaptadorCuento.mdata.remove(position);
                    adaptadorCuento.notifyItemRemoved(position);
                    adaptadorCuento.notifyItemRangeChanged(position, adaptadorCuento.mdata.size());

                    //Actualizo el fichero
                    actualizarFichero(position);
                }

                @Override
                public void onCartaPulsada(int position, ImageView imageView) {
                    Toast.makeText(getApplicationContext(), "Carta seleccionada", Toast.LENGTH_SHORT).show();
                    verCuentoSeleccionado(position, imageView);
                }
            });
            br.close();
        }
        catch (Exception e)
        {
            Log.d("Fichero", "Error al leer el fichero interno");
        }
    }

    public void guardarCuento(View view) {
        String tituloCuento = titulo.getText().toString();
        String descripcionCuento = descripcion.getText().toString();
        String resultado = tituloCuento + "," + descripcionCuento;
        try{
            OutputStreamWriter osw= new OutputStreamWriter(openFileOutput("cuentos.txt", Context.MODE_APPEND));
            osw.write( resultado + "\n");
            osw.close();
            Toast.makeText(getApplicationContext(), "CUENTO GUARDADO", Toast.LENGTH_SHORT).show();
            limpiar();
        }
        catch (Exception e) {
            Log.e ("Fichero", "ERROR!! al escribir fichero en memoria interna");
        }
    }

    private void actualizarFichero(int pos)
    {
        //Borrar fichero falta acabar
        final ArrayList<Cuento> cuentos = new ArrayList<Cuento>();
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput("cuentos.txt")));
            String linea = br.readLine();
            while (linea!=null)
            {
                String [] trozos = linea.split(",");
                cuentos.add(new Cuento(trozos[0], trozos[1]));
                linea = br.readLine();
            }
            cuentos.remove(pos);
            br.close();

            String dir = getFilesDir().getAbsolutePath();
            File f0 = new File(dir, "cuentos.txt");
            boolean deleted = f0.delete();
            if (deleted)
                Log.d("borrado", "si");
            else
                Log.d("borrado", "no");


            OutputStreamWriter osw= new OutputStreamWriter(openFileOutput("cuentos.txt", Context.MODE_APPEND));
            for (int i=0; i<cuentos.size(); i++)
            {
                String lineaEscribir = cuentos.get(i).getTitulo() + "," + cuentos.get(i).getDescripcion();
                osw.write( lineaEscribir + "\n");
            }
            osw.close();
        }
        catch (Exception e)
        {
            Log.d("Fichero", "Error al leer el fichero interno");
        }
    }

    private void limpiar()
    {
        titulo.setText("");
        descripcion.setText("");
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
            foto.setBackground(ob);

        }
    }

    private void verCuentoSeleccionado(int position, ImageView imageView)
    {

        String tituloCuento = adaptadorCuento.mdata.get(position).getTitulo();
        String descripcionCuento = adaptadorCuento.mdata.get(position).getDescripcion();

        Intent intent = new Intent(this, Actividad5_cuento.class);

        intent.putExtra("titulo", tituloCuento);
        intent.putExtra("descripcion", descripcionCuento);



        Pair<View, String> p1 = Pair.create((View)titulo, "txtTitulo");
        Pair<View, String> p2 = Pair.create((View)descripcion, "txtDescripcion");
        Pair<View, String> p3 = Pair.create((View)imageView, "imagenPatrimonio");

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, p1, p2, p3);
        startActivity(intent, options.toBundle());
    }
}