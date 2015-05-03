package com.unicorn.qingkee.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.unicorn.qingkee.MyApplication;
import com.unicorn.qingkee.R;
import com.unicorn.qingkee.activity.asset.ArrivalAssetListActivity;
import com.unicorn.qingkee.bean.AssetQueryInfo;
import com.unicorn.qingkee.custom.BetterSpinner;
import com.unicorn.qingkee.custom.SpinnerData;
import com.unicorn.qingkee.util.JSONUtils;
import com.unicorn.qingkee.util.StringUtils;
import com.unicorn.qingkee.util.ToastUtils;
import com.unicorn.qingkee.util.UrlUtils;
import com.unicorn.qingkee.volley.MyVolley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class ArrivalAssetQueryFragment extends Fragment {

    @InjectView(R.id.et_asset_name)
    MaterialEditText etAssetName;

    @InjectView(R.id.sp_company)
    BetterSpinner spCompany;

    @InjectView(R.id.sp_dept)
    BetterSpinner spDept;

    @InjectView(R.id.et_employee_name)
    MaterialEditText etEmployeeName;

    @InjectView(R.id.et_address)
    MaterialEditText etAddress;

    @InjectView(R.id.et_romeNumber)
    MaterialEditText etRomeNumber;

    @InjectView(R.id.sp_asset_sort)
    BetterSpinner spAssetSort;

    // ==================== onCreateView ====================

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_arrival_asset_query, container, false);
        ButterKnife.inject(this, view);

        initViews();

        return view;
    }

    @OnClick(R.id.btn_clear_views)
    public void clearViews() {

        etAssetName.setText(StringUtils.EMPTY);
        spCompany.setText(StringUtils.EMPTY);
        spDept.setText(StringUtils.EMPTY);
        etEmployeeName.setText(StringUtils.EMPTY);
        etAddress.setText(StringUtils.EMPTY);
        etRomeNumber.setText(StringUtils.EMPTY);
        spAssetSort.setText(StringUtils.EMPTY);
    }

    @OnClick(R.id.btn_query)
    public void query() {

        AssetQueryInfo assetQueryInfo = new AssetQueryInfo();
        assetQueryInfo.setUserId(MyApplication.getInstance().getUserInfo().getUserId());
        assetQueryInfo.setAssetName(etAssetName.getText().toString().trim());
        assetQueryInfo.setCompanyId(spCompany.getSelectedValue());
        assetQueryInfo.setDeptId(spDept.getSelectedValue());
        assetQueryInfo.setEmployeeName(etEmployeeName.getText().toString().trim());
        assetQueryInfo.setAddress(etAddress.getText().toString().trim());
        assetQueryInfo.setRomeNumber(etRomeNumber.getText().toString().trim());
        assetQueryInfo.setAssetSort(spAssetSort.getSelectedValue());
        assetQueryInfo.setAssetStatus("01");    // 到货状态

        Intent intent = new Intent(getActivity(), ArrivalAssetListActivity.class);
        intent.putExtra("assetQueryInfo", assetQueryInfo);
        startActivity(intent);
    }

    private void initViews() {

        initSpCompany();
        initSpAssetSort();
    }

    private void initSpCompany() {

        fetchCompanyList();
        spCompany.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                spDept.clear();
                fetchDeptList(spCompany.getSelectedValue());
            }
        });
    }

    private void initSpAssetSort() {

        List<SpinnerData> spinnerDataList = new ArrayList<>();
        spinnerDataList.add(new SpinnerData("1", "办公"));
        spinnerDataList.add(new SpinnerData("2", "租赁"));
        spAssetSort.setSpinnerDataList(spinnerDataList);
    }

    private void fetchCompanyList() {

        MyVolley.getRequestQueue().add(new JsonObjectRequest(UrlUtils.getBaseUrl() + "/GetCompany",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        int result = JSONUtils.getInt(response, "Result", 1);
                        if (result != 0) {
                            ToastUtils.show(JSONUtils.getString(response, "Msg", ""));
                        } else {
                            JSONArray companyJSONArray = JSONUtils.getJSONArray(response, "lstCompany", null);
                            List<SpinnerData> spinnerDataList = new ArrayList<>();
                            for (int i = 0; i != companyJSONArray.length(); i++) {
                                JSONObject jsonObject = JSONUtils.getJSONObject(companyJSONArray, i);
                                String companyId = JSONUtils.getString(jsonObject, "ID", "");
                                String companyName = JSONUtils.getString(jsonObject, "Commanyname", "");
                                spinnerDataList.add(new SpinnerData(companyId, companyName));
                            }
                            spCompany.setSpinnerDataList(spinnerDataList);
                        }
                    }
                },
                MyVolley.getDefaultErrorListener()));
    }

    private void fetchDeptList(final String companyId) {

        String url = UrlUtils.getBaseUrl() + "/GetDept?companyid=" + companyId;
        MyVolley.getRequestQueue().add(new JsonObjectRequest(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        int result = JSONUtils.getInt(response, "Result", 1);
                        if (result != 0) {
                            ToastUtils.show(JSONUtils.getString(response, "Msg", ""));
                        } else {
                            JSONArray deptJSONArray = JSONUtils.getJSONArray(response, "lstDept", null);
                            List<SpinnerData> spinnerDataList = new ArrayList<>();
                            for (int i = 0; i != deptJSONArray.length(); i++) {
                                JSONObject jsonObject = JSONUtils.getJSONObject(deptJSONArray, i);
                                String deptId = JSONUtils.getString(jsonObject, "ID", "");
                                String deptName = JSONUtils.getString(jsonObject, "Deptname", "");
                                spinnerDataList.add(new SpinnerData(deptId, deptName));
                            }
                            spDept.setSpinnerDataList(spinnerDataList);
                        }
                    }
                },
                MyVolley.getDefaultErrorListener()));
    }

}


