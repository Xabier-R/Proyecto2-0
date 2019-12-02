package com.aar.app.proyectoLlodio.sopaLetras.di.modules;

import android.content.Context;

import com.aar.app.proyectoLlodio.sopaLetras.data.sqlite.DbHelper;
import com.aar.app.proyectoLlodio.sopaLetras.data.sqlite.GameDataSQLiteDataSource;
import com.aar.app.proyectoLlodio.sopaLetras.data.xml.WordXmlDataSource;
import com.aar.app.proyectoLlodio.sopaLetras.data.GameDataSource;
import com.aar.app.proyectoLlodio.sopaLetras.data.WordDataSource;

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
