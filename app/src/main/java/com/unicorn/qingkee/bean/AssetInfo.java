package com.unicorn.qingkee.bean;


public class AssetInfo {

    private String userId;

    private String barcode;

    private String assetStatus;

    // setter & getter

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getAssetStatus() {
        return assetStatus;
    }

    public void setAssetStatus(String assetStatus) {
        this.assetStatus = assetStatus;
    }

// ============= toUrl ============

    public String toUrl() {

        if (userId == null || barcode == null || assetStatus == null) {
            throw new RuntimeException(getClass().getSimpleName() + " instance contains null field!");
        }
        return "userid=" + userId +
                "&barcode=" + barcode +
                "&assetStatus=" + assetStatus;
    }
}
