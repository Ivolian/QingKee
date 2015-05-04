package com.unicorn.qingkee.adapter.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.unicorn.qingkee.fragment.asset.ArrivalAssetQueryFragment;
import com.unicorn.qingkee.fragment.asset.AssetAbandonFragment;
import com.unicorn.qingkee.fragment.asset.AssetAllotInFragment;
import com.unicorn.qingkee.fragment.asset.AssetAllotOutFragment;
import com.unicorn.qingkee.fragment.asset.AssetApplyFragment;
import com.unicorn.qingkee.fragment.asset.AssetInventoryListFragment;
import com.unicorn.qingkee.fragment.asset.AssetRepairInFragment;
import com.unicorn.qingkee.fragment.asset.AssetRepairOutFragment;
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
                return new AssetAllotOutFragment();
            case 2:
                return new AssetAllotInFragment();
            case 3:
                return new AssetApplyFragment();
            case 4:
                return new AssetInventoryListFragment();
            case 5:
                return new AssetTransferFragment();
            case 6:
                return new AssetAbandonFragment();
            case 7:
                return new AssetRepairInFragment();
            case 8:
                return new AssetRepairOutFragment();
            default:
                return null;
        }
    }

}
