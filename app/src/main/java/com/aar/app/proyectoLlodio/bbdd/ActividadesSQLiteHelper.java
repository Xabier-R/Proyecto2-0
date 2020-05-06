package com.aar.app.proyectoLlodio.bbdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class ActividadesSQLiteHelper extends SQLiteOpenHelper {


    //Sentencia SQL para crear la tabla de Contactos
    String sqlCreate = "CREATE TABLE actividades (actividad TEXT PRIMARY KEY, realizada TEXT)";
    String sqlCreateIdioma = "CREATE TABLE idiomas (idioma TEXT PRIMARY KEY)";

    public ActividadesSQLiteHelper(Context contexto, String nombre, SQLiteDatabase.CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
        db.execSQL(sqlCreateIdioma);
        insertarActividades(db);
        insertarIdioma(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS Contactos");
        //Se crea la nueva versión de la tabla
        db.execSQL(sqlCreate);
    }

    private void insertarActividades(SQLiteDatabase db) {
        for (int i=1; i<=7; i++) {
            ContentValues nuevoRegistro = new ContentValues();
            nuevoRegistro.put("actividad", "actividad" + i);
            nuevoRegistro.put("realizada",  "no");
            db.insert("actividades", null, nuevoRegistro);
        }
    }

    private void insertarIdioma(SQLiteDatabase db) {
        ContentValues nuevoRegistro = new ContentValues();
        nuevoRegistro.put("idioma", "eu");
        db.insert("idiomas", null, nuevoRegistro);
    }
}
