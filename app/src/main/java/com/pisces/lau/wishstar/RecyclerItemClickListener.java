package com.pisces.lau.wishstar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Liu Wenyue on 2015/9/6.
 * Company: New Thread Android
 * Email: liuwenyueno2@gmail.com
 */
public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener{
    //定义接口,声明接口.
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    GestureDetector mGestureDetector;

    public RecyclerItemClickListener(Context context,OnItemClickListener listener){
        mListener =listener;
        mGestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View childView=rv.findChildViewUnder(e.getX(),e.getY());
        if (childView!=null&&mListener!=null&&mGestureDetector.onTouchEvent(e)){
            mListener.onItemClick(childView,rv.getChildLayoutPosition(childView));
            return true;
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
