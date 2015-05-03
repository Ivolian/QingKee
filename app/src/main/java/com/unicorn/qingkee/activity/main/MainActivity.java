package com.unicorn.qingkee.activity.main;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;

import com.unicorn.qingkee.R;
import com.unicorn.qingkee.activity.base.ToolbarActivity;
import com.unicorn.qingkee.adapter.asset.MainActivityPagerAdapter;

import butterknife.InjectView;
import butterknife.OnPageChange;


public class MainActivity extends ToolbarActivity {

    final String[] FRAGMENT_TITLES = {
            "到货查询", "调拨出库", "调拨入库", "资产领用", "资产盘点",
            "资产转移", "资产报废", "维修出库", "维修入库"
    };

    @InjectView(R.id.viewpager)
    ViewPager viewPager;

    @InjectView(R.id.drawer)
    DrawerLayout mDrawerLayout;

    @Override
    public int getLayoutResourceId() {

        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        initToolbar(FRAGMENT_TITLES[getIntent().getIntExtra("fragmentIndex", 0)]);

        initDrawerLayout();
        initViewPager();
    }

    private void initDrawerLayout() {

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        actionBarDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);
    }

    private void initViewPager() {

        viewPager.setAdapter(new MainActivityPagerAdapter(getSupportFragmentManager(), FRAGMENT_TITLES.length));
        viewPager.setCurrentItem(getIntent().getIntExtra("fragmentIndex", 0));
    }

    @OnPageChange(value = R.id.viewpager, callback = OnPageChange.Callback.PAGE_SELECTED)
    public void changeToolbarTitle(int position) {

        mToolbar.setTitle(FRAGMENT_TITLES[position]);
//        getSupportActionBar().setTitle(FRAGMENT_TITLES[position]);
    }

}
