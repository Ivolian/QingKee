package com.unicorn.qingkee.fragment.asset;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unicorn.qingkee.R;
import com.unicorn.qingkee.adapter.list.AssetDetailPhotoListAdapter;
import com.unicorn.qingkee.bean.Asset;
import com.unicorn.qingkee.fragment.base.BaseFragment;

import java.util.ArrayList;

import butterknife.InjectView;


// 资产详情照片
public class AssetDetailPhotoFragment extends BaseFragment {

    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_asset_detail_second;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        initRecyclerView();

        return view;
    }

    private void initRecyclerView() {

        recyclerView.setLayoutManager(getLinearLayoutManager());
        Asset asset = (Asset) getActivity().getIntent().getSerializableExtra("asset");
        recyclerView.setAdapter(new AssetDetailPhotoListAdapter(asset == null ? new ArrayList<String>() : asset.getPictureList()));
    }

    private LinearLayoutManager getLinearLayoutManager() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return linearLayoutManager;
    }

}
