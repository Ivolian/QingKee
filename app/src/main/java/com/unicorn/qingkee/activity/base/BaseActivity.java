package com.unicorn.qingkee.activity.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.cengalabs.flatui.FlatUI;
import com.unicorn.qingkee.R;

import butterknife.InjectView;


public abstract class BaseActivity extends ActionBarActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        FlatUI.initDefaultValues(this);
        FlatUI.setDefaultTheme(FlatUI.GRASS);
    }

    protected void initToolbar(String title) {

        initToolbar(title, false);
    }

    protected void initToolbar(String title, boolean displayHomeAsUpEnable) {

        toolbar.setTitle(title);
        setSupportActionBar(toolbar);   // 使 toolbar 有效，比如绑定 activity 菜单
        getSupportActionBar().setBackgroundDrawable(FlatUI.getActionBarDrawable(this, FlatUI.GRASS, false));    // 设置 toolbar 背景色
        getSupportActionBar().setDisplayHomeAsUpEnabled(displayHomeAsUpEnable);
    }

    protected void startActivity(Class activityClass) {

        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
