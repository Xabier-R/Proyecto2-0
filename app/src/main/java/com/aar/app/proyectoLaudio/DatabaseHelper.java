package com.aar.app.proyectoLaudio;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

class DatabaseHelper extends SQLiteOpenHelper {

    final private static int DATABASE_VERSION = 1;
    final private static String DATABASE_NAME = "mbgl_offline.db";

    private AssetManager assets;
    private String databaseDir;
    private Context context;

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        this.context=context;
        assets = context.getAssets();
        databaseDir = context.getApplicationInfo().dataDir + "/files/";

        File file = new File(databaseDir);
        if(!file.exists()) file.mkdir();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        copyDatabase();
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        if(!isDatabaseExist())
            copyDatabase();
        return super.getWritableDatabase();
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        if(!isDatabaseExist())
            copyDatabase();
        return super.getReadableDatabase();
    }


    private Boolean isDatabaseExist() {
        return new File(databaseDir + DATABASE_NAME).exists();
    }

    private void copyDatabase() {

        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.mbgl_offline);

            FileOutputStream outputStream = new FileOutputStream(databaseDir + "mbgl-offline.db");

            byte[] buffer = new byte[8 * 1024];

            int readed;
            while ((readed = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, readed);
            }

            outputStream.flush();

            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
