package com.pisces.lau.wishstar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Liu Wenyue on 2015/8/5.
 * Company: New Thread Android
 * Email: liuwenyueno2@gmail.com
 */
public class ComposeActivity extends AppCompatActivity {
    /*撰写愿望并发布(到愿望大厅)模块*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compose_layout);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right,R.anim.right_to_left);
    }
}
