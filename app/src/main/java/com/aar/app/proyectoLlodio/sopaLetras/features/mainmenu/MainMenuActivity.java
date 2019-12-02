package com.aar.app.proyectoLlodio.sopaLetras.features.mainmenu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Spinner;

import com.aar.app.proyectoLlodio.R;
import com.aar.app.proyectoLlodio.sopaLetras.features.ViewModelFactory;
import com.aar.app.proyectoLlodio.WordSearchApp;
import com.aar.app.proyectoLlodio.sopaLetras.features.FullscreenActivity;
import com.aar.app.proyectoLlodio.sopaLetras.features.gameplay.GamePlayActivity;
import com.aar.app.proyectoLlodio.sopaLetras.model.GameTheme;
import com.aar.app.proyectoLlodio.sopaLetras.easyadapter.MultiTypeAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainMenuActivity extends FullscreenActivity {

//    @BindView(R.id.rv) RecyclerView mRv;
    @BindView(R.id.game_template_spinner) Spinner mGridSizeSpinner;

    @BindArray(R.array.game_round_dimension_values)
    int[] mGameRoundDimVals;

    @Inject
    ViewModelFactory mViewModelFactory;
    MultiTypeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        ((WordSearchApp) getApplication()).getAppComponent().inject(this);

        mAdapter = new MultiTypeAdapter();
    }

    public void showGameThemeList(List<GameTheme> gameThemes) {
        mAdapter.setItems(gameThemes);

    }

    public void onNewGameClick() {
        int dim = mGameRoundDimVals[ mGridSizeSpinner.getSelectedItemPosition() ];
        Intent intent = new Intent(MainMenuActivity.this, GamePlayActivity.class);
        intent.putExtra(GamePlayActivity.EXTRA_ROW_COUNT, dim);
        intent.putExtra(GamePlayActivity.EXTRA_COL_COUNT, dim);
        startActivity(intent);
    }

}
