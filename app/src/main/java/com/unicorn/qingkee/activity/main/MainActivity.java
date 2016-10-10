package com.unicorn.qingkee.activity.main;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;

import com.unicorn.qingkee.R;
import com.unicorn.qingkee.activity.base.ToolbarActivity;
import com.unicorn.qingkee.adapter.list.SideMenuAdapter;
import com.unicorn.qingkee.adapter.pager.MainActivityPagerAdapter;

import butterknife.InjectView;


public class MainActivity extends ToolbarActivity {

    public final String[] FRAGMENT_TITLES = {
            "到货查询", "调拨出库", "调拨入库", "资产领用", "资产盘点",
            "资产转出", "资产转入", "维修入库", "维修出库", "资产报废"
    };

    @InjectView(R.id.viewpager)
    public ViewPager viewPager;

    @InjectView(R.id.drawer)
    public DrawerLayout drawerLayout;

    ActionBarDrawerToggle actionBarDrawerToggle;

    @InjectView(R.id.side_menu)
    RecyclerView sideMenu;

    SideMenuAdapter sideMenuAdapter;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        initToolbar(FRAGMENT_TITLES[getIntent().getIntExtra("position", 0)]);

        initDrawerLayout();
        initViewPager();
        initSideMenu();
    }

    private void initDrawerLayout() {

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
    }

    private void initViewPager() {

        viewPager.setOffscreenPageLimit(FRAGMENT_TITLES.length);
        viewPager.setAdapter(new MainActivityPagerAdapter(getSupportFragmentManager(), FRAGMENT_TITLES.length));
        viewPager.setCurrentItem(getIntent().getIntExtra("position", 0));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                toolbar.setTitle(FRAGMENT_TITLES[position]);
                sideMenuAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initSideMenu() {

        sideMenu.setLayoutManager(getLinearLayoutManager());
        sideMenuAdapter = new SideMenuAdapter(this);
        sideMenu.setAdapter(sideMenuAdapter);
    }

    public void onSideMenuItemClick(int position) {

        drawerLayout.closeDrawers();
        viewPager.setCurrentItem(position, false);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawers();
            return;
        }
        super.onBackPressed();
    }

    private LinearLayoutManager getLinearLayoutManager() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return linearLayoutManager;
    }

}
