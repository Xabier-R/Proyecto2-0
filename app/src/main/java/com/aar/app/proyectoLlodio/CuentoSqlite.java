package com.aar.app.proyectoLlodio;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class CuentoSqlite extends SQLiteOpenHelper {

    //Sentencia SQL para crear la tabla de Cuento
    String sqlCreate = "CREATE TABLE Cuento (titulo TEXT PRIMARY KEY, descripcion TEXT, foto TEXT)";
    public CuentoSqlite(Context contexto, String nombre, CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }
    //Metodo que ejecuta la sentencia para la creacion de la tabla Cuento
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS Cuento");
        //Se crea la nueva versión de la tabla
        db.execSQL(sqlCreate);
    }
}
