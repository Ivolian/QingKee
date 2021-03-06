package com.unicorn.qingkee.activity.main;

import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.unicorn.qingkee.MyApplication;
import com.unicorn.qingkee.R;
import com.unicorn.qingkee.activity.base.ToolbarActivity;
import com.unicorn.qingkee.bean.UserInfo;
import com.unicorn.qingkee.util.JSONUtils;
import com.unicorn.qingkee.util.SharedPreferencesUtils;
import com.unicorn.qingkee.util.ToastUtils;
import com.unicorn.qingkee.util.UrlUtils;
import com.unicorn.qingkee.volley.MyVolley;
import com.unicorn.qingkee.volley.toolbox.VolleyErrorHelper;

import org.json.JSONObject;

import butterknife.InjectView;
import butterknife.OnClick;


public class LoginActivity extends ToolbarActivity {

    final String SF_LOGIN_CODE = "login_code";

    final String SF_REMEMBER_ME = "remember_me";

    // ========================= views ===========================

    @InjectView(R.id.et_login_code)
    EditText etLoginCode;

    @InjectView(R.id.et_login_password)
    EditText etLoginPassword;

    @InjectView(R.id.cb_remember_me)
    CheckBox cbRememberMe;

    // ========================= onCreate ===========================

    @Override
    public int getLayoutResourceId() {

        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        initToolbar("登录");

        restoreSharedPreferencesInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_login_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.modify_server_address) {
            startActivity(ModifyServerAddressActivity.class);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // ========================= 点击登录按钮 ===========================

    @OnClick(R.id.btn_login)
    public void login() {
        showProgressDialog("登录中...");
        MyVolley.getRequestQueue().add(new JsonObjectRequest(getUrl(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideProgressDialog();
                        int result = JSONUtils.getInt(response, "Result", 1);
                        if (result != 0) {
                            // 0表示成功，非0则显示Msg
                            ToastUtils.show(JSONUtils.getString(response, "Msg", ""));
                        } else {
                            storeSharedPreferencesInfo();
                            // 全局化 UserInfo
                            MyApplication.getInstance().setUserInfo(UserInfo.parse(JSONUtils.getJSONObject(response, "UserInfo", null)));
                            startActivity(SelectActivity.class);
                            finish();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        hideProgressDialog();
                        ToastUtils.show(VolleyErrorHelper.getErrorMessage(volleyError));
                    }
                }));
    }

    private String getUrl() {

        Uri.Builder builder = Uri.parse(UrlUtils.getBaseUrl() + "/login?").buildUpon();
        builder.appendQueryParameter("logincode", etLoginCode.getText().toString().trim());
        builder.appendQueryParameter("loginpwd", etLoginPassword.getText().toString().trim());
        return builder.toString();
    }

    // ========================= 处理 SharedPreferences ===========================

    private void restoreSharedPreferencesInfo() {

        cbRememberMe.setChecked(SharedPreferencesUtils.getBoolean(SF_REMEMBER_ME));
        if (cbRememberMe.isChecked()) {
            etLoginCode.setText(SharedPreferencesUtils.getString(SF_LOGIN_CODE));
        }
    }

    private void storeSharedPreferencesInfo() {

        SharedPreferencesUtils.putBoolean(SF_REMEMBER_ME, cbRememberMe.isChecked());
        SharedPreferencesUtils.putString(SF_LOGIN_CODE, etLoginCode.getText().toString());
    }

}
