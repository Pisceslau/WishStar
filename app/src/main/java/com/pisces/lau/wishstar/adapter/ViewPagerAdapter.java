package com.pisces.lau.wishstar.adapter;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Liu Wenyue on 2015/7/25.
 * Company: New Thread Android
 * Email: liuwenyueno2@gmail.com
 */
/*
* Go to previous/next method	ALT + UP/DOWN
Show parameters for method	CTRL + P
Quick documentation lookup	CTRL + Q*/
public class ViewPagerAdapter extends PagerAdapter {
    //界面列表
    private List<View> views;

    public ViewPagerAdapter(List<View> views) {
        this.views = views;
    }

    //销毁position的位置的界面
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    @Override
    public void finishUpdate(ViewGroup container) {

    }
    //获得当前界面数

    @Override
    public int getCount() {
        if (views != null) {
            return views.size();
        }
        return 0;

    }
    //初始化position位置的界面

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //第一个参数为子视图,第二个参数为子视图添加到的位置
        container.addView(views.get(position),0);
        return views.get(position);
    }

    //判断是否由对象生成界面,检查由instantiateItem
    // 返回的对象是否与页面视图相关联
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        super.restoreState(state, loader);
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate(ViewGroup container) {

    }

}
