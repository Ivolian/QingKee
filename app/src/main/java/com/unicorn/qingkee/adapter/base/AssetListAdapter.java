package com.unicorn.qingkee.adapter.base;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;


import com.unicorn.qingkee.bean.Asset;

import java.util.ArrayList;
import java.util.List;


public abstract class AssetListAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected Activity activity;

    protected ArrayList<Asset> assetList;

    protected AssetListAdapter(Activity activity) {

        this.activity = activity;
        this.assetList = new ArrayList<>();
    }

    public void setAssetList(ArrayList<Asset> assetList) {

        this.assetList = assetList;
    }

    public void addAssetList(List<Asset> assetList) {

        this.assetList.addAll(assetList);
    }

    @Override
    public int getItemCount() {
        return assetList.size();
    }

    public ArrayList<Asset> getAssetList() {
        return assetList;
    }

}
