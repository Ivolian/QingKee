package com.unicorn.qingkee.activity.asset;

import android.net.Uri;
import android.os.Bundle;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.unicorn.qingkee.MyApplication;
import com.unicorn.qingkee.R;
import com.unicorn.qingkee.activity.base.ToolbarActivity;
import com.unicorn.qingkee.bean.Asset;
import com.unicorn.qingkee.util.JSONUtils;
import com.unicorn.qingkee.util.ToastUtils;
import com.unicorn.qingkee.util.UrlUtils;
import com.unicorn.qingkee.volley.MyVolley;
import com.unicorn.qingkee.volley.toolbox.VolleyErrorHelper;

import org.json.JSONObject;

import java.text.SimpleDateFormat;

import butterknife.InjectView;


public class AssetDetailActivity extends ToolbarActivity {

    @InjectView(R.id.asset_name)
    MaterialEditText etAssetName;

    @InjectView(R.id.asset_status)
    MaterialEditText etAssetStatus;

    @InjectView(R.id.brand)
    MaterialEditText etBrand;

    @InjectView(R.id.models)
    MaterialEditText etModels;

    @InjectView(R.id.buy_date)
    MaterialEditText etBuyDate;

    @InjectView(R.id.asset_value)
    MaterialEditText etAssetValue;

    @InjectView(R.id.supplier_name)
    MaterialEditText etSupplierName;

    @InjectView(R.id.company_name)
    MaterialEditText etCompanyName;

    @InjectView(R.id.asset_sort)
    MaterialEditText etAssetSort;

    @InjectView(R.id.address)
    MaterialEditText etAddress;

    @InjectView(R.id.room_number)
    MaterialEditText etRoomnumber;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_asset_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        initToolbar("资产详情", true);

        String barcode = getIntent().getStringExtra("barcode");
        fetchAssetByBarcode(barcode);
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

                            etAssetName.setText(asset.getAssetName());
                            etAssetStatus.setText(Asset.getAssetStatusText(asset.getAssetStatus()));
                            etBrand.setText(asset.getBrand());
                            etModels.setText(asset.getModels());
                            etModels.setText(asset.getModels());
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-m-d hh:mm:ss");
                            etBuyDate.setText(simpleDateFormat.format(asset.getBuyDate()));
                            etAssetValue.setText(asset.getAssetValue());
                            etSupplierName.setText(asset.getSupplierName());
                            etCompanyName.setText(asset.getCompanyName());
                            etAssetSort.setText(Asset.getAssetSortText(asset.getAssetSort()));
                            etAddress.setText(asset.getAddress());
                            etRoomnumber.setText(asset.getRoomNumber());
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

}
