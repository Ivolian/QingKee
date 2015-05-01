package com.unicorn.qingkee.activity.main;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.unicorn.qingkee.R;
import com.unicorn.qingkee.activity.base.BaseActivity;

import butterknife.ButterKnife;


public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        initToolbar("登录");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_login_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.modify_server_address) {
            startActivity(ModifyServerAddressActivity.class);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
