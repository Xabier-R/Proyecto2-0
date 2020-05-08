package com.aar.app.proyectoLaudio.sopaLetras.di.modules;

import android.content.Context;

import com.aar.app.proyectoLaudio.sopaLetras.data.sqlite.DbHelper;
import com.aar.app.proyectoLaudio.sopaLetras.data.sqlite.GameDataSQLiteDataSource;
import com.aar.app.proyectoLaudio.sopaLetras.data.xml.WordXmlDataSource;
import com.aar.app.proyectoLaudio.sopaLetras.data.GameDataSource;
import com.aar.app.proyectoLaudio.sopaLetras.data.WordDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by xabier on 18/07/17.
 */

@Module
public class DataSourceModule {

    @Provides
    @Singleton
    DbHelper provideDbHelper(Context context) {
        return new DbHelper(context);
    }

    @Provides
    @Singleton
    GameDataSource provideGameRoundDataSource(DbHelper dbHelper) {
        return new GameDataSQLiteDataSource(dbHelper);
    }

//    @Provides
//    @Singleton
//    WordDataSource provideWordDataSource(DbHelper dbHelper) {
//        return new WordSQLiteDataSource(dbHelper);
//    }

    @Provides
    @Singleton
    WordDataSource provideWordDataSource(Context context) {
        return new WordXmlDataSource(context);
    }

}
