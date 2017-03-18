package com.example.jamesljk.project;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by qinwei on 2016/12/6.
 */

public class MyPagerAdapter extends PagerAdapter {
    private List<View> pageList;

    public MyPagerAdapter(List<View> pageList){
        this.pageList = pageList;
    }

    @Override
    public int getCount(){
        return pageList.size();
    }


    @Override
    public boolean isViewFromObject(View arg0, Object arg1){
        return arg0 == arg1;
    }
    @Override
    public void destroyItem(ViewGroup container,int position,Object object){
        container.removeView(pageList.get(position));
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {  //这个方法用来实例化页卡
        container.addView(pageList.get(position), 0);//添加页卡
        return pageList.get(position);
    }
}
