package com.unicorn.qingkee.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.unicorn.qingkee.MyApplication;
import com.unicorn.qingkee.R;
import com.unicorn.qingkee.bean.Asset;
import com.unicorn.qingkee.mycode.BetterSpinner;
import com.unicorn.qingkee.mycode.FetchUtil;
import com.unicorn.qingkee.util.StringUtils;
import com.unicorn.qingkee.util.ToastUtils;


import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class AssetApplyFragment extends Fragment {

    final int REQUEST_CODE = 233;

    @InjectView(R.id.et_assets)
    MaterialEditText etAssets;

    ArrayList<Asset> assetList = new ArrayList<>();

    @InjectView(R.id.sp_dept)
    BetterSpinner spDept;

    @InjectView(R.id.sp_employee)
    BetterSpinner spEmployee;

    @InjectView(R.id.et_note)
    MaterialEditText etNote;

    // ====================== ButterKnife ======================

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_asset_apply, container, false);
        ButterKnife.inject(this, view);

        initSpDept();

        return view;
    }

    // ====================== onActivityResult ======================

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        // 正常退出
        if (data == null) {
            return;
        }
        assetList = (ArrayList<Asset>) data.getSerializableExtra("assetList");
        etAssets.setText(assetListToText());
    }

    @OnClick(R.id.et_assets)
    public void startAssetDisplayActivity() {

//        Intent intent = new Intent(getActivity(), AssetDisplayListActivity.class);
//        intent.putExtra("title", "待领用资产");
//        intent.putExtra("assetStatus","03");
//        intent.putExtra("assetList", assetList);
//        startActivityForResult(intent, REQUEST_CODE);
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

//        AssetApplyInfo assetApplyInfo = new AssetApplyInfo();
//        assetApplyInfo.setUserId(MyApplication.getInstance().getUserInfo().getUserId());
//        assetApplyInfo.setBarcode(assetListToBarcode());
//        assetApplyInfo.setCompanyId(MyApplication.getInstance().getUserInfo().getCompanyId());
//        assetApplyInfo.setDeptId(spDept.getSelectedValue());
//        assetApplyInfo.setEmployeeId(spEmployee.getSelectedValue());
//        assetApplyInfo.setNote(etNote.getText().toString().trim());

//        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "处理中...", "请稍后...", true);
//        String url = Url.getBaseUrl() + "/AssetApply?" + assetApplyInfo.toUrl();
//        MyVolley.getRequestQueue().add(new JsonObjectRequest(url,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        progressDialog.dismiss();
//                        int result = JSONUtils.getInt(response, "Result", 1);
//                        if (result != 0) {
//                            // 领用失败，未找到资产
//                            ToastUtils.show(JSONUtils.getString(response, "Msg", ""));
//                        } else {
//                            ToastUtils.show("领用成功");
//                            AssetApplyFragment.this.getActivity().finish();
//
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError volleyError) {
//                        progressDialog.dismiss();
//                        ToastUtils.show(VolleyErrorHelper.getErrorMessage(volleyError));
//                    }
//                }));
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

    private String assetListToText() {

        String text = "";
        for (int i = 0, size = assetList.size(); i != size; i++) {
            String assetName = assetList.get(i).getAssetName();
            text += assetName;
            if (i != size - 1) {
                text += " , ";
            }
        }
        return text;
    }

    private String assetListToBarcode() {

        List<String> barcodeList = new ArrayList<>();
        for (Asset asset : assetList) {
            barcodeList.add(asset.getBarcode());
        }

        return StringUtils.join(barcodeList.toArray(), '|');
    }

}
