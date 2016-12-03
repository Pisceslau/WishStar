package com.pisces.lau.wishstar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Liu Wenyue on 2015/7/15.
 * Company: New Thread Android
 * Email: liuwenyueno2@gmail.com
 */
public class ContentFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.content_fragment, container, false);

        return v;
    }

}
