package com.example.jamesljk.project;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;

/*
 * Created by qinwei on 206/12/8.
 */

public class MySeekBar extends SeekBar {
    public MySeekBar(Context context) {
        super(context);
    }

    public MySeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.seekBarStyle);
    }

    public MySeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    /**
     * onTouchEvent 是在 SeekBar 继承的抽象类 AbsSeekBar 里
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        //禁用进度条的拖动事件
        //return super.onTouchEvent(event);
        return false ;
    }
}
