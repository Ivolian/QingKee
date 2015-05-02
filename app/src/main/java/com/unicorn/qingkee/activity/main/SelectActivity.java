package com.unicorn.qingkee.activity.main;

import android.os.Bundle;
import android.os.Handler;

import com.unicorn.qingkee.R;
import com.unicorn.qingkee.activity.base.ToolbarActivity;

import butterknife.OnClick;


public class SelectActivity extends ToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        initToolbar("青客资产管理");
    }

    @Override
    public int getLayoutResourceId() {

        return R.layout.activity_select;
    }

    @OnClick(R.id.asset_add)
    public void onClick() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(MainActivity.class);
            }
        }, 1000);
    }

}
