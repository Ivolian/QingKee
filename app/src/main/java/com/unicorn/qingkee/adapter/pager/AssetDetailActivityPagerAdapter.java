package com.unicorn.qingkee.adapter.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.unicorn.qingkee.fragment.TestFragment;
import com.unicorn.qingkee.fragment.asset.AssetDetailFragment;


// todo
public class AssetDetailActivityPagerAdapter extends FragmentStatePagerAdapter {

    public AssetDetailActivityPagerAdapter(FragmentManager fm) {
        super(fm);

    }

    public Fragment getItem(int num) {

        if (num == 0) {
            return new AssetDetailFragment();
        }
        // todo
        return new TestFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return position == 0 ? "基本信息" : "照片";
    }

}