package com.unicorn.qingkee.bean;


import java.io.Serializable;

public class Asset implements Serializable {

    String id;
    String assetName;
    String companyName;
    Double assetCost;
    String assetSort;
    String installPosition;
    String roomNumber;
    String barcode;

    //

    public Asset(String id, String assetName, String companyName, Double assetCost, String assetSort, String installPosition, String roomNumber, String barcode) {
        this.id = id;
        this.assetName = assetName;
        this.companyName = companyName;
        this.assetCost = assetCost;
        this.assetSort = assetSort;
        this.installPosition = installPosition;
        this.roomNumber = roomNumber;
        this.barcode = barcode;
    }

    //

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Double getAssetCost() {
        return assetCost;
    }

    public void setAssetCost(Double assetCost) {
        this.assetCost = assetCost;
    }

    public String getAssetSort() {
        return assetSort;
    }

    public void setAssetSort(String assetSort) {
        this.assetSort = assetSort;
    }

    public String getInstallPosition() {
        return installPosition;
    }

    public void setInstallPosition(String installPosition) {
        this.installPosition = installPosition;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}
