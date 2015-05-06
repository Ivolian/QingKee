package com.unicorn.qingkee.adapter.list;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unicorn.qingkee.R;
import com.unicorn.qingkee.adapter.base.AssetListAdapter;
import com.unicorn.qingkee.bean.Asset;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class AssetsListAdapter extends AssetListAdapter<AssetsListAdapter.ViewHolder> {

    public AssetsListAdapter(Activity activity) {

        super(activity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.activity_assets_list_item, viewGroup, false);

        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.tv_asset_name)
        TextView tvAssetName;

        @InjectView(R.id.tv_company_name)
        TextView tvCompanyName;

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
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        Asset asset = assetList.get(position);
        viewHolder.tvAssetName.setText("资产名称： " + asset.getAssetName());
        viewHolder.tvCompanyName.setText("所属公司： " + asset.getCompanyName());
        viewHolder.tvAssetCost.setText("购入价格： " + asset.getAssetCost());
        viewHolder.tvAssetSort.setText("资产分类： " + Asset.getAssetSortText(asset.getAssetSort()));
        viewHolder.tvInstallPosition.setText("安装地址： " + asset.getInstallPosition());
        viewHolder.tvRoomNumber.setText("房间号： " + asset.getRoomNumber());
    }
}
