package com.aar.app.proyectoLaudio.sopaLetras.features.gameplay;

import com.aar.app.proyectoLaudio.sopaLetras.commons.Mapper;
import com.aar.app.proyectoLaudio.sopaLetras.model.UsedWord;
import com.aar.app.proyectoLaudio.sopaLetras.custom.StreakView;

/**
 * Created by xabier on 09/07/17.
 */

public class StreakLineMapper extends Mapper<UsedWord.AnswerLine, StreakView.StreakLine> {
    @Override
    public StreakView.StreakLine map(UsedWord.AnswerLine obj) {
        StreakView.StreakLine s = new StreakView.StreakLine();
        s.getStartIndex().set(obj.startRow, obj.startCol);
        s.getEndIndex().set(obj.endRow, obj.endCol);
        s.setColor(obj.color);

        return s;
    }

    @Override
    public UsedWord.AnswerLine revMap(StreakView.StreakLine obj) {
        UsedWord.AnswerLine a = new UsedWord.AnswerLine();
        a.startRow = obj.getStartIndex().row;
        a.startCol = obj.getStartIndex().col;
        a.endRow = obj.getEndIndex().row;
        a.endCol = obj.getEndIndex().col;
        a.color = obj.getColor();

        return a;
    }
}
