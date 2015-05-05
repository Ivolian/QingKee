package com.unicorn.qingkee.adapter.list;

import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.unicorn.qingkee.R;
import com.unicorn.qingkee.bean.Inventory;
import com.unicorn.qingkee.fragment.asset.AssetInventoryListFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class AssetInventoryListAdapter extends RecyclerView.Adapter<AssetInventoryListAdapter.ViewHolder> {

    private AssetInventoryListFragment assetInventoryListFragment;

    private List<Inventory> inventoryList = new ArrayList<>();

    public AssetInventoryListAdapter(AssetInventoryListFragment assetInventoryListFragment) {
        this.assetInventoryListFragment = assetInventoryListFragment;
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

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    PopupMenu popupMenu = new PopupMenu(assetInventoryListFragment.getActivity(), v);
                    popupMenu.inflate(R.menu.menu_inventory);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()) {
                                case R.id.inventory:
                                    assetInventoryListFragment.currentInventoryBatch = inventoryList.get(getAdapterPosition()).getInventoryBatch();
                                    IntentIntegrator.forSupportFragment(assetInventoryListFragment).initiateScan();
                                    return true;
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        Inventory inventory = inventoryList.get(position);
        viewHolder.tvInventoryBatch.setText("盘点批次号： " + inventory.getInventoryBatch());
        viewHolder.tvDescription.setText("描述： " + inventory.getDescription());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-m-d hh:mm:ss");
        viewHolder.tvPublishDate.setText("发布日期： " + simpleDateFormat.format(inventory.getPublishDate()));
    }

    public List<Inventory> getInventoryList() {
        return inventoryList;
    }

    public void setInventoryList(List<Inventory> inventoryList) {
        this.inventoryList = inventoryList;
    }
}
