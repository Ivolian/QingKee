package com.unicorn.qingkee.bean;


public class AssetBindInfo {

    private String userId;

    private String assetId;

    private String barcode;

    private String pictures;

    // setter & getter

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

    // ============= toUrl ============

    public String toUrl() {

        if (userId == null || assetId == null || barcode == null || pictures == null) {
            throw new RuntimeException(getClass().getSimpleName() + " instance contains null field!");
        }

        return "userid=" + userId +
                "&assetid=" + assetId +
                "&barcode=" + barcode +
                "&pictures=" + pictures;
    }
}
