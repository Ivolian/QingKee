package com.unicorn.qingkee.fragment.asset;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.unicorn.qingkee.MyApplication;
import com.unicorn.qingkee.R;
import com.unicorn.qingkee.adapter.asset.AssetInventoryListAdapter;
import com.unicorn.qingkee.bean.Inventory;
import com.unicorn.qingkee.fragment.base.BaseFragment;
import com.unicorn.qingkee.util.JSONUtils;
import com.unicorn.qingkee.util.ToastUtils;
import com.unicorn.qingkee.util.UrlUtils;
import com.unicorn.qingkee.volley.MyVolley;
import com.unicorn.qingkee.volley.toolbox.VolleyErrorHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;


public class AssetInventoryListFragment extends BaseFragment {

    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;

    AssetInventoryListAdapter assetInventoryListAdapter;

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_asset_inventory_list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        initRecyclerView();
        loadData();

        return view;
    }

    private void initRecyclerView() {

        recyclerView.setLayoutManager(getLinearLayoutManager());
        assetInventoryListAdapter = new AssetInventoryListAdapter(getActivity());
        recyclerView.setAdapter(assetInventoryListAdapter);
    }

    private void loadData() {
        showProgressDialog("加载数据中...");
        MyVolley.getRequestQueue().add(new JsonObjectRequest(getUrl(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideProgressDialog();
                        int result = JSONUtils.getInt(response, "Result", 1);
                        if (result != 0) {
                            ToastUtils.show(JSONUtils.getString(response, "Msg", ""));
                        } else {
                            List<Inventory> inventoryList = new ArrayList<>();
                            JSONArray jsonArray = JSONUtils.getJSONArray(response, "lstInventory", null);
                            for (int i = 0, length = jsonArray.length(); i != length; i++) {
                                JSONObject jsonObject = JSONUtils.getJSONObject(jsonArray, i);
                                inventoryList.add(Inventory.parse(jsonObject));
                            }
                            assetInventoryListAdapter.setInventoryList(inventoryList);
                            assetInventoryListAdapter.notifyDataSetChanged();
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

        Uri.Builder builder = Uri.parse(UrlUtils.getBaseUrl() + "/GetInventory?").buildUpon();
        builder.appendQueryParameter("userid", MyApplication.getInstance().getUserInfo().getUserId());
        builder.appendQueryParameter("status", "4");
        return builder.toString();
    }

    private LinearLayoutManager getLinearLayoutManager() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return linearLayoutManager;
    }

}
