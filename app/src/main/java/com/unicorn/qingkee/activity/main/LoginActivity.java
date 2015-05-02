package com.unicorn.qingkee.activity.main;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;
import com.unicorn.qingkee.R;
import com.unicorn.qingkee.activity.base.BaseActivity;
import com.unicorn.qingkee.util.JSONUtils;
import com.unicorn.qingkee.util.SharedPreferencesUtils;
import com.unicorn.qingkee.util.ToastUtils;
import com.unicorn.qingkee.util.UrlUtils;
import com.unicorn.qingkee.volley.MyVolley;

import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class LoginActivity extends BaseActivity implements ObservableScrollViewCallbacks {

    final String SF_LOGIN_CODE = "login_code";

    final String SF_REMEMBER_ME = "remember_me";

    MaterialDialog progressDialog;

    // ========================= views ===========================

    @InjectView(R.id.et_login_code)
    EditText etLoginCode;

    @InjectView(R.id.et_login_password)
    EditText etLoginPassword;

    @InjectView(R.id.cb_remember_me)
    CheckBox cbRememberMe;


    @InjectView(R.id.scroll)
    ObservableScrollView scrollView;

    @InjectView(R.id.toolbar_shadow)
    View shadow;

    // ========================= onCreate ===========================

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        initToolbar("登录");

        restoreSharedPreferencesInfo();
        scrollView.setScrollViewCallbacks(this);
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
        showProgressDialog();
        MyVolley.getRequestQueue().add(new JsonObjectRequest(getLoginUrl(),
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
                            ToastUtils.show("OK");
//                            MyApplication.getInstance().setUserInfo(UserInfo.getUserInfo(JSONUtils.getJSONObject(response, "UserInfo", null)));
//                            startActivity(SelectActivity.class);
//                            finish();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        hideProgressDialog();
//                        ToastUtils.show(VolleyErrorHelper.getErrorMessage(volleyError));
                    }
                }));
    }

    private String getLoginUrl() {

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

    // ========================= progress dialog ===========================

    private void showProgressDialog() {

        if (progressDialog == null) {
            progressDialog = new MaterialDialog.Builder(this)
                    .theme(Theme.LIGHT)
                    .title("登录中...")
                    .content("请稍后...")
                    .cancelable(false)
                    .progress(true, 0)
                    .show();
        } else {
            progressDialog.show();
        }
    }

    private void hideProgressDialog() {

        progressDialog.dismiss();
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        if (scrollState == ScrollState.UP) {
            if (toolbarIsShown()) {
                hideToolbar();
            }
        } else if (scrollState == ScrollState.DOWN) {
            if (toolbarIsHidden()) {
                showToolbar();
            }
        }
    }

    private boolean toolbarIsShown() {
        return ViewHelper.getTranslationY(mToolbar) == 0;
    }

    private boolean toolbarIsHidden() {
        return ViewHelper.getTranslationY(mToolbar) == -mToolbar.getHeight();
    }

    private void showToolbar() {
        moveToolbar(0);
    }

    private void hideToolbar() {
        moveToolbar(-mToolbar.getHeight());
    }

    private void moveToolbar(float toTranslationY) {
        if (ViewHelper.getTranslationY(mToolbar) == toTranslationY) {
            return;
        }
        ValueAnimator animator = ValueAnimator.ofFloat(ViewHelper.getTranslationY(mToolbar), toTranslationY).setDuration(200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float translationY = (float) animation.getAnimatedValue();
                ViewHelper.setTranslationY(mToolbar, translationY);
                ViewHelper.setTranslationY( scrollView, translationY);
                ViewHelper.setTranslationY( shadow, translationY);

                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) (scrollView).getLayoutParams();
                lp.height = (int) -translationY + getScreenHeight() - lp.topMargin;
                ( scrollView).requestLayout();
            }
        });
        animator.start();
    }

    protected int getScreenHeight() {
        return findViewById(android.R.id.content).getHeight();
    }

}
