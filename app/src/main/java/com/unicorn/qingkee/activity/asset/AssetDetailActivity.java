package com.unicorn.qingkee.activity.asset;

import android.os.Bundle;

import com.unicorn.qingkee.R;
import com.unicorn.qingkee.activity.base.ToolbarActivity;
import com.unicorn.qingkee.util.ToastUtils;


public class AssetDetailActivity extends ToolbarActivity {

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_asset_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        initToolbar("资产详情", true);
        ToastUtils.show(getIntent().getStringExtra("barcode"));
    }


}
