package com.unicorn.qingkee.fragment.asset;

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
import com.unicorn.qingkee.activity.asset.ArrivalAssetListActivity;
import com.unicorn.qingkee.bean.AssetQueryInfo;
import com.unicorn.qingkee.fragment.base.BaseFragment;
import com.unicorn.qingkee.mycode.BetterSpinner;
import com.unicorn.qingkee.mycode.FetchUtil;
import com.unicorn.qingkee.mycode.SpinnerData;
import com.unicorn.qingkee.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class ArrivalAssetQueryFragment extends BaseFragment {

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
    public int getLayoutResourceId() {

        return R.layout.fragment_arrival_asset_query;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

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

        FetchUtil.fetchCompanyList(spCompany);
        spCompany.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                spDept.clear();
                FetchUtil.fetchDeptList(spDept, spCompany.getSelectedValue());
            }
        });
    }

    private void initSpAssetSort() {

        List<SpinnerData> spinnerDataList = new ArrayList<>();
        spinnerDataList.add(new SpinnerData("1", "办公"));
        spinnerDataList.add(new SpinnerData("2", "租赁"));
        spAssetSort.setSpinnerDataList(spinnerDataList);
    }

}


