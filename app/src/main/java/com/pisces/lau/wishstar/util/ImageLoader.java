package com.pisces.lau.wishstar.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.squareup.okhttp.internal.DiskLruCache;

/**
 * Created by E440 on 2016/3/25.
 * ImageLoader实现
 */
public class ImageLoader {
    private LruCache<String, Bitmap> memoryCache;
    private DiskLruCache diskLruCache;
    private Context context;

    private ImageLoader(Context context) {
        this.context = context;
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 8;
        memoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
            }
        };
      /*  File diskCacheDir = getDiskCacheDir(context, "bitmap");
        if (!diskCacheDir.exists()) {
            diskCacheDir.mkdirs();
        }
        if (getUsableSpace(diskCacheDir) > DISK_CACHE_SIZE) {
            try {
                diskLruCache = DiskLruCache.create(diskCacheDir, 1, 1, DISK_CACHE_SIZE);
                isDiskLruCacheCreated =  true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }
}
