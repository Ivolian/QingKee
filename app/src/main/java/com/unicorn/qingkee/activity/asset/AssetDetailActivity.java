package com.unicorn.qingkee.activity.asset;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.unicorn.qingkee.R;
import com.unicorn.qingkee.activity.base.ToolbarActivity;
import com.unicorn.qingkee.adapter.pager.AssetDetailActivityPagerAdapter;

import butterknife.InjectView;
import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;


// 资产详情基本信息
public class AssetDetailActivity extends ToolbarActivity {

    @InjectView(R.id.materialTabHost)
    MaterialTabHost materialTabHost;

    @InjectView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_asset_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        initToolbar("资产详情", true);
        initViews();
    }

    private void initViews() {

        AssetDetailActivityPagerAdapter pagerAdapter = new AssetDetailActivityPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                materialTabHost.setSelectedNavigationItem(position);
            }
        });
        for (int i = 0; i < pagerAdapter.getCount(); i++) {
            materialTabHost.addTab(
                    materialTabHost.newTab()
                            .setText(pagerAdapter.getPageTitle(i))
                            .setTabListener(new MaterialTabListener() {
                                @Override
                                public void onTabSelected(MaterialTab materialTab) {
                                    viewPager.setCurrentItem(materialTab.getPosition());
                                }

                                @Override
                                public void onTabReselected(MaterialTab materialTab) {
                                }

                                @Override
                                public void onTabUnselected(MaterialTab materialTab) {

                                }
                            })
            );
        }
    }

}
