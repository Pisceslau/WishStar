package com.pisces.lau.wishstar;

import android.os.Bundle;

import com.pisces.lau.wishstar.util.BaseActivity;

/**
 * Created by Liu Wenyue on 2015/7/20.
 * Company: New Thread Android
 * Email: liuwenyueno2@gmail.com
 */
public class SettingActivity extends BaseActivity {
    //设置界面
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getFragmentManager().beginTransaction().replace(R.id.setting_frame, new SettingFragment()).commit();


    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.setting_layout;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }
}

