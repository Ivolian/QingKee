package com.unicorn.qingkee;

import android.os.Bundle;
import android.widget.EditText;

import com.unicorn.qingkee.base.BaseActivity;
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

    @OnClick(R.id.btn_modify)
    public void modifyServerAddress() {

        String newServerAddress = etServerAddress.getText().toString().trim();
        UrlUtils.setServerAddress(newServerAddress);
        SharedPreferencesUtils.putString(UrlUtils.SF_SERVER_ADDRESS, newServerAddress);

        ToastUtils.show("修改成功");
        finish();
    }

}
