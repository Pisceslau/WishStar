package com.pisces.lau.wishstar.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Liu Wenyue on 2015/7/18.
 * Company: New Thread Android
 * Email: liuwenyueno2@gmail.com
 */
public class Util {
    public static String UTIL_TAG = "UTIL";

    public static Bitmap getBitmap(String imageUri) {
        Log.v(UTIL_TAG, "getbitmap:" + imageUri);

        //显示网络上的图片
        Bitmap bitmap = null;
        try {

            URL myFileUrl = new URL(imageUri);
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();

            Log.v(UTIL_TAG, "image download finished." + imageUri);

        } catch (IOException e) {
            e.printStackTrace();
            Log.v(UTIL_TAG, "getbitmap bmp fail---");
            return null;
        }
        return bitmap;
    }

    /*检查是否存在SdCard*/
    public static boolean hasSdCard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }


}
