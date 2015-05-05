package com.unicorn.qingkee.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.unicorn.qingkee.R;
import com.unicorn.qingkee.activity.base.ToolbarActivity;
import com.unicorn.qingkee.util.ToastUtils;


public class SelectActivity extends ToolbarActivity {

    int[] viewIdS = {
            R.id.asset_add, R.id.asset_allot_out, R.id.asset_allot_in,
            R.id.asset_apply, R.id.asset_inventory, R.id.asset_transfer,
            R.id.asset_abandon, R.id.asset_repair_in, R.id.asset_repair_out
    };

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_select;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        initToolbar("青客资产管理");
        initViews();
    }

    private void initViews() {

        for (int i = 0, length = viewIdS.length; i != length; i++) {
            final int index = i;
            findViewById(viewIdS[index]).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startMainActivity(index);
                        }
                    }, 800);
                }
            });
        }
    }

    private void startMainActivity(int fragmentIndex) {

        Intent intent = new Intent(SelectActivity.this, MainActivity.class);
        intent.putExtra("fragmentIndex", fragmentIndex);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_select_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.asset_detail) {
            // todo
            ToastUtils.show("todo");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // ==================== 再按一次退出 ====================

    private long exitTime;

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtils.show("再按一次退出");
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

}
