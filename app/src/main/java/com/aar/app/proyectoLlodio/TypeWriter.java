package com.aar.app.proyectoLlodio;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

import java.util.LinkedList;
import java.util.Queue;


public class TypeWriter extends androidx.appcompat.widget.AppCompatTextView {

    private boolean isRunning = false;
    private long mTypeSpeed = 58;
    private long mDeleteSpeed = 10;
    private long mPauseDelay = 1000;

    public long getmTypeSpeed() {
        return mTypeSpeed;
    }

    public void setmTypeSpeed(long mTypeSpeed) {
        this.mTypeSpeed = mTypeSpeed;
    }

    public Queue<Repeater> mRunnableQueue = new LinkedList<>();

    private Runnable mRunNextRunnable = new Runnable() {
        @Override
        public void run() {
            runNext();
        }
    };

    public TypeWriter(Context context, AttributeSet attrs) {
        super(context, attrs);

        setBackgroundColor(Color.TRANSPARENT);
        setCursorAtEnd();
        setCursorVisible(true);
        setRawInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
    }

    public TypeWriter type(CharSequence text, long speed) {
        mRunnableQueue.add(new TextAdder(text, speed, mRunNextRunnable));
        if (!isRunning) runNext();
        return this;
    }

    public TypeWriter type(CharSequence text) {
        return type(text, mTypeSpeed);
    }

    public TypeWriter delete(CharSequence text, long speed) {
        mRunnableQueue.add(new TextRemover(text, speed, mRunNextRunnable));

        if (!isRunning) runNext();

        return this;
    }

    public TypeWriter delete(CharSequence text) {
        return delete(text, mDeleteSpeed);
    }

    public TypeWriter pause(long millis) {
        mRunnableQueue.add(new TypePauser(millis, mRunNextRunnable));

        if (!isRunning) runNext();

        return this;
    }

    public TypeWriter run(Runnable runnable) {
        mRunnableQueue.add(new TypeRunnable(runnable, mRunNextRunnable));

        if (!isRunning) runNext();
        return this;
    }

    public TypeWriter pause() {
        return pause(mPauseDelay);
    }

    private void setCursorAtEnd() {
       // setSelection(getText().length());
    }

    private void runNext() {
        isRunning = true;
        Repeater next = mRunnableQueue.poll();

        if (next == null) {
            isRunning = false;
            return;
        }

        next.run();
    }

    private abstract class Repeater implements Runnable {
        protected Handler mHandler = new Handler();
        private Runnable mDoneRunnable;
        private long mDelay;

        public Repeater(Runnable doneRunnable, long delay) {
            mDoneRunnable = doneRunnable;
            mDelay = delay;
        }

        protected void done() {
            mDoneRunnable.run();
        }

        protected void delayAndRepeat() {
            mHandler.postDelayed(this, mDelay);
        }
    }

    private class TextAdder extends Repeater {

        private CharSequence mTextToAdd;

        public TextAdder(CharSequence textToAdd, long speed, Runnable doneRunnable) {
            super(doneRunnable, speed);

            mTextToAdd = textToAdd;
        }



//        public void stop() {
//            this.finalize();
//        }

        @Override
        public void run() {
            if (mTextToAdd.length() == 0) {
                done();
                return;
            }

            char first = mTextToAdd.charAt(0);
            mTextToAdd = mTextToAdd.subSequence(1, mTextToAdd.length());

            ScrollView scrollViewP1 = Pantalla1.scrollView;
            ScrollView scrollView1 = Actividad1_empezar.scrollView;
            ScrollView scrollView2 = Actividad2_empezar.scrollView;
            ScrollView scrollView3 = Actividad3.scrollView;
            ScrollView scrollView4 = Actividad4_empezar.scrollView;
            ScrollView scrollView5 = Actividad5_empezar.scrollView;
            ScrollView scrollView6 = Actividad6_empezar.scrollView;
            ScrollView scrollView7 = Actividad7.scrollView;


            if(scrollViewP1 != null ){
                scrollViewP1.fullScroll(View.FOCUS_DOWN);
            }
            if(scrollView1 != null) {
                scrollView1.fullScroll(View.FOCUS_DOWN);
            }
            if(scrollView2 != null) {
                scrollView2.fullScroll(View.FOCUS_DOWN);
            }
            if(scrollView3 != null) {
                scrollView3.fullScroll(View.FOCUS_DOWN);
            }
            if(scrollView4 != null) {
                scrollView4.fullScroll(View.FOCUS_DOWN);
            }
            if(scrollView5 != null) {
                scrollView5.fullScroll(View.FOCUS_DOWN);
            }
            if(scrollView6 != null) {
                scrollView6.fullScroll(View.FOCUS_DOWN);
            }
            if(scrollView7 != null) {
                scrollView7.fullScroll(View.FOCUS_DOWN);
            }


            CharSequence text = getText();
            setText(text.toString() + first);
            setCursorAtEnd();
            delayAndRepeat();
        }


    }

    private class TextRemover extends Repeater {

        private CharSequence mTextToRemove;

        public TextRemover(CharSequence textToRemove, long speed, Runnable doneRunnable) {
            super(doneRunnable, speed);

            mTextToRemove = textToRemove;
        }

        @Override
        public void run() {
            if (mTextToRemove.length() == 0) {
                done();
                return;
            }

            char last = mTextToRemove.charAt(mTextToRemove.length() - 1);
            mTextToRemove = mTextToRemove.subSequence(0, mTextToRemove.length() - 1);

            CharSequence text = getText();

            if (text.charAt(text.length() - 1) == last) {
                setText(text.subSequence(0, text.length() - 1));
            }

            setCursorAtEnd();
            delayAndRepeat();
        }
    }

    private class TypePauser extends Repeater {

        boolean hasPaused = false;

        public TypePauser(long delay, Runnable doneRunnable) {
            super(doneRunnable, delay);
        }

        @Override
        public void run() {
            if (hasPaused) {
                done();
                return;
            }

            hasPaused = true;
            delayAndRepeat();
        }
    }

    private class TypeRunnable extends Repeater {

        Runnable mRunnable;

        public TypeRunnable(Runnable runnable, Runnable doneRunnable) {
            super(doneRunnable, 0);

            mRunnable = runnable;
        }

        @Override
        public void run() {
            mRunnable.run();
            done();

        }
    }

}