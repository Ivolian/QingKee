package com.unicorn.qingkee.activity.main;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.gc.materialdesign.widgets.SnackBar;
import com.unicorn.qingkee.R;
import com.unicorn.qingkee.activity.base.BaseActivity;
import com.unicorn.qingkee.util.SharedPreferencesUtils;
import com.unicorn.qingkee.util.ToastUtils;
import com.unicorn.qingkee.util.UrlUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class ModifyServerAddressActivity extends BaseActivity {

    @InjectView(R.id.et_server_address)
    EditText etServerAddress;

    // ========================= onCreate ===========================

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_server_address);
        ButterKnife.inject(this);
        initToolbar("修改服务器地址", true);

        etServerAddress.setText(UrlUtils.getServerAddress());
    }

    // ========================= 点击确认修改 ===========================

    private void modifyServerAddress() {

        String serverAddress = etServerAddress.getText().toString().trim();
        UrlUtils.setServerAddress(serverAddress);
        SharedPreferencesUtils.putString(UrlUtils.SF_SERVER_ADDRESS, serverAddress);
        ToastUtils.show("修改成功");
    }

    @OnClick(R.id.btn_modify)
    public void confirm() {

        SnackBar snackbar = new SnackBar(this, "确认修改服务器地址?", "确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyServerAddress();
            }
        });
        snackbar.setIndeterminate(true);
        snackbar.setColorButton(0xff2ab081);
        snackbar.show();
    }

}
