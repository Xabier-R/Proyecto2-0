package com.aar.app.proyectoLaudio.sopaLetras.di.modules;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.aar.app.proyectoLaudio.sopaLetras.features.ViewModelFactory;
import com.aar.app.proyectoLaudio.sopaLetras.data.GameDataSource;
import com.aar.app.proyectoLaudio.sopaLetras.data.WordDataSource;
import com.aar.app.proyectoLaudio.sopaLetras.features.gameover.GameOverViewModel;
import com.aar.app.proyectoLaudio.sopaLetras.features.gameplay.GamePlayViewModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by xabier on 18/07/17.
 */

@Module
public class AppModule {

    private Application mApp;

    public AppModule(Application application) {
        mApp = application;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return mApp;
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    @Singleton
    ViewModelFactory provideViewModelFactory(GameDataSource gameDataSource,
                                             WordDataSource wordDataSource) {
        return new ViewModelFactory(
                new GameOverViewModel(gameDataSource),
                new GamePlayViewModel(gameDataSource, wordDataSource)
        );
    }
}
