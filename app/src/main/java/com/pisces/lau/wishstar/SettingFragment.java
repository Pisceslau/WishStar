package com.pisces.lau.wishstar;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by Liu Wenyue on 2015/8/9.
 * Company: New Thread Android
 * Email: liuwenyueno2@gmail.com
 */
public class SettingFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //载入Preferences.xml

        addPreferencesFromResource(R.xml.preferences);

    }
}
