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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rengwuxian.materialedittext.validation.RegexpValidator;
import com.unicorn.qingkee.MyApplication;
import com.unicorn.qingkee.R;
import com.unicorn.qingkee.fragment.base.BaseFragment;
import com.unicorn.qingkee.util.JSONUtils;
import com.unicorn.qingkee.util.ToastUtils;
import com.unicorn.qingkee.util.UrlUtils;
import com.unicorn.qingkee.volley.MyVolley;
import com.unicorn.qingkee.volley.toolbox.VolleyErrorHelper;

import org.json.JSONObject;

import butterknife.InjectView;
import butterknife.OnClick;


public class AssetAbandonFragment extends BaseFragment {

    @InjectView(R.id.barcode)
    MaterialEditText etBarcode;

    @InjectView(R.id.et_aband_value)
    MaterialEditText etAbandValue;

    @InjectView(R.id.et_aband_cost)
    MaterialEditText etAbandCost;

    @InjectView(R.id.et_note)
    MaterialEditText etNote;

    @OnClick(R.id.btn_scan_barcode)
    public void scanBarcode() {

        IntentIntegrator.forSupportFragment(this).initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        // 处理扫描条码返回结果
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null && result.getContents() != null) {
            etBarcode.setText(result.getContents());
        }
    }
//    @Override
//    public String getAssetStatus() {
//        return "04";
//    }
//
//    @Override
//    public String getTitle() {
//        return "待报废资产";
//    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_asset_abandon;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);
        initViews();
        return view;
    }

    private void initViews() {

        RegexpValidator regexpValidator = new RegexpValidator("请输入两位正小数", "^[\\s]{0,}$|^[0-9]+.[0-9]{2}?$");
        etAbandValue.addValidator(regexpValidator);
        etAbandCost.addValidator(regexpValidator);
    }


    @OnClick(R.id.btn_confirm)
    public void confirm() {

        if (!etAbandValue.validate()) {
//            ToastUtils.show("两位小数");
            return;
        }

        // todo
        if (!etAbandValue.validate()) {
            ToastUtils.show("两位小数");
            return;
        }
        if (etBarcode.getText().toString().equals("")) {
            ToastUtils.show("条码不能为空");
            return;
        }
        if (etAbandValue.getText().toString().equals("")) {
            ToastUtils.show("报废收入不能为空");
            return;
        }
        if (etAbandCost.getText().toString().equals("")) {
            ToastUtils.show("报废成本不能为空");
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
                            ToastUtils.show("报废成功");
                            finishWithActivity();
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

        Uri.Builder builder = Uri.parse(UrlUtils.getBaseUrl() + "/AssetAbandoned?").buildUpon();
        builder.appendQueryParameter("userid", MyApplication.getInstance().getUserInfo().getUserId());
        builder.appendQueryParameter("barcode", etBarcode.getText().toString().trim());
        builder.appendQueryParameter("abandvalue", etAbandValue.getText().toString().trim());
        builder.appendQueryParameter("abandcost", etAbandCost.getText().toString().trim());
        builder.appendQueryParameter("note", etNote.getText().toString().trim());
        return builder.toString();
    }

}
