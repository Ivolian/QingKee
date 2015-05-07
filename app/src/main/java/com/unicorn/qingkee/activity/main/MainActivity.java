package com.unicorn.qingkee.activity.main;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;

import com.unicorn.qingkee.R;
import com.unicorn.qingkee.activity.base.ToolbarActivity;
import com.unicorn.qingkee.adapter.pager.MainActivityPagerAdapter;
import com.unicorn.qingkee.fragment.other.SideMenuFragment;

import butterknife.InjectView;


public class MainActivity extends ToolbarActivity {

    public final String[] FRAGMENT_TITLES = {
            "到货查询", "调拨出库", "调拨入库", "资产领用", "资产盘点",
            "资产转移", "资产报废", "维修入库", "维修出库"
    };

    @InjectView(R.id.viewpager)
    public ViewPager viewPager;

    @InjectView(R.id.drawer)
    public DrawerLayout drawerLayout;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.side_menu_fragment, new SideMenuFragment()).commit();
        }

        initToolbar(FRAGMENT_TITLES[getIntent().getIntExtra("position", 0)]);
        initDrawerLayout();
        initViewPager();
    }

    private void initDrawerLayout() {

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        actionBarDrawerToggle.syncState();
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
                ((SideMenuFragment) getSupportFragmentManager().findFragmentById(R.id.side_menu_fragment)).notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void onSideMenuItemClick(int position) {

        drawerLayout.closeDrawers();
        viewPager.setCurrentItem(position, false);
    }

}
