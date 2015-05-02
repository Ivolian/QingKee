package com.unicorn.qingkee.adaper.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.unicorn.qingkee.fragment.TestFragment;


public class MainActivityPagerAdapter extends FragmentPagerAdapter {

    private final int FRAGMENT_COUNT = 3;

    public MainActivityPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }

    @Override
    public Fragment getItem(int position) {

        return new TestFragment();
    }

}
