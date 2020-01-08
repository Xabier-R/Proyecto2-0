package com.aar.app.proyectoLlodio;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.mapbox.mapboxsdk.Mapbox;

import com.aar.app.proyectoLlodio.sopaLetras.di.component.AppComponent;
import com.aar.app.proyectoLlodio.sopaLetras.di.component.DaggerAppComponent;
import com.aar.app.proyectoLlodio.sopaLetras.di.modules.AppModule;

/**
 * Created by xabier on 18/07/17.
 */

public class WordSearchApp extends Application {

    AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();

        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        DatabaseHelper usdbh = new DatabaseHelper(this);
        SQLiteDatabase db = usdbh.getReadableDatabase();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

}