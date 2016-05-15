package com.pisces.lau.wishstar.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by E440 on 2016/5/15.
 * 用户发送给圈子的信息
 */
public class Message extends BmobObject {
    private String message;
    private String username;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
