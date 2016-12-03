package com.pisces.lau.wishstar.bean;

import android.graphics.Bitmap;

import cn.bmob.v3.BmobUser;

/**
 * Created by Liu Wenyue on 2015/7/16.
 * Company: New Thread Android
 * Email: liuwenyueno2@gmail.com
 */
public class User extends BmobUser {

 private Bitmap profile;

    public Bitmap getProfile() {
        return profile;
    }

    public void setProfile(Bitmap profile) {
        this.profile = profile;
    }
}
