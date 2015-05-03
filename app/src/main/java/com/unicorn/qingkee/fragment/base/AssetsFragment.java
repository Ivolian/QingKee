package com.unicorn.qingkee.fragment.base;

import android.content.Intent;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.unicorn.qingkee.R;
import com.unicorn.qingkee.activity.asset.AssetsListActivity;
import com.unicorn.qingkee.bean.Asset;
import com.unicorn.qingkee.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;


public abstract class AssetsFragment extends BaseFragment {

    MaterialDialog progressDialog;

    final int ASSETS_REQUEST_CODE = 2333;

    ArrayList<Asset> assetList = new ArrayList<>();

    @InjectView(R.id.et_assets)
    public MaterialEditText etAssets;

    abstract public String getAssetStatus();

    abstract public String getTitle();

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data == null) {
            return;
        }
        assetList = (ArrayList<Asset>) data.getSerializableExtra("assetList");
        etAssets.setText(getAssetsText());
    }

    @OnClick(R.id.et_assets)
    public void startAssetDisplayActivity() {

        Intent intent = new Intent(getActivity(), AssetsListActivity.class);
        intent.putExtra("title", getTitle());
        intent.putExtra("assetStatus", getAssetStatus());
        intent.putExtra("assetList", assetList);
        startActivityForResult(intent, ASSETS_REQUEST_CODE);
    }

    public String getAssetsText() {

        String text = "";
        for (int i = 0, size = assetList.size(); i != size; i++) {
            String assetName = assetList.get(i).getAssetName();
            text += assetName;
            if (i != size - 1) {
                text += " , ";
            }
        }
        return text;
    }

    public String getBarcodes() {

        List<String> barcodeList = new ArrayList<>();
        for (Asset asset : assetList) {
            barcodeList.add(asset.getBarcode());
        }
        return StringUtils.join(barcodeList.toArray(), '|');
    }


    public void showProgressDialog() {

        if (progressDialog == null) {
            progressDialog = new MaterialDialog.Builder(getActivity())
                    .theme(Theme.LIGHT)
                    .title("处理中...")
                    .content("请稍后...")
                    .cancelable(false)
                    .progress(true, 0)
                    .show();
        } else {
            progressDialog.show();
        }
    }

    public void hideProgressDialog() {

        progressDialog.dismiss();
    }

}
