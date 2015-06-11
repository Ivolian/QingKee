package com.unicorn.qingkee.fragment.asset;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.unicorn.qingkee.R;
import com.unicorn.qingkee.bean.Asset;
import com.unicorn.qingkee.fragment.base.BaseFragment;
import com.unicorn.qingkee.util.TimeUtils;

import butterknife.InjectView;


// 资产详情基本信息
public class AssetDetailInfoFragment extends BaseFragment {

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

    @InjectView(R.id.dept_name)
    MaterialEditText etDeptName;

    @InjectView(R.id.employee_name)
    MaterialEditText etEmployeeName;

    @InjectView(R.id.asset_sort)
    MaterialEditText etAssetSort;

    @InjectView(R.id.address)
    MaterialEditText etAddress;

    @InjectView(R.id.room_number)
    MaterialEditText etRoomnumber;

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_asset_detail_info;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        initViews();

        return view;
    }

    private void initViews() {

        Asset asset = (Asset) getActivity().getIntent().getSerializableExtra("asset");
        if (asset != null) {
            etAssetName.setText(asset.getAssetName());
            etAssetStatus.setText(Asset.getAssetStatusText(asset.getAssetStatus()));
            etBrand.setText(asset.getBrand());
            etModels.setText(asset.getModels());
            etModels.setText(asset.getModels());
            etBuyDate.setText(TimeUtils.getTime(asset.getBuyDate().getTime()));
            etAssetValue.setText(asset.getAssetValue());
            etSupplierName.setText(asset.getSupplierName());
            etCompanyName.setText(asset.getCompanyName());
            etDeptName.setText(asset.getDeptName());
            etEmployeeName.setText(asset.getEmployeeName());
            etAssetSort.setText(Asset.getAssetSortText(asset.getAssetSort()));
            etAddress.setText(asset.getAddress());
            etRoomnumber.setText(asset.getRoomNumber());
        }
    }

}
