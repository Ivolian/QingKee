package com.unicorn.qingkee.activity.main;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;

import com.gc.materialdesign.widgets.SnackBar;
import com.unicorn.qingkee.R;
import com.unicorn.qingkee.activity.base.ToolbarActivity;
import com.unicorn.qingkee.util.SharedPreferencesUtils;
import com.unicorn.qingkee.util.ToastUtils;
import com.unicorn.qingkee.util.UrlUtils;

import butterknife.InjectView;
import butterknife.OnClick;


public class ModifyServerAddressActivity extends ToolbarActivity {

    @InjectView(R.id.et_server_address)
    EditText etServerAddress;

    SnackBar snackbar;

    // ========================= onCreate ===========================


    @Override
    public int getLayoutResourceId() {

        return R.layout.activity_modify_server_address;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        initToolbar("修改服务器地址", true);

        etServerAddress.setText(UrlUtils.getServerAddress());
        initSnackBar();
    }

    private void initSnackBar() {

        snackbar = new SnackBar(this, "确认修改服务器地址?", "确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyServerAddress();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 800);
            }
        });
        snackbar.setIndeterminate(true);
        snackbar.setColorButton(0xff2ab081);
    }

    @OnClick(R.id.btn_modify)
    public void confirm() {

        if (!snackbar.isShowing()) {
            snackbar.show();
        }
    }

    private void modifyServerAddress() {

        String serverAddress = etServerAddress.getText().toString().trim();
        UrlUtils.setServerAddress(serverAddress);
        SharedPreferencesUtils.putString(UrlUtils.SF_SERVER_ADDRESS, serverAddress);
        ToastUtils.show("修改成功");
    }

}
