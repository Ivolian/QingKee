package com.unicorn.qingkee.fragment.asset;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.unicorn.qingkee.R;
import com.unicorn.qingkee.activity.asset.AssetDetailActivity;
import com.unicorn.qingkee.bean.Asset;
import com.unicorn.qingkee.fragment.base.BaseFragment;

import java.text.SimpleDateFormat;

import butterknife.InjectView;


public class AssetDetailFragmentFirst extends BaseFragment {

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_asset_detail_first;
    }

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        Asset asset = ((AssetDetailActivity) getActivity()).asset;
        if (asset != null) {
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

        return view;
    }


}
