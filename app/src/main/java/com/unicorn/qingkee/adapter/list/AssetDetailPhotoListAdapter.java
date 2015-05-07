package com.unicorn.qingkee.adapter.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.NetworkImageView;
import com.unicorn.qingkee.R;
import com.unicorn.qingkee.util.UrlUtils;
import com.unicorn.qingkee.volley.MyVolley;

import java.io.File;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class AssetDetailPhotoListAdapter extends RecyclerView.Adapter<AssetDetailPhotoListAdapter.ViewHolder> {

    private List<String> photoList;

    public AssetDetailPhotoListAdapter(List<String> photoList) {
        this.photoList = photoList;
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.asset_detail_photo_list_item, viewGroup, false);
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.networkImageView)
        NetworkImageView networkImageView;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.inject(this, v);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        viewHolder.networkImageView.setDefaultImageResId(R.drawable.aio_image_default);
        viewHolder.networkImageView.setErrorImageResId(R.drawable.aio_image_fail);
        String photoUrl = UrlUtils.getUploadImagesUrl() + File.separator + photoList.get(position);
        viewHolder.networkImageView.setImageUrl(photoUrl, MyVolley.getImageLoader());
    }

}
