package com.pisces.lau.wishstar;

import android.os.Bundle;

import com.pisces.lau.wishstar.util.BaseActivity;

/**
 * Created by Liu Wenyue on 2015/8/6.
 * Company: New Thread Android
 * Email: liuwenyueno2@gmail.com
 */
public class EditUserInfoActivity extends BaseActivity{
    //编辑用户信息Activity,修改完云端和本界面前界面也改变

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //findViewById()
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.edit_user_info_layout;
    }
}
