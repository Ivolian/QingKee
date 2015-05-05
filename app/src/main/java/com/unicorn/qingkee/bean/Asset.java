package com.unicorn.qingkee.bean;

import com.unicorn.qingkee.util.JSONUtils;

import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Asset implements Serializable {

    String id;
    String assetName;
    String brand;
    String models;
    Date buyDate;
    String assetValue;
    String supplierName;
    String address;
    String companyName;
    Double assetCost;
    String assetSort;
    String installPosition;
    String roomNumber;
    String barcode;

    //

    public String getId() {
        return id;
    }

    public String getAssetName() {
        return assetName;
    }

    public String getBrand() {
        return brand;
    }

    public String getModels() {
        return models;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public String getAssetValue() {
        return assetValue;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getAddress() {
        return address;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Double getAssetCost() {
        return assetCost;
    }

    public String getAssetSort() {
        return assetSort;
    }

    public String getInstallPosition() {
        return installPosition;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getBarcode() {
        return barcode;
    }

    //

    public static Asset parse(JSONObject jsonObject) {

        Asset asset = new Asset();
        asset.id = JSONUtils.getString(jsonObject, "ID", "");
        asset.assetName = JSONUtils.getString(jsonObject, "Assetname", "");
        asset.companyName = JSONUtils.getString(jsonObject, "CommanyName", "");
        asset.assetCost = JSONUtils.getDouble(jsonObject, "Assetcost", 0);
        asset.assetSort = JSONUtils.getString(jsonObject, "Assetsort", "");
        asset.installPosition = JSONUtils.getString(jsonObject, "Installposition", "");
        asset.roomNumber = JSONUtils.getString(jsonObject, "Roomnumber", "");
        asset.barcode = JSONUtils.getString(jsonObject, "Barcode", "");
        asset.brand = JSONUtils.getString(jsonObject, "Brand", "");
        asset.models = JSONUtils.getString(jsonObject, "Models", "");
        String dateString = JSONUtils.getString(jsonObject, "Buydate", "");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd\'T\'hh:mm:ss");
        try {
            asset.buyDate = simpleDateFormat.parse(dateString);
        } catch (Exception e) {
            //
        }
        asset.assetValue = JSONUtils.getString(jsonObject, "Assetvalue", "");
        asset.supplierName = JSONUtils.getString(jsonObject, "SupplierName", "");
        asset.address = JSONUtils.getString(jsonObject, "Address", "");
        return asset;
    }
}
