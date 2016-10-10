package com.unicorn.qingkee.fragment.base;

import android.content.Intent;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.unicorn.qingkee.R;
import com.unicorn.qingkee.activity.asset.AssetTransferOutListActivity;
import com.unicorn.qingkee.activity.asset.AssetsListActivity;
import com.unicorn.qingkee.activity.base.ToolbarActivity;
import com.unicorn.qingkee.bean.Asset;
import com.unicorn.qingkee.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;


public abstract class AssetsFragment extends BaseFragment {

    final int ASSETS_REQUEST_CODE = 2333;

    @InjectView(R.id.et_assets)
    public MaterialEditText etAssets;

    public ArrayList<Asset> assetList = new ArrayList<>();


    public boolean isTransferOut() {
        return false;
    }

    @OnClick(R.id.et_assets)
    public void startAssetDisplayActivity() {

        Intent intent = new Intent(getActivity(), isTransferOut() ? AssetTransferOutListActivity.class : AssetsListActivity.class);
        intent.putExtra("title", ((ToolbarActivity) getActivity()).getToolbarTitle());
        intent.putExtra("assetStatus", getAssetStatus());
        intent.putExtra("assetList", assetList);
        // http://stackoverflow.com/questions/6147884/onactivityresult-not-being-called-in-fragment
        startActivityForResult(intent, ASSETS_REQUEST_CODE);
    }

    abstract public String getAssetStatus();

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            ArrayList<Asset> tempList = (ArrayList<Asset>) data.getSerializableExtra("assetList");
            if (tempList != null) {
                assetList = tempList;
                etAssets.setText(getAssetsText());
            }
        }
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
}
