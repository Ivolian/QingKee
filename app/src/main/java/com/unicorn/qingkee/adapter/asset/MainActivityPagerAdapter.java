package com.unicorn.qingkee.adapter.asset;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.unicorn.qingkee.fragment.asset.ArrivalAssetQueryFragment;
import com.unicorn.qingkee.fragment.asset.AssetApplyFragment;
import com.unicorn.qingkee.fragment.TestFragment;
import com.unicorn.qingkee.fragment.asset.AssetTransferFragment;


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
            case 5:
                return new AssetTransferFragment();
            default:
                return new TestFragment();
        }
    }

}
