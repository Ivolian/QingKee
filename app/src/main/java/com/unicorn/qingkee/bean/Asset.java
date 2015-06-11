package com.unicorn.qingkee.bean;

import com.unicorn.qingkee.util.JSONUtils;

import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Asset implements Serializable {

    String id;
    String assetName;
    String assetStatus;
    String brand;
    String models;
    Date buyDate;
    String assetValue;
    String supplierName;
    String address;
    String companyName;
    String deptName;
    String employeeName;
    Double assetCost;
    String assetSort;
    String installPosition;
    String roomNumber;
    String barcode;
    String pictures;
    List<String> pictureList = new ArrayList<>();

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

    public String getAssetStatus() {
        return assetStatus;
    }

    public void setAssetStatus(String assetStatus) {
        this.assetStatus = assetStatus;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getDeptName() {
        return deptName;
    }

    public String getEmployeeName() {
        return employeeName;
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

    public String getPictures() {
        return pictures;
    }

    public List<String> getPictureList() {
        return pictureList;
    }

    //

    public static Asset parse(JSONObject jsonObject) {

        Asset asset = new Asset();
        asset.id = JSONUtils.getString(jsonObject, "ID", "");
        asset.assetName = JSONUtils.getString(jsonObject, "Assetname", "");
        asset.companyName = JSONUtils.getString(jsonObject, "CommanyName", "");
        asset.deptName = JSONUtils.getString(jsonObject, "DeptName", "");
        asset.employeeName = JSONUtils.getString(jsonObject, "EmployeeName", "");
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
        asset.assetStatus = JSONUtils.getString(jsonObject, "Assetstatus", "");

        asset.pictures = JSONUtils.getString(jsonObject, "Pictures", "");
        for (String picture : asset.pictures.split("\\|")) {
            asset.pictureList.add(picture);
        }

        return asset;
    }

    public static String getAssetSortText(String assetSort) {

        return assetSort.equals("1") ? "办公" : "租聘";
    }

    public static String getAssetStatusText(String assetStatus) {

        switch (assetStatus) {
            case "01":
                return "到货";
            case "02":
                return "未审核";
            case "03":
                return "闲置";
            case "04":
                return "在用";
            case "05":
                return "外借";
            case "06":
                return "维修";
            case "07":
                return "报废";
            case "08":
                return "退货";
            case "09":
                return "调拨中";
            default:
                return null;
        }
    }
}
