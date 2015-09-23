package com.pisces.lau.wishstar.util;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.pisces.lau.wishstar.R;

/**
 * Created by Liu Wenyue on 2015/8/6.
 * Company: New Thread Android
 * Email: liuwenyueno2@gmail.com
 */
public abstract class BaseActivity extends AppCompatActivity {
    //BaseActivity,可被其他Activity继承的Activity,写共同布局,如toolbar.
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
        toolbar = (Toolbar) findViewById(R.id.toolbar);
      //  swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);


            }
        }
        //SwipeRefreshLayout配置
     //   swipeRefresh.setColorSchemeColors(R.color.md_orange_400, R.color.md_light_blue_500, R.color.md_red_A200, R.color.md_purple_500);
    }

    //子类需要实现(implements的抽象方法)
    protected abstract int getLayoutResourceId();
}
