package com.unicorn.qingkee.adapter.asset;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unicorn.qingkee.R;
import com.unicorn.qingkee.activity.asset.AssetBindActivity;
import com.unicorn.qingkee.adapter.base.AssetListAdapter;
import com.unicorn.qingkee.bean.Asset;
import com.unicorn.qingkee.bean.Inventory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class AssetInventoryListAdapter extends RecyclerView.Adapter<AssetInventoryListAdapter.ViewHolder> {

    private Activity activity;

    private List<Inventory> inventoryList = new ArrayList<>();

    public AssetInventoryListAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public int getItemCount() {
        return inventoryList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_asset_inventory_list_item, viewGroup, false);

        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.inventory_batch)
        TextView tvInventoryBatch;

        @InjectView(R.id.description)
        TextView tvDescription;

        @InjectView(R.id.publish_date)
        TextView tvPublishDate;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.inject(this, v);

//            v.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(activity, AssetBindActivity.class);
//                    intent.putExtra("asset", assetList.get(getAdapterPosition()));
//                    activity.startActivity(intent);
//                }
//            });
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        Inventory inventory = inventoryList.get(position);
        viewHolder.tvInventoryBatch.setText("盘点批次号： " + inventory.getInventoryBatch());
        viewHolder.tvDescription.setText("描述： " + inventory.getDescription());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-m-d hh:mm:ss");
        viewHolder.tvPublishDate.setText("出版日期： " + simpleDateFormat.format(inventory.getPublishDate()));
    }

    public List<Inventory> getInventoryList() {
        return inventoryList;
    }

    public void setInventoryList(List<Inventory> inventoryList) {
        this.inventoryList = inventoryList;
    }
}
