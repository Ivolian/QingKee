package com.unicorn.qingkee.activity.asset;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.unicorn.qingkee.R;
import com.unicorn.qingkee.activity.base.ToolbarActivity;
import com.unicorn.qingkee.adapter.pager.AssetDetailActivityPagerAdapter;
import com.unicorn.qingkee.bean.Asset;

import butterknife.InjectView;
import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;


public class AssetDetailActivity extends ToolbarActivity {

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_asset_detail;
    }

    @InjectView(R.id.viewPager)
    ViewPager viewPager;

    @InjectView(R.id.materialTabHost)
    MaterialTabHost materialTabHost;

    public Asset asset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        asset = (Asset) getIntent().getSerializableExtra("asset");
        initToolbar("资产详情", true);
        initViews();
    }

    private void initViews() {

        AssetDetailActivityPagerAdapter adapter = new AssetDetailActivityPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                materialTabHost.setSelectedNavigationItem(position);
            }
        });

        for (int i = 0; i < adapter.getCount(); i++) {
            materialTabHost.addTab(
                    materialTabHost.newTab()
                            .setText(adapter.getPageTitle(i))
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
