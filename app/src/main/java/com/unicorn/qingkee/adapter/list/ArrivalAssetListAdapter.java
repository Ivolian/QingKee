package com.unicorn.qingkee.adapter.list;

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

import butterknife.ButterKnife;
import butterknife.InjectView;


public class ArrivalAssetListAdapter extends AssetListAdapter<ArrivalAssetListAdapter.ViewHolder> {

    public ArrivalAssetListAdapter(Activity activity) {

        super(activity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.activity_arrival_asset_list_item, viewGroup, false);

        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.tv_asset_name)
        TextView tvAssetName;

        @InjectView(R.id.tv_dept_name)
        TextView tvDeptName;

        @InjectView(R.id.tv_asset_cost)
        TextView tvAssetCost;

        @InjectView(R.id.tv_asset_sort)
        TextView tvAssetSort;

        @InjectView(R.id.tv_install_position)
        TextView tvInstallPosition;

        @InjectView(R.id.tv_room_number)
        TextView tvRoomNumber;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.inject(this, v);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, AssetBindActivity.class);
                    intent.putExtra("asset", assetList.get(getAdapterPosition()));
                    activity.startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        Asset asset = assetList.get(position);
        viewHolder.tvAssetName.setText("资产名称： " + asset.getAssetName());
        viewHolder.tvDeptName.setText("所属部门： " + asset.getDeptName());
        viewHolder.tvAssetCost.setText("购入价格： " + asset.getAssetCost());
        viewHolder.tvAssetSort.setText("资产分类： " + Asset.getAssetSortText(asset.getAssetSort()));
        viewHolder.tvInstallPosition.setText("安装地址： " + asset.getInstallPosition());
        viewHolder.tvRoomNumber.setText("房间号： " + asset.getRoomNumber());
    }

}
