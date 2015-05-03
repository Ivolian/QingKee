package com.unicorn.qingkee.activity.asset;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.clans.fab.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.unicorn.qingkee.MyApplication;
import com.unicorn.qingkee.R;
import com.unicorn.qingkee.activity.base.ToolbarActivity;
import com.unicorn.qingkee.adapter.asset.ArrivalAssetListAdapter;
import com.unicorn.qingkee.bean.Asset;
import com.unicorn.qingkee.bean.AssetInfo;
import com.unicorn.qingkee.mycode.SwipeableRecyclerViewTouchListener;
import com.unicorn.qingkee.util.JSONUtils;
import com.unicorn.qingkee.util.ToastUtils;
import com.unicorn.qingkee.util.UrlUtils;
import com.unicorn.qingkee.volley.MyVolley;
import com.unicorn.qingkee.volley.toolbox.VolleyErrorHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class AssetsListActivity extends ToolbarActivity {

    final int RESULT_CODE = 233;

    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;

    ArrivalAssetListAdapter assetListAdapter;

    @InjectView(R.id.floating_action_button)
    FloatingActionButton floatingActionButton;

    @Override
    public int getLayoutResourceId() {

        return R.layout.activity_assets_list_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        initToolbar(getIntent().getStringExtra("title") + "列表", true);

        initRecyclerView();
    }

    private void initRecyclerView() {

        recyclerView.setLayoutManager(getLinearLayoutManager());
        assetListAdapter = new ArrivalAssetListAdapter(this);
        assetListAdapter.setAssetList((ArrayList<Asset>) getIntent().getSerializableExtra("assetList"));
        recyclerView.setAdapter(assetListAdapter);
        SwipeableRecyclerViewTouchListener swipeTouchListener =
                new SwipeableRecyclerViewTouchListener(recyclerView,
                        new SwipeableRecyclerViewTouchListener.SwipeListener() {
                            @Override
                            public boolean canSwipe(int position) {
                                return true;
                            }

                            @Override
                            public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    assetListAdapter.getAssetList().remove(position);
                                    assetListAdapter.notifyItemRemoved(position);
                                }
                                assetListAdapter.notifyDataSetChanged();
                                floatingActionButton.show(true);
                            }

                            @Override
                            public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    assetListAdapter.getAssetList().remove(position);
                                    assetListAdapter.notifyItemRemoved(position);
                                }
                                assetListAdapter.notifyDataSetChanged();
                                floatingActionButton.show(true);
                            }
                        },
                        new RecyclerView.OnScrollListener() {
                            @Override
                            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                if (Math.abs(dy) > 4) {
                                    if (dy > 0) {
                                        floatingActionButton.hide(true);
                                    } else {
                                        floatingActionButton.show(true);
                                    }
                                }
                            }
                        });
        recyclerView.addOnItemTouchListener(swipeTouchListener);
    }

    // ==================== onActivityResult ====================

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null && result.getContents() != null) {
            String barcode = result.getContents();
            fetchAssetByBarcode(barcode);
        }
    }

    @OnClick(R.id.floating_action_button)
    public void finishWithData() {

        Intent intent = new Intent();
        intent.putExtra("assetList", assetListAdapter.getAssetList());
        setResult(RESULT_CODE, intent);
        finish();
    }

    // ========================= menu ===========================

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_assets_list_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add_asset:
                new IntentIntegrator(this).initiateScan();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchAssetByBarcode(final String barcode) {

        AssetInfo assetInfo = new AssetInfo();
        assetInfo.setUserId(MyApplication.getInstance().getUserInfo().getUserId());
        assetInfo.setBarcode(barcode);
        assetInfo.setAssetStatus(getIntent().getStringExtra("assetStatus"));

        final ProgressDialog progressDialog = ProgressDialog.show(this, "处理中...", "请稍后...", true);
        String url = UrlUtils.getBaseUrl() + "/GetAssetByBarcode?" + assetInfo.toUrl();
        MyVolley.getRequestQueue().add(new JsonObjectRequest(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        int result = JSONUtils.getInt(response, "Result", 1);
                        if (result != 0) {
                            ToastUtils.show(JSONUtils.getString(response, "Msg", ""));
                        } else {
                            JSONArray jsonArray = JSONUtils.getJSONArray(response, "lstAsset", null);
                            Asset asset = getAssetFromJSONArray(jsonArray);
                            for (Asset temp : assetListAdapter.getAssetList()) {
                                if (asset.getId().equals(temp.getId())) {
                                    ToastUtils.show("该资产已在列表中");
                                    return;
                                }
                            }
                            assetListAdapter.getAssetList().add(asset);
                            assetListAdapter.notifyDataSetChanged();
                            floatingActionButton.show(true);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                        ToastUtils.show(VolleyErrorHelper.getErrorMessage(volleyError));
                    }
                }));
    }

    // ============ low level methods ===============

    private LinearLayoutManager getLinearLayoutManager() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return linearLayoutManager;
    }

    private Asset getAssetFromJSONArray(JSONArray assetJSONArray) {

        JSONObject jsonObject = JSONUtils.getJSONObject(assetJSONArray, 0);
        String assetId = JSONUtils.getString(jsonObject, "ID", "");
        String assetName = JSONUtils.getString(jsonObject, "Assetname", "");
        String companyName = JSONUtils.getString(jsonObject, "CommanyName", "");
        Double assetCost = JSONUtils.getDouble(jsonObject, "Assetcost", 0);
        String assetSort = JSONUtils.getString(jsonObject, "Assetsort", "");
        String installPosition = JSONUtils.getString(jsonObject, "Installposition", "");
        String roomNumber = JSONUtils.getString(jsonObject, "Roomnumber", "");
        String barcode = JSONUtils.getString(jsonObject, "Barcode", "");
        return new Asset(assetId, assetName, companyName, assetCost, assetSort, installPosition, roomNumber, barcode);
    }

}
