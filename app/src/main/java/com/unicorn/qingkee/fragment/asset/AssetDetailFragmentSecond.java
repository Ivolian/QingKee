package com.unicorn.qingkee.fragment.asset;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unicorn.qingkee.R;
import com.unicorn.qingkee.activity.asset.AssetDetailActivity;
import com.unicorn.qingkee.adapter.list.AssetDetailListAdapter;
import com.unicorn.qingkee.bean.Asset;
import com.unicorn.qingkee.fragment.base.BaseFragment;

import java.util.List;

import butterknife.InjectView;


public class AssetDetailFragmentSecond extends BaseFragment {

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_asset_detail_second;
    }

    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        Asset asset = ((AssetDetailActivity) getActivity()).asset;
        initRecyclerView(asset.getPictureList());

        return view;
    }

    private void initRecyclerView(List<String> photoList) {

        recyclerView.setLayoutManager(getLinearLayoutManager());
        recyclerView.setAdapter(new AssetDetailListAdapter(photoList));
    }

    private LinearLayoutManager getLinearLayoutManager() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return linearLayoutManager;
    }

}
