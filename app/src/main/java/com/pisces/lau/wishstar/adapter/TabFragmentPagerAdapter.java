package com.pisces.lau.wishstar.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.pisces.lau.wishstar.DetailedInfoFragment;
import com.pisces.lau.wishstar.HomepageFragment;

/**
 * Created by Liu Wenyue on 2015/8/17.
 * Company: New Thread Android
 * Email: liuwenyueno2@gmail.com
 */
public class TabFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT=2;//page页码数目
    private String tabTitles[] = new String[]{"个人主页","详细信息"};

    public TabFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HomepageFragment();
            case 1:
                return new DetailedInfoFragment();
            default:
                return new HomepageFragment();
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
