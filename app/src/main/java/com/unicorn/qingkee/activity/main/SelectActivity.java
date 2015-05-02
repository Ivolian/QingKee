package com.unicorn.qingkee.activity.main;

import android.os.Bundle;

import com.unicorn.qingkee.R;
import com.unicorn.qingkee.activity.base.ScrollableActivity;
import com.unicorn.qingkee.util.ToastUtils;

import butterknife.OnClick;


public class SelectActivity extends ScrollableActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        initToolbar("青客资产管理");

        mStickyToolbar = false;
    }

    @Override
    public int getLayoutResourceId() {

        return R.layout.activity_select;
    }

    @OnClick(R.id.asset_add)
    public void onClick(){
        ToastUtils.show("hehe");
    }

}
