package com.unicorn.qingkee.activity.main;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;

import com.unicorn.qingkee.R;
import com.unicorn.qingkee.activity.base.ToolbarActivity;
import com.unicorn.qingkee.adapter.pager.MainActivityPagerAdapter;
import com.unicorn.qingkee.fragment.other.SideMenuFragment;
import com.unicorn.qingkee.util.ToastUtils;

import butterknife.InjectView;
import butterknife.OnPageChange;


public class MainActivity extends ToolbarActivity {

    public final String[] FRAGMENT_TITLES = {
            "到货查询", "调拨出库", "调拨入库", "资产领用", "资产盘点",
            "资产转移", "资产报废", "维修入库", "维修出库"
    };

    @InjectView(R.id.viewpager)
    public ViewPager viewPager;

    @InjectView(R.id.drawer)
    public DrawerLayout drawerLayout;

    SideMenuFragment sideMenuFragment;

    public int currentItem;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.e("result", getClass().getName() + currentItem);


        super.onCreate(savedInstanceState);
        Log.e("result", getClass().getName() + currentItem);

        initToolbar(FRAGMENT_TITLES[getIntent().getIntExtra("fragmentIndex", 0)]);

        currentItem = getIntent().getIntExtra("fragmentIndex", 0);
            sideMenuFragment = new SideMenuFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.side_menu_fragment, sideMenuFragment).commit();

        initDrawerLayout();
        initViewPager();
    }

    private void initDrawerLayout() {

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        actionBarDrawerToggle.syncState();
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
    }

    private void initViewPager() {

        viewPager.setOffscreenPageLimit(FRAGMENT_TITLES.length);
        viewPager.setAdapter(new MainActivityPagerAdapter(getSupportFragmentManager(), FRAGMENT_TITLES.length));
        viewPager.setCurrentItem(currentItem);
    }

    @OnPageChange(value = R.id.viewpager, callback = OnPageChange.Callback.PAGE_SELECTED)
    public void changeToolbarTitle(int position) {

        toolbar.setTitle(FRAGMENT_TITLES[position]);
        currentItem = position;
        sideMenuFragment.notifyDataSetChanged();
    }

    public void onSideMenuItemClick(int position) {

        drawerLayout.closeDrawers();
        viewPager.setCurrentItem(position, false);
    }

}
