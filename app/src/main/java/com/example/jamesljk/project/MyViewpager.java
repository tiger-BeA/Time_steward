package com.example.jamesljk.project;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by qinwei on 2016/12/8.
 */

public class MyViewpager extends ViewPager {
    private boolean DISABLE=false;
    public MyViewpager(Context context){
        super(context);
    }
    public MyViewpager(Context context, AttributeSet attrs){
        super(context,attrs);
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return DISABLE&&super.onInterceptTouchEvent(arg0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        return DISABLE&&super.onTouchEvent(arg0);
    }
}
