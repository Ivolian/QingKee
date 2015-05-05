package com.unicorn.qingkee.adapter.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.unicorn.qingkee.fragment.asset.AssetDetailFragmentFirst;
import com.unicorn.qingkee.fragment.asset.AssetDetailFragmentSecond;


// todo
public class AssetDetailActivityPagerAdapter extends FragmentStatePagerAdapter {

    public AssetDetailActivityPagerAdapter(FragmentManager fm) {
        super(fm);

    }

    public Fragment getItem(int num) {

        return num == 0 ? new AssetDetailFragmentFirst() : new AssetDetailFragmentSecond();
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