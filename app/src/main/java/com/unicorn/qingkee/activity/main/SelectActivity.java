package com.unicorn.qingkee.activity.main;

import android.os.Bundle;
import com.unicorn.qingkee.R;
import com.unicorn.qingkee.activity.base.ScrollableActivity;

import butterknife.ButterKnife;


public class SelectActivity extends ScrollableActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        ButterKnife.inject(this);
        initToolbar("青客资产管理");
        observableScrollView.setScrollViewCallbacks(this);
        enable = false;
    }

}
