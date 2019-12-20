package com.aar.app.proyectoLlodio;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class myRecilcerView extends RecyclerView {

    public myRecilcerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public myRecilcerView(@NonNull Context context) {
        super(context);
    }

    public myRecilcerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, expandSpec);
    }
}
