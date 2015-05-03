package com.unicorn.qingkee.activity.asset;

import android.content.Intent;
import android.net.Uri;
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
import com.unicorn.qingkee.mycode.SwipeableRecyclerViewTouchListener;
import com.unicorn.qingkee.util.JSONUtils;
import com.unicorn.qingkee.util.ToastUtils;
import com.unicorn.qingkee.util.UrlUtils;
import com.unicorn.qingkee.volley.MyVolley;
import com.unicorn.qingkee.volley.toolbox.VolleyErrorHelper;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.InjectView;
import butterknife.OnClick;


public class AssetsListActivity extends ToolbarActivity {

    @InjectView(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;

    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;

    // todo 写适配器
    ArrivalAssetListAdapter arrivalAssetListAdapter;

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
        arrivalAssetListAdapter = new ArrivalAssetListAdapter(this);
        arrivalAssetListAdapter.setAssetList((ArrayList<Asset>) getIntent().getSerializableExtra("assetList"));
        recyclerView.setAdapter(arrivalAssetListAdapter);
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
                                    arrivalAssetListAdapter.getAssetList().remove(position);
                                    arrivalAssetListAdapter.notifyItemRemoved(position);
                                }
                                arrivalAssetListAdapter.notifyDataSetChanged();
                                floatingActionButton.show(true);
                            }

                            @Override
                            public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    arrivalAssetListAdapter.getAssetList().remove(position);
                                    arrivalAssetListAdapter.notifyItemRemoved(position);
                                }
                                arrivalAssetListAdapter.notifyDataSetChanged();
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

    @OnClick(R.id.floatingActionButton)
    public void finishWithData() {

        Intent intent = new Intent();
        intent.putExtra("assetList", arrivalAssetListAdapter.getAssetList());
        setResult(2333, intent);
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
                            Asset asset = Asset.parse(JSONUtils.getJSONObject(JSONUtils.getJSONArray(response, "lstAsset", null), 0));
                            for (Asset temp : arrivalAssetListAdapter.getAssetList()) {
                                if (asset.getId().equals(temp.getId())) {
                                    ToastUtils.show("该资产已在列表中");
                                    return;
                                }
                            }
                            arrivalAssetListAdapter.getAssetList().add(asset);
                            arrivalAssetListAdapter.notifyDataSetChanged();
                            floatingActionButton.show(true);
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
        builder.appendQueryParameter("assetStatus", getIntent().getStringExtra("assetStatus"));
        return builder.toString();
    }

    private LinearLayoutManager getLinearLayoutManager() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return linearLayoutManager;
    }

}
