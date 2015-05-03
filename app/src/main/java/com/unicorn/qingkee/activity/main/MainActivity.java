package com.unicorn.qingkee.activity.main;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;

import com.unicorn.qingkee.R;
import com.unicorn.qingkee.activity.base.ToolbarActivity;
import com.unicorn.qingkee.adapter.asset.MainActivityPagerAdapter;

import butterknife.InjectView;


public class MainActivity extends ToolbarActivity {

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
        initToolbar("青客资产管理");
        initDrawerLayout();
        viewPager.setAdapter(new MainActivityPagerAdapter(getSupportFragmentManager()));
    }

    private void initDrawerLayout() {

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        actionBarDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);
    }

}
