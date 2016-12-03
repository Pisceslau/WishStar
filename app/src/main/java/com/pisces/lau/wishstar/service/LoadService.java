package com.pisces.lau.wishstar.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Liu Wenyue on 2016/5/15.
 * Company: New Thread Android
 * Email: liuwenyueno2@gmail.com
 */
public class LoadService extends Service {
    private LoadBinder binder = new LoadBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
