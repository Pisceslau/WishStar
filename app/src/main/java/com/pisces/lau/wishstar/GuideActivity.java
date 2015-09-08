package com.pisces.lau.wishstar;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pisces.lau.wishstar.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liu Wenyue on 2015/7/25.
 * Company: New Thread Android
 * Email: liuwenyueno2@gmail.com
 */
public class GuideActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ViewPager vp;
    private ViewPagerAdapter vpAdapter;
    private List<View> views;

    //引导图片资源
    private static final int[] pics = {R.drawable.star1, R.drawable.star2, R.drawable.star3, R.drawable.star4};
    //底部小点图片
    private ImageView[] dots;
    //记录当前选中位置
    private int currentIndex;
    /*调用当activity第一次创建*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_layout);

        //偏好设置
        PreferenceManager.setDefaultValues(this,R.xml.preferences,false);

        views = new ArrayList<>();

        LinearLayout.LayoutParams mParams = new LinearLayout.
                LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        //初始化引导图片列表
        for (int pic : pics) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(mParams);
            iv.setImageResource(pic);
            views.add(iv);
        }
        vp = (ViewPager) findViewById(R.id.guide_viewpager);
        //初始化Adapter
        vpAdapter = new ViewPagerAdapter(views);
        vp.setAdapter(vpAdapter);
        //绑定回调
        vp.addOnPageChangeListener(this);

        //初始化底部小点
        initDots();

    }

    private void initDots() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);

        dots = new ImageView[pics.length];

        //循环取得小点图片
        for (int i = 0; i < pics.length; i++) {
            dots[i] = (ImageView) ll.getChildAt(i);
            dots[i].setEnabled(true);//都设为灰色
            dots[i].setOnClickListener(this);
            dots[i].setTag(i);//设置位置tag,方便取出与当前位置对应
        }
        currentIndex = 0;
        dots[currentIndex].setEnabled(false);//设置为白色,即选中状态
    }

    /*设置当前的引导页*/
    private void setCurView(int position) {
        if (position < 0 || position >= pics.length) {
            return;
        }
        vp.setCurrentItem(position);
    }

    /*这只当前引导小点的选中*/
    private void setCurDot(int position) {
        if (position < 0 || position > pics.length - 1 || currentIndex == position) {
            return;
        }
        dots[position].setEnabled(false);
        dots[currentIndex].setEnabled(true);

        currentIndex = position;
    }
    //当滑动状态改变时候调用

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    //当当前页面被滑动时候调用

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


    }
    //当新的页面被选中时候调用

    @Override
    public void onPageSelected(int position) {
        //设置底部小点选中状态
        setCurDot(position);
        if (position == vp.getAdapter().getCount() - 1) {
            goToLoginActivity();

        }

    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        setCurView(position);
        setCurDot(position);
    }


   /* @Override
    public boolean dispatchTouchEvent(@NonNull MotionEvent ev) {

        if (gestureDetector.onTouchEvent(ev)) {

            ev.setAction(MotionEvent.ACTION_CANCEL);

        }

        return super.dispatchTouchEvent(ev);
    }*/

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //当按返回键时候,转到LoginActivity
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goToLoginActivity();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void goToLoginActivity() {

        Intent i = new Intent(GuideActivity.this, LoginActivity.class);
        startActivity(i);
        finish();

    }
}

