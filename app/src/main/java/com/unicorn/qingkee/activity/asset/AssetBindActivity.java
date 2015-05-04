package com.unicorn.qingkee.activity.asset;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.PopupMenu;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import com.lid.lib.LabelView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.unicorn.qingkee.MyApplication;
import com.unicorn.qingkee.R;
import com.unicorn.qingkee.activity.base.ToolbarActivity;
import com.unicorn.qingkee.activity.other.PhotoPagerActivity;
import com.unicorn.qingkee.bean.Asset;
import com.unicorn.qingkee.bean.AssetBindInfo;
import com.unicorn.qingkee.mycode.ImageUtil;
import com.unicorn.qingkee.mycode.UploadImageView;
import com.unicorn.qingkee.util.StringUtils;
import com.unicorn.qingkee.util.ToastUtils;
import com.unicorn.qingkee.util.UrlUtils;


import org.apache.http.Header;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;


// todo todo 有待进一步优化

public class AssetBindActivity extends ToolbarActivity {

    final int TAKE_PHOTO_REQUEST_CODE = 2333;

    final int PHOTO_WIDTH = dpToPx(135);

    final int PHOTO_HEIGHT = dpToPx(200);

    private Asset asset;

    private String currentPhotoPath;

    @InjectView(R.id.asset_name)
    MaterialEditText etAssetName;

    @InjectView(R.id.barcode)
    MaterialEditText etBarcode;

    @InjectView(R.id.photo_container)
    LinearLayout llPhotoContainer;

    @Override
    public int getLayoutResourceId() {

        return R.layout.activity_asset_bind;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        asset = (Asset) getIntent().getSerializableExtra("asset");

        initToolbar("资产绑定", true);
        etAssetName.setText(asset.getAssetName());
    }

    @OnClick(R.id.btn_scan_barcode)
    public void scanBarcode() {

        new IntentIntegrator(this).initiateScan();
    }

    @OnClick(R.id.btn_take_photo)
    public void takePhoto() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri randomUri = ImageUtil.getRandomPhotoUri();
        currentPhotoPath = randomUri.getPath();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, randomUri);
        startActivityForResult(intent, TAKE_PHOTO_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // 处理扫描条码返回结果
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null && result.getContents() != null) {
            etBarcode.setText(result.getContents());
        }

        // 处理拍照返回结果
        if (requestCode == TAKE_PHOTO_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                afterTakePhoto();
            }
        }
    }

    @OnClick(R.id.btn_confirm)
    public void confirm() {

        if (etBarcode.getText().toString().equals("")) {
            ToastUtils.show("请先扫描条码");
            return;
        }

        List<String> photoNameList = new ArrayList<>();
        for (int i = 0; i != llPhotoContainer.getChildCount(); i++) {
            FrameLayout frameLayout = (FrameLayout) llPhotoContainer.getChildAt(i);
            UploadImageView ivPhoto = (UploadImageView) frameLayout.getChildAt(0);
            if (ivPhoto.isUploading()) {
                ToastUtils.show("请等待照片上传完毕");
                return;
            }

            String photoPath = ivPhoto.getCompressedPhotoPath();
            String photoName = photoPath.substring(photoPath.lastIndexOf("/") + 1, photoPath.length());
            photoNameList.add(photoName);
        }
        if (photoNameList.size() == 0) {
            ToastUtils.show("请先上传照片");
            return;
        }

        //
        AssetBindInfo assetBindInfo = new AssetBindInfo();
        assetBindInfo.setUserId(MyApplication.getInstance().getUserInfo().getUserId());
        assetBindInfo.setAssetId(asset.getId());
        assetBindInfo.setBarcode(etBarcode.getText().toString().trim());
        assetBindInfo.setPictures(StringUtils.join(photoNameList.toArray(), '|'));

        ToastUtils.show(StringUtils.join(photoNameList.toArray(), '|'));

//        final ProgressDialog progressDialog = ProgressDialog.show(this, "处理中...", "请稍后...", true);
//        String url = UrlUtils.getBaseUrl() + "/BindAsset?" + assetBindInfo.toUrl();
//        MyVolley.getRequestQueue().add(new JsonObjectRequest(url,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        progressDialog.dismiss();
//                        int result = JSONUtils.getInt(response, "Result", 1);
//                        if (result != 0) {
//                            ToastUtils.show(JSONUtils.getString(response, "Msg", ""));
//                        } else {
//                            ToastUtils.show("绑定成功");
//                            startActivity(ArrivalAssetListActivity.class);
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError volleyError) {
//                        progressDialog.dismiss();
//                        ToastUtils.show(VolleyErrorHelper.getErrorMessage(volleyError));
//                    }
//                }));
    }

    private void afterTakePhoto() {

        if (currentPhotoPath == null) {
            return;
        }

        String compressedPhotoPath = ImageUtil.compressPhoto(currentPhotoPath);
        // 高分辨率的手机，好像必须要这样做
        BitmapFactory.Options factoryOptions = ImageUtil.getFactoryOptions(PHOTO_WIDTH, PHOTO_HEIGHT, currentPhotoPath);
        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, factoryOptions);

        final UploadImageView ivPhoto = new UploadImageView(this, compressedPhotoPath);
        ivPhoto.setImageBitmap(bitmap);
        ivPhoto.setScaleType(ImageView.ScaleType.FIT_XY);
        llPhotoContainer.addView(ivPhoto, PHOTO_WIDTH, PHOTO_HEIGHT);

        // 上传
        LabelView uploadingView = addUploadingLabel(ivPhoto);
        uploadPhoto(ivPhoto, uploadingView);

        // 展示照片
        ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int photoIndex = -1;
                ArrayList<String> photoPathList = new ArrayList<>();
                for (int i = 0; i != llPhotoContainer.getChildCount(); i++) {
                    FrameLayout frameLayout = (FrameLayout) llPhotoContainer.getChildAt(i);
                    UploadImageView ivPhoto = (UploadImageView) frameLayout.getChildAt(0);
                    if (ivPhoto == v) {
                        photoIndex = i;
                    }
                    photoPathList.add(ivPhoto.getCompressedPhotoPath());
                }

                Intent intent = new Intent(AssetBindActivity.this, PhotoPagerActivity.class);
                intent.putExtra("photoPathList", photoPathList);
                intent.putExtra("photoIndex", photoIndex);
                startActivity(intent);
            }
        });

        // 删除照片
        ivPhoto.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                PopupMenu popupMenu = new PopupMenu(AssetBindActivity.this, v);
                popupMenu.inflate(R.menu.remove_photo_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.remove_photo:
                                llPhotoContainer.removeView((View) v.getParent());
                                return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
                return true;
            }
        });
    }

    // low level methods

    private void uploadPhoto(final UploadImageView ivPhoto, final LabelView labelUploading) {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        try {
            requestParams.put("hpf", new File(ivPhoto.getCompressedPhotoPath()));
        } catch (Exception e) {
            //
        }
        String url = UrlUtils.getBaseUrl() + "/AssetsImagesUpload";
        client.post(url, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {

                ivPhoto.setUploading(false);
                labelUploading.remove();
                addSuccessLabel(ivPhoto);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                ivPhoto.setUploading(false);
                labelUploading.remove();
                addFailureLabel(ivPhoto);
            }
        });
    }

    public int dpToPx(int dp) {

        DisplayMetrics displayMetrics = MyApplication.getInstance().getResources().getDisplayMetrics();
        return (int) ((dp * displayMetrics.density) + 0.5);
    }

    private LabelView addUploadingLabel(UploadImageView ivPhoto) {

        LabelView label = new LabelView(this);
        label.setText("上传中");
        label.setBackgroundColor(0xffff7342);
        label.setTargetView(ivPhoto, 10, LabelView.Gravity.LEFT_TOP);
        return label;
    }

    private void addSuccessLabel(UploadImageView ivPhoto) {

        LabelView label = new LabelView(AssetBindActivity.this);
        label.setText("成功");
        label.setBackgroundColor(0xff2ab081);
        label.setTargetView(ivPhoto, 10, LabelView.Gravity.LEFT_TOP);
    }

    private void addFailureLabel(UploadImageView ivPhoto) {

        LabelView label = new LabelView(AssetBindActivity.this);
        label.setText("失败");
        label.setBackgroundColor(0xfff56773);
        label.setTargetView(ivPhoto, 10, LabelView.Gravity.LEFT_TOP);
    }

}
