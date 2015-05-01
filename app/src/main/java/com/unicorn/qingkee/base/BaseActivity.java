package com.unicorn.qingkee.base;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.cengalabs.flatui.FlatUI;
import com.unicorn.qingkee.R;

import butterknife.InjectView;


public class BaseActivity extends ActionBarActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        FlatUI.initDefaultValues(this);
        FlatUI.setDefaultTheme(FlatUI.GRASS);
    }

    protected void initToolbar(String title) {

        toolbar.setTitle(title);
        setSupportActionBar(toolbar);   // 使 toolbar 有效，比如绑定 activity 菜单
        getSupportActionBar().setBackgroundDrawable(FlatUI.getActionBarDrawable(this, FlatUI.GRASS, false));    // 设置 toolbar 背景色
    }

}
