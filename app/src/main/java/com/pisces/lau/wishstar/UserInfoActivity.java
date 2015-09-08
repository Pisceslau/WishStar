package com.pisces.lau.wishstar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.pisces.lau.wishstar.adapter.TabFragmentPagerAdapter;
import com.pisces.lau.wishstar.util.BaseActivity;

/**
 * Created by Liu Wenyue on 2015/8/6.
 * Company: New Thread Android
 * Email: liuwenyueno2@gmail.com
 */
public class UserInfoActivity extends BaseActivity {
    //用户信息,如头像邮箱等设置,点击右上角的toolbar icon 进入到EditUserInfoActivity
    TabLayout tabLayout;
    ViewPager viewPager;

    public static String POSITION = "POSITION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // do extra stuff on your resources, using findViewById on your layout for the activity
        //findViewById();
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new TabFragmentPagerAdapter(getSupportFragmentManager()));

        //Give the TabLayout the ViewPager
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);



    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState .putInt(POSITION,tabLayout.getSelectedTabPosition());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        viewPager.setCurrentItem(savedInstanceState.getInt(POSITION));
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.user_info_layout;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user_info, menu);
        return super.onCreateOptionsMenu(menu);

    }*/

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
     /*   int id = item.getItemId();*/
     /*   switch (id) {
            //case R.id.:
            //return true;
            //case R.id.:
        }*/
        return super.onOptionsItemSelected(item);
    }
}
