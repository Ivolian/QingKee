package com.unicorn.qingkee.activity.asset;

import com.unicorn.qingkee.bean.Asset;
import com.unicorn.qingkee.util.ToastUtils;

import java.util.List;


public class AssetTransferOutListActivity extends AssetsListActivity {

    @Override
    public boolean checkAssetList(List<Asset> assetList, Asset current) {
        if (assetList.size() == 0) {
            return true;
        }

        Asset asset = assetList.get(0);
        if (!asset.getAssetSort().equals(current.getAssetSort())) {
            ToastUtils.show("所添加资产必须为同一类别");
            return false;
        }

        return true;
    }

}
