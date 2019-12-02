package com.aar.app.proyectoLlodio.sopaLetras.features.gameover;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aar.app.proyectoLlodio.sopaLetras.data.GameDataSource;
import com.aar.app.proyectoLlodio.sopaLetras.model.GameDataInfo;

public class GameOverViewModel extends ViewModel {

    private GameDataSource mGameDataSource;
    private MutableLiveData<GameDataInfo> mOnGameDataInfoLoaded = new MutableLiveData<>();

    public GameOverViewModel(GameDataSource gameDataSource) {
        mGameDataSource = gameDataSource;
    }

    public void loadData(int gid) {
        mGameDataSource.getGameDataInfo(gid, mOnGameDataInfoLoaded::setValue);
    }

    public void deleteGameRound(int gid) {
        mGameDataSource.deleteGameData(gid);
    }

    public LiveData<GameDataInfo> getOnGameDataInfoLoaded() {
        return mOnGameDataInfoLoaded;
    }
}
