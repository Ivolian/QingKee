package com.unicorn.qingkee.activity.asset;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.unicorn.qingkee.R;
import com.unicorn.qingkee.activity.base.ToolbarActivity;
import com.unicorn.qingkee.adapter.list.ArrivalAssetListAdapter;
import com.unicorn.qingkee.bean.Asset;
import com.unicorn.qingkee.bean.AssetQueryInfo;
import com.unicorn.qingkee.util.JSONUtils;
import com.unicorn.qingkee.util.ToastUtils;
import com.unicorn.qingkee.util.UrlUtils;
import com.unicorn.qingkee.volley.MyVolley;
import com.unicorn.qingkee.volley.toolbox.VolleyErrorHelper;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.InjectView;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;


public class ArrivalAssetListActivity extends ToolbarActivity {

    // ==================== pager data ====================

    final Integer pagerSize = 5;

    Integer currentPager;

    Boolean noData;

    Boolean allDataLoaded;

    // ==================== views ====================

    @InjectView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;

    ArrivalAssetListAdapter arrivalAssetListAdapter;

    @InjectView(R.id.load_more)
    SmoothProgressBar loadMore;

    AssetQueryInfo assetQueryInfo;

    @Override
    public int getLayoutResourceId() {

        return R.layout.activity_arrival_asset_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        assetQueryInfo = (AssetQueryInfo) getIntent().getSerializableExtra("assetQueryInfo");
        assetQueryInfo.setPagerSize(pagerSize);

        initToolbar("到货资产列表", true);
        initPagerData();
        initSwipeRefreshLayout();
        initRecyclerView();
        reload();
    }

    @Override
    protected void onNewIntent(Intent intent) {

        super.onNewIntent(intent);
        reload();
    }

    private void initSwipeRefreshLayout() {

        swipeRefreshLayout.setColorSchemeResources(R.color.grass_primary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reload();
            }
        });
    }

    private void initRecyclerView() {

        final LinearLayoutManager linearLayoutManager = getLinearLayoutManager();
        recyclerView.setLayoutManager(linearLayoutManager);
        arrivalAssetListAdapter = new ArrivalAssetListAdapter(this);
        recyclerView.setAdapter(arrivalAssetListAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                if (noData || allDataLoaded || loadMore.getVisibility() == View.VISIBLE) {
                    return;
                }
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastVisibleItem = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = linearLayoutManager.getItemCount();
                    if (totalItemCount != 0 && totalItemCount == (lastVisibleItem + 1)) {
                        loadMore();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            }
        });
    }

    private void reload() {

        // https://code.google.com/p/android/issues/detail?id=77712
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        initPagerData();
        assetQueryInfo.setPager(1);
        MyVolley.getRequestQueue().add(new JsonObjectRequest(UrlUtils.getBaseUrl() + "/GetAssets?" + assetQueryInfo.toUrl(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        swipeRefreshLayout.setRefreshing(false);
                        int result = JSONUtils.getInt(response, "Result", 1);
                        if (result != 0) {
                            ToastUtils.show(JSONUtils.getString(response, "Msg", ""));
                        } else {
                            JSONArray assetJSONArray = JSONUtils.getJSONArray(response, "lstAsset", null);
                            arrivalAssetListAdapter.setAssetList(getListFromJSONArray(assetJSONArray));
                            arrivalAssetListAdapter.notifyDataSetChanged();

                            noData = (assetJSONArray.length() == 0);
                            if (noData) {
                                ToastUtils.show("没有符合查询条件的数据");
                                return;
                            }
                            currentPager = 1;
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        swipeRefreshLayout.setRefreshing(false);
                        ToastUtils.show(VolleyErrorHelper.getErrorMessage(volleyError));
                    }
                }));
    }

    private void loadMore() {

        loadMore.setVisibility(View.VISIBLE);
        assetQueryInfo.setPager(currentPager + 1);
        MyVolley.getRequestQueue().add(new JsonObjectRequest(UrlUtils.getBaseUrl() + "/GetAssets?" + assetQueryInfo.toUrl(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loadMore.setVisibility(View.GONE);
                        int result = JSONUtils.getInt(response, "Result", 1);
                        if (result != 0) {
                            ToastUtils.show(JSONUtils.getString(response, "Msg", ""));
                        } else {
                            JSONArray assetJSONArray = JSONUtils.getJSONArray(response, "lstAsset", null);
                            arrivalAssetListAdapter.addAssetList(getListFromJSONArray(assetJSONArray));
                            arrivalAssetListAdapter.notifyDataSetChanged();

                            allDataLoaded = (assetJSONArray.length() == 0);
                            if (allDataLoaded) {
                                ToastUtils.show("已加载所有数据");
                                return;
                            }
                            currentPager++;
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        loadMore.setVisibility(View.GONE);
                        ToastUtils.show(VolleyErrorHelper.getErrorMessage(volleyError));
                    }
                }));
    }

    // ============ low level methods ===============

    private void initPagerData() {

        currentPager = 0;
        noData = false;
        allDataLoaded = false;
    }

    private LinearLayoutManager getLinearLayoutManager() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return linearLayoutManager;
    }

    private ArrayList<Asset> getListFromJSONArray(JSONArray assetJSONArray) {

        ArrayList<Asset> assetList = new ArrayList<>();
        for (int i = 0; i != assetJSONArray.length(); i++) {
            JSONObject jsonObject = JSONUtils.getJSONObject(assetJSONArray, i);
            Asset asset = Asset.parse(jsonObject);
            assetList.add(asset);
        }
        return assetList;
    }

}
