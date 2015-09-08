package com.pisces.lau.wishstar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Liu Wenyue on 2015/7/20.
 * Company: New Thread Android
 * Email: liuwenyueno2@gmail.com
 */
public class SettingActivity extends AppCompatActivity {
    //设置界面
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingFragment()).commit();


    }


}

