package com.pisces.lau.wishstar.bean;

import android.media.Image;

import cn.bmob.v3.BmobUser;

/**
 * Created by Liu Wenyue on 2015/7/16.
 * Company: New Thread Android
 * Email: liuwenyueno2@gmail.com
 */
public class User extends BmobUser {

 /*   private String userName;
    private String userPassword;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }*/
 private Image profile;

    public Image getProfile() {
        return profile;
    }

    public void setProfile(Image profile) {
        this.profile = profile;
    }
}
