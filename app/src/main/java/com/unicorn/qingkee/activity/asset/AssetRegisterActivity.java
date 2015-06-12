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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
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
import com.unicorn.qingkee.activity.other.PhotoActivity;
import com.unicorn.qingkee.mycode.BetterSpinner;
import com.unicorn.qingkee.mycode.FetchUtil;
import com.unicorn.qingkee.mycode.ImageUtil;
import com.unicorn.qingkee.mycode.SpinnerData;
import com.unicorn.qingkee.mycode.UploadImageView;
import com.unicorn.qingkee.util.JSONUtils;
import com.unicorn.qingkee.util.StringUtils;
import com.unicorn.qingkee.util.TimeUtils;
import com.unicorn.qingkee.util.ToastUtils;
import com.unicorn.qingkee.util.UrlUtils;
import com.unicorn.qingkee.volley.MyVolley;
import com.unicorn.qingkee.volley.toolbox.VolleyErrorHelper;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnFocusChange;


public class AssetRegisterActivity extends ToolbarActivity {

    final int TAKE_PHOTO_REQUEST_CODE = 2333;

    final int PHOTO_WIDTH = dpToPx(135);

    final int PHOTO_HEIGHT = dpToPx(200);

    private String currentPhotoPath;

    @InjectView(R.id.asset_name)
    MaterialEditText etAssetName;

    @InjectView(R.id.asset_brand)
    MaterialEditText etAssetBrand;

    @InjectView(R.id.asset_models)
    MaterialEditText etAssetModels;

    @InjectView(R.id.asset_factory_date)
    MaterialEditText etAssetFactoryDate;

    Date factoryDate = new Date();

    @InjectView(R.id.asset_address)
    MaterialEditText etAssetAddress;

    @InjectView(R.id.asset_room_number)
    MaterialEditText etAssetRoomNumber;

    @InjectView(R.id.asset_install_position)
    MaterialEditText etAssetInstallPosition;

    @InjectView(R.id.asset_notes)
    MaterialEditText etAssetNotes;

    @InjectView(R.id.barcode)
    MaterialEditText etBarcode;

    @InjectView(R.id.photo_container)
    LinearLayout llPhotoContainer;

    @InjectView(R.id.sp_dept)
    BetterSpinner spDept;

    @InjectView(R.id.sp_asset_sort)
    BetterSpinner spAssetSort;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_asset_register;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        initToolbar("资产登记", true);
        initViews();
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

        if (etBarcode.getText().toString().equals(StringUtils.EMPTY)) {
            ToastUtils.show("请先扫描条码");
            return;
        }
        if (spAssetSort.getSelectedValue().equals(StringUtils.EMPTY)) {
            ToastUtils.show("资产类别不能为空");
            return;
        }
        if (etAssetName.getText().toString().equals(StringUtils.EMPTY)) {
            ToastUtils.show("资产名称不能为空");
            return;
        }
        if (etAssetBrand.getText().toString().equals(StringUtils.EMPTY)) {
            ToastUtils.show("资产品牌不能为空");
            return;
        }
        if (etAssetModels.getText().toString().equals(StringUtils.EMPTY)) {
            ToastUtils.show("资产型号不能为空");
            return;
        }
        if (etAssetAddress.getText().toString().equals(StringUtils.EMPTY)
                && spAssetSort.getSelectedValue().equals("2")) {
            ToastUtils.show("租赁类资产地址不能为空");
            return;
        }
        if (etAssetRoomNumber.getText().toString().equals(StringUtils.EMPTY)
                && spAssetSort.getSelectedValue().equals("2")) {
            ToastUtils.show("租赁类资产门牌号不能为空");
            return;
        }
        if (spDept.getSelectedValue().equals(StringUtils.EMPTY)) {
            ToastUtils.show("使用部门不能为空");
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
        if (photoNameList.size() == 0 && spAssetSort.getSelectedValue().equals("2")) {
            ToastUtils.show("租赁类资产至少拍摄一张照片");
            return;
        }

        //
        Uri.Builder builder = Uri.parse(UrlUtils.getBaseUrl() + "/RegisterAsset?").buildUpon();
        builder.appendQueryParameter("assetname", etAssetName.getText().toString().trim());
        builder.appendQueryParameter("barcode", etBarcode.getText().toString().trim());
        builder.appendQueryParameter("brand", etAssetBrand.getText().toString().trim());
        builder.appendQueryParameter("models", etAssetModels.getText().toString().trim());
        builder.appendQueryParameter("factorydate", TimeUtils.getTime(factoryDate.getTime(), TimeUtils.DATE_FORMAT_DATE));
        builder.appendQueryParameter("pictures", StringUtils.join(photoNameList.toArray(), '|'));
        builder.appendQueryParameter("address", etAssetAddress.getText().toString().trim());
        builder.appendQueryParameter("roomnumber", etAssetRoomNumber.getText().toString().trim());
        builder.appendQueryParameter("installposition", etAssetInstallPosition.getText().toString().trim());
        builder.appendQueryParameter("assetsort", spAssetSort.getSelectedValue());
        builder.appendQueryParameter("deptid", spDept.getSelectedValue());
        builder.appendQueryParameter("companyid",MyApplication.getInstance().getUserInfo().getCompanyId());
        builder.appendQueryParameter("notes", etAssetNotes.getText().toString().trim());
        builder.appendQueryParameter("userid", MyApplication.getInstance().getUserInfo().getUserId());

        MyVolley.getRequestQueue().add(new JsonObjectRequest(builder.toString(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideProgressDialog();
                        int result = JSONUtils.getInt(response, "Result", 1);
                        if (result != 0) {
                            ToastUtils.show(JSONUtils.getString(response, "Msg", ""));
                        } else {
                            ToastUtils.show("登记成功");
                            finish();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        hideProgressDialog();
                        ToastUtils.show(VolleyErrorHelper.getErrorMessage(volleyError));
                    }
                }));
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
                Intent intent = new Intent(AssetRegisterActivity.this, PhotoActivity.class);
                intent.putExtra("photoPath", currentPhotoPath);
                startActivity(intent);
            }
        });

        // 删除照片
        ivPhoto.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                PopupMenu popupMenu = new PopupMenu(AssetRegisterActivity.this, v);
                popupMenu.inflate(R.menu.menu_remove_photo);
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

        LabelView label = new LabelView(AssetRegisterActivity.this);
        label.setText("成功");
        label.setBackgroundColor(0xff2ab081);
        label.setTargetView(ivPhoto, 10, LabelView.Gravity.LEFT_TOP);
    }

    private void addFailureLabel(UploadImageView ivPhoto) {

        LabelView label = new LabelView(AssetRegisterActivity.this);
        label.setText("失败");
        label.setBackgroundColor(0xfff56773);
        label.setTargetView(ivPhoto, 10, LabelView.Gravity.LEFT_TOP);
    }

    private void initViews() {

        etAssetFactoryDate.setText(TimeUtils.getTime(factoryDate.getTime(), TimeUtils.DATE_FORMAT_DATE));
        initSpDept();
        initSpAssetSort();
    }

    private void initSpDept() {

        FetchUtil.fetchDeptList(spDept, MyApplication.getInstance().getUserInfo().getCompanyId());
    }

    private void initSpAssetSort() {

        List<SpinnerData> spinnerDataList = new ArrayList<>();
        spinnerDataList.add(new SpinnerData("1", "办公"));
        spinnerDataList.add(new SpinnerData("2", "租赁"));
        spAssetSort.setSpinnerDataList(spinnerDataList);
    }

    //


    @OnFocusChange(R.id.asset_factory_date)
    public void etFactoryDateOnFocused(boolean focused) {
        if (focused) {
            showDatePickerDialog();
        }
    }

    @OnClick(R.id.asset_factory_date)
    public void etFactoryDateOnClick() {
        showDatePickerDialog();
    }

    private void showDatePickerDialog() {

        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(factoryDate);
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog datePickerDialog, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(year, monthOfYear, dayOfMonth);
                        factoryDate.setTime(calendar.getTimeInMillis());
                        etAssetFactoryDate.setText(TimeUtils.getTime(factoryDate.getTime(), TimeUtils.DATE_FORMAT_DATE));
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show(getFragmentManager(), "FactoryDate");
    }
}
