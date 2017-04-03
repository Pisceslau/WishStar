package com.pisces.lau.wishstar.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by E440 on 2017/3/31.
 */
public class Frequency extends BmobObject {
    private String name;

    private String frequency;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String prequency) {
        this.frequency = prequency;
    }
}
