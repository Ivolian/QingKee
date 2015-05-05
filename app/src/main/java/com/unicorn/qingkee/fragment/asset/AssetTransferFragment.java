package com.unicorn.qingkee.fragment.asset;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.unicorn.qingkee.MyApplication;
import com.unicorn.qingkee.R;
import com.unicorn.qingkee.fragment.base.AssetsFragment;
import com.unicorn.qingkee.mycode.BetterSpinner;
import com.unicorn.qingkee.mycode.FetchUtil;
import com.unicorn.qingkee.util.JSONUtils;
import com.unicorn.qingkee.util.StringUtils;
import com.unicorn.qingkee.util.ToastUtils;
import com.unicorn.qingkee.util.UrlUtils;
import com.unicorn.qingkee.volley.MyVolley;
import com.unicorn.qingkee.volley.toolbox.VolleyErrorHelper;


import org.json.JSONObject;

import butterknife.InjectView;
import butterknife.OnClick;


// 同公司走转移流程
// todo 转移有问题
public class AssetTransferFragment extends AssetsFragment {

    @InjectView(R.id.sp_dept)
    BetterSpinner spDept;

    @InjectView(R.id.sp_employee)
    BetterSpinner spEmployee;

    @InjectView(R.id.et_note)
    MaterialEditText etNote;

    @Override
    public String getAssetStatus() {
        return "04";
    }

    @Override
    public String getTitle() {
        return "待转移资产";
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_asset_transfer;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        initSpDept();

        return view;
    }

    @OnClick(R.id.btn_clear_views)
    public void clearViews() {

        spDept.setText(StringUtils.EMPTY);
        spEmployee.setText(StringUtils.EMPTY);
        etNote.setText(StringUtils.EMPTY);
    }

    @OnClick(R.id.btn_confirm)
    public void confirm() {

        if (etAssets.getText().toString().equals("")) {
            ToastUtils.show("请先添加资产");
            return;
        }
        if (spDept.getSelectedValue().equals(StringUtils.EMPTY)) {
            ToastUtils.show("使用部门不能为空");
            return;
        }
        if (spEmployee.getSelectedValue().equals(StringUtils.EMPTY)) {
            ToastUtils.show("使用人不能为空");
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
                            // 可以本人转移到本人
                            ToastUtils.show("转移成功");
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

        Uri.Builder builder = Uri.parse(UrlUtils.getBaseUrl() + "/AssetTransfer?").buildUpon();
        builder.appendQueryParameter("userid", MyApplication.getInstance().getUserInfo().getUserId());
        builder.appendQueryParameter("barcode", getBarcodes());
        builder.appendQueryParameter("deptid", spDept.getSelectedValue());
        builder.appendQueryParameter("employeeid", spEmployee.getSelectedValue());
        builder.appendQueryParameter("note", etNote.getText().toString().trim());
        return builder.toString();
    }

    private void initSpDept() {

        final String companyId = MyApplication.getInstance().getUserInfo().getCompanyId();
        FetchUtil.fetchDeptList(spDept, companyId);
        spDept.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                spEmployee.clear();
                FetchUtil.fetchEmployeeList(spEmployee, companyId, spDept.getSelectedValue());
            }
        });
    }

}
