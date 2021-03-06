package com.unicorn.qingkee.fragment.asset;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.unicorn.qingkee.MyApplication;
import com.unicorn.qingkee.R;
import com.unicorn.qingkee.adapter.list.AssetInventoryListAdapter;
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

    @InjectView(R.id.progressBar)
    ProgressBarCircularIndeterminate progressBar;

    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;

    AssetInventoryListAdapter assetInventoryListAdapter;

    public String currentInventoryId;

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_asset_inventory_list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        initRecyclerView();

        return view;
    }

    private void initRecyclerView() {

        recyclerView.setLayoutManager(getLinearLayoutManager());
        assetInventoryListAdapter = new AssetInventoryListAdapter(this);
        recyclerView.setAdapter(assetInventoryListAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        // 处理扫描条码返回结果
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null && result.getContents() != null) {
            inventory(result.getContents());
        }
    }

    private void showManualBarcodeDialog() {

        MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                .title("请输入条码")
                .customView(R.layout.dialog_manual_barcode, true)
                .positiveText("确定")
                .negativeText("取消")
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        if (dialog.getCustomView() != null) {
                            EditText etBarcode = (EditText) dialog.getCustomView().findViewById(R.id.barcode);
                            inventory(etBarcode.getText().toString().trim());
                        }
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                    }
                }).build();
        dialog.show();
    }

    private void inventory(String barcode) {

        Uri.Builder builder = Uri.parse(UrlUtils.getBaseUrl() + "/InventoryOperation?").buildUpon();
        builder.appendQueryParameter("userid", MyApplication.getInstance().getUserInfo().getUserId());
        builder.appendQueryParameter("barcode", barcode);
        builder.appendQueryParameter("inventoryid", currentInventoryId);

        showProgressDialog();
        MyVolley.getRequestQueue().add(new JsonObjectRequest(builder.toString(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideProgressDialog();
                        ToastUtils.show(JSONUtils.getString(response, "Msg", ""));
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

    @Override
    public void isVisibleToUser() {

        fetchList();
    }

    private void fetchList() {
        showProgressDialog();
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

    public void onInventoryListItemClicked(String clickedInventoryId, boolean manual) {

        currentInventoryId = clickedInventoryId;
        if (manual) {
            showManualBarcodeDialog();
        } else {
            IntentIntegrator.forSupportFragment(this).initiateScan();
        }
    }

    //

    @Override
    public void showProgressDialog() {

        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgressDialog() {

        if (progressBar != null) {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
