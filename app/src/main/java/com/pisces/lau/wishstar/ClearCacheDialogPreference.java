package com.pisces.lau.wishstar;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;

/**
 * Created by Liu Wenyue on 2015/8/7.
 * Company: New Thread Android
 * Email: liuwenyueno2@gmail.com
 */
public class ClearCacheDialogPreference extends DialogPreference {
    public ClearCacheDialogPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        persistBoolean(positiveResult);
    }
}
