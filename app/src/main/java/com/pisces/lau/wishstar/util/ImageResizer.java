package com.pisces.lau.wishstar.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * Created by E440 on 2016/3/25.
 * 图片压缩功能、裁剪
 */
public class ImageResizer {
    private static final String TAG = "ImageResizer";

    public ImageResizer() {

    }

    /*使用BitmapFactory.Options的inSampleSize,inJustDecodeBounds属性实现图片高效加载*/
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        //将BitmapFactory.Options的inJustDecodeBounds设置为true，获得其宽高信息
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        //计算采样率
        options.inSampleSize = calculateSampleSize(options, reqWidth, reqHeight);
        //把inJustDecodeBounds设置为false,完全加载图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    private static int calculateSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        //获得图片的原始宽和高
        if (reqWidth == 0 || reqHeight == 0) {
            return 1;
        }
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize >= reqWidth)) {
                inSampleSize *= 2;
            }
        }
        Log.d(TAG, "sampleSize:" + inSampleSize);
        return inSampleSize;
    }
}
