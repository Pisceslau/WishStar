package com.pisces.lau.wishstar;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;

/**
 * Created by Liu Wenyue on 2015/8/9.
 * Company: New Thread Android
 * Email: liuwenyueno2@gmail.com
 */
public class SettingFragment extends PreferenceFragment {
    EditTextPreference editTextPreference;//对应Preference的输入文本框
    private String TAG = "SettingFragment";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //载入Preferences.xml

        addPreferencesFromResource(R.xml.preferences);
        editTextPreference = (EditTextPreference)findPreference("pref_nickname");

    }

    @Override
    public void onResume() {
        super.onResume();
        editTextPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                return false;
            }
        });
        editTextPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String nickname = editTextPreference.getText();//得到更改后的值?
                Log.d(TAG, nickname);
                return false;
            }
        });

    }
}