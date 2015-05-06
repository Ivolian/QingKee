package com.unicorn.qingkee.activity.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.cengalabs.flatui.FlatUI;
import com.unicorn.qingkee.R;

import butterknife.ButterKnife;
import butterknife.InjectView;


public abstract class ToolbarActivity extends AppCompatActivity {

    MaterialDialog progressDialog;

    @InjectView(R.id.toolbar)
    protected Toolbar toolbar;

    @InjectView(R.id.toolbar_shadow)
    protected View shadow;

    abstract public int getLayoutResourceId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.e("result", getClass().getName() + " onCreate");

        super.onCreate(savedInstanceState);

        FlatUI.initDefaultValues(this);
        FlatUI.setDefaultTheme(FlatUI.GRASS);

        setContentView(getLayoutResourceId());
        ButterKnife.inject(this);
    }

    protected void initToolbar(String title) {

        initToolbar(title, false);
    }

    protected void initToolbar(String title, boolean displayHomeAsUpEnable) {

        toolbar.setTitle(title);
        setSupportActionBar(toolbar);   // 使 toolbar 有效，比如绑定 activity 菜单
        if (getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(FlatUI.getActionBarDrawable(this, FlatUI.GRASS, false));    // 设置 toolbar 背景色
            getSupportActionBar().setDisplayHomeAsUpEnabled(displayHomeAsUpEnable);
        }
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


    public void showProgressDialog() {

        showProgressDialog("处理中...");
    }

    public void showProgressDialog(String title) {

        if (progressDialog == null) {
            progressDialog = new MaterialDialog.Builder(this)
                    .theme(Theme.LIGHT)
                    .title(title)
                    .content("请稍后...")
                    .cancelable(false)
                    .progress(true, 0)
                    .show();
        } else {
            progressDialog.show();
        }
    }

    public void hideProgressDialog() {

        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    public String getToolbarTitle(){

        return toolbar.getTitle().toString();
    }

}
