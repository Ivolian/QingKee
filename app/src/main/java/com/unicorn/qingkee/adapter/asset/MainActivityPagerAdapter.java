package com.unicorn.qingkee.adapter.asset;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.unicorn.qingkee.fragment.ArrivalAssetQueryFragment;
import com.unicorn.qingkee.fragment.AssetApplyFragment;
import com.unicorn.qingkee.fragment.TestFragment;


public class MainActivityPagerAdapter extends FragmentPagerAdapter {

    private int count;

    public MainActivityPagerAdapter(FragmentManager fm, int count) {
        super(fm);
        this.count = count;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new ArrivalAssetQueryFragment();
            case 1:
                return new AssetApplyFragment();
            default:
                return new TestFragment();
        }
    }

}
