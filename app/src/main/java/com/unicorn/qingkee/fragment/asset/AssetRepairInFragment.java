package com.unicorn.qingkee.fragment.asset;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.unicorn.qingkee.MyApplication;
import com.unicorn.qingkee.R;
import com.unicorn.qingkee.fragment.base.AssetsFragment;
import com.unicorn.qingkee.util.JSONUtils;
import com.unicorn.qingkee.util.ToastUtils;
import com.unicorn.qingkee.util.UrlUtils;
import com.unicorn.qingkee.volley.MyVolley;
import com.unicorn.qingkee.volley.toolbox.VolleyErrorHelper;


import org.json.JSONObject;

import butterknife.InjectView;
import butterknife.OnClick;


public class AssetRepairInFragment extends AssetsFragment {

    @InjectView(R.id.et_note)
    MaterialEditText etNote;

    @Override
    public String getAssetStatus() {
        return "06";
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_asset_repair_in;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @OnClick(R.id.btn_confirm)
    public void confirm() {

        if (etAssets.getText().toString().equals("")) {
            ToastUtils.show("请先添加资产");
            return;
        }
        if (etNote.getText().toString().equals("")) {
            ToastUtils.show("备注不能为空");
            return;
        }
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
                            ToastUtils.show("维修入库成功");
                            AssetRepairInFragment.this.getActivity().finish();
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

        Uri.Builder builder = Uri.parse(UrlUtils.getBaseUrl() + "/AssetRepairIn?").buildUpon();
        builder.appendQueryParameter("userid", MyApplication.getInstance().getUserInfo().getUserId());
        builder.appendQueryParameter("barcode", getBarcodes());
        builder.appendQueryParameter("note", etNote.getText().toString().trim());
        return builder.toString();
    }

}
