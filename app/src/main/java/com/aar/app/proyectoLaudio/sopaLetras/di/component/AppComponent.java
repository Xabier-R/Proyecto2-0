package com.aar.app.proyectoLaudio.sopaLetras.di.component;

import com.aar.app.proyectoLaudio.sopaLetras.di.modules.AppModule;
import com.aar.app.proyectoLaudio.sopaLetras.di.modules.DataSourceModule;
import com.aar.app.proyectoLaudio.sopaLetras.features.FullscreenActivity;
import com.aar.app.proyectoLaudio.sopaLetras.features.gameover.GameOverActivity;
import com.aar.app.proyectoLaudio.sopaLetras.features.gameplay.GamePlayActivity;
import com.aar.app.proyectoLaudio.sopaLetras.features.mainmenu.MainMenuActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by xabier on 18/07/17.
 */

@Singleton
@Component(modules = {AppModule.class, DataSourceModule.class})
public interface AppComponent {

    void inject(GamePlayActivity activity);

    void inject(MainMenuActivity activity);

    void inject(GameOverActivity activity);

    void inject(FullscreenActivity activity);

}
