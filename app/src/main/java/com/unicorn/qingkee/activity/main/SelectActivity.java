package com.unicorn.qingkee.activity.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.unicorn.qingkee.MyApplication;
import com.unicorn.qingkee.R;
import com.unicorn.qingkee.activity.asset.AssetDetailActivity;
import com.unicorn.qingkee.activity.base.ToolbarActivity;
import com.unicorn.qingkee.bean.Asset;
import com.unicorn.qingkee.util.JSONUtils;
import com.unicorn.qingkee.util.ToastUtils;
import com.unicorn.qingkee.util.UrlUtils;
import com.unicorn.qingkee.volley.MyVolley;
import com.unicorn.qingkee.volley.toolbox.VolleyErrorHelper;

import org.json.JSONObject;


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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // 处理扫描条码返回结果
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null && result.getContents() != null) {
            fetchAssetByBarcode(result.getContents());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_select_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.asset_detail) {
            new IntentIntegrator(this).initiateScan();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchAssetByBarcode(final String barcode) {

        showProgressDialog();
        MyVolley.getRequestQueue().add(new JsonObjectRequest(getUrl(barcode),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideProgressDialog();
                        int result = JSONUtils.getInt(response, "Result", 1);
                        if (result != 0) {
                            ToastUtils.show(JSONUtils.getString(response, "Msg", ""));
                        } else {
                            JSONObject jsonObject = JSONUtils.getJSONObject(JSONUtils.getJSONArray(response, "lstAsset", null), 0);
                            Asset asset = Asset.parse(jsonObject);
                            Intent intent = new Intent(SelectActivity.this, AssetDetailActivity.class);
                            intent.putExtra("asset", asset);
                            startActivity(intent);
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

    private String getUrl(String barcode) {

        Uri.Builder builder = Uri.parse(UrlUtils.getBaseUrl() + "/GetAssetByBarcode?").buildUpon();
        builder.appendQueryParameter("userid", MyApplication.getInstance().getUserInfo().getUserId());
        builder.appendQueryParameter("barcode", barcode);
        return builder.toString();
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
