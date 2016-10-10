package com.unicorn.qingkee.fragment.asset;

import android.content.Intent;
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
import com.unicorn.qingkee.activity.address.AddressSearchActivity;
import com.unicorn.qingkee.activity.address.model.Address;
import com.unicorn.qingkee.activity.base.ToolbarActivity;
import com.unicorn.qingkee.fragment.base.AssetsFragment;
import com.unicorn.qingkee.util.JSONUtils;
import com.unicorn.qingkee.util.StringUtils;
import com.unicorn.qingkee.util.ToastUtils;
import com.unicorn.qingkee.util.UrlUtils;
import com.unicorn.qingkee.volley.MyVolley;
import com.unicorn.qingkee.volley.toolbox.VolleyErrorHelper;

import org.json.JSONObject;

import butterknife.InjectView;
import butterknife.OnClick;


public class AssetTransferInFragment extends AssetsFragment {


    @InjectView(R.id.et_address)
    MaterialEditText etAddress;

    @InjectView(R.id.et_note)
    MaterialEditText etNote;

    @Override
    public String getAssetStatus() {
        return "11";
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_asset_transfer_in;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @OnClick(R.id.btn_clear_views)
    public void clearViews() {
        address = null;
        etAddress.setText(StringUtils.EMPTY);
        etNote.setText(StringUtils.EMPTY);
    }

    @OnClick(R.id.btn_confirm)
    public void confirm() {
        if (etAssets.getText().toString().equals(StringUtils.EMPTY)) {
            ToastUtils.show("资产不能为空");
            return;
        }
        if (address == null) {
            ToastUtils.show("安装地址不能为空");
            return;
        }
//        if (etNote.getText().toString().equals(StringUtils.EMPTY)) {
//            ToastUtils.show("备注不能为空");
//            return;
//        }
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
                            // 可以本人转移到本人
                            String toolbarTitle = ((ToolbarActivity) getActivity()).getToolbarTitle();
                            ToastUtils.show(toolbarTitle + "成功");
                            finishActivity();
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
        Uri.Builder builder = Uri.parse(UrlUtils.getBaseUrl() + "/AssetTransferIn?").buildUpon();
        builder.appendQueryParameter("barcode", getBarcodes());
        builder.appendQueryParameter("address", address.getDocname());
        builder.appendQueryParameter("addresscode", address.getDoccode());
        builder.appendQueryParameter("userid", MyApplication.getInstance().getUserInfo().getUserId());
//        builder.appendQueryParameter("deptid", spDept.getSelectedValue());
//        builder.appendQueryParameter("employeeid", spEmployee.getSelectedValue());
        builder.appendQueryParameter("note", etNote.getText().toString().trim());
        return builder.toString();
    }


    //  address

    @OnClick(R.id.et_address)
    public void addressOnClick() {
        Intent intent = new Intent(getActivity(), AddressSearchActivity.class);
        startActivityForResult(intent, 2333);
    }

    Address address;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            address = (Address) data.getSerializableExtra("address");
            if (address != null) {
                etAddress.setText(address.getDocname());
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
