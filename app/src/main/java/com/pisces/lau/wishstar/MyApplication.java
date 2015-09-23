package com.pisces.lau.wishstar;

import android.app.Application;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;

import cn.bmob.v3.Bmob;

/**
 * Created by Liu Wenyue on 2015/7/12.
 * Company: New Thread Android
 * Email: liuwenyueno2@gmail.com
 */
public class MyApplication extends Application {
    private static MyApplication instance;
    public static MyApplication getInstance() {
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this);
        Log.v("SDK Init", "初始化");
        //初始化Bmob SDK
        //使用时请将第二个参数ID为Bmob的服务器端Application ID
        Bmob.initialize(this, AppConstants.BMOB_KEY);
        Log.v("Bmob","初始化");

    }
}
