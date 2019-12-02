package com.aar.app.proyectoLlodio;

import android.app.Application;

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
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

}