package com.unicorn.qingkee.bean;

import java.io.Serializable;


// 方便分页查询
public class AssetQueryInfo implements Serializable {

    private String userId;

    private String assetName;

    private String companyId;

    private String deptId;

    private String employeeName;

    private String address;

    private String romeNumber;

    private String assetSort;

    private String assetStatus;

    private Integer pager;

    private Integer pagerSize;

    // ============= getter & setter ============

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRomeNumber() {
        return romeNumber;
    }

    public void setRomeNumber(String romeNumber) {
        this.romeNumber = romeNumber;
    }

    public String getAssetSort() {
        return assetSort;
    }

    public void setAssetSort(String assetSort) {
        this.assetSort = assetSort;
    }

    public String getAssetStatus() {
        return assetStatus;
    }

    public void setAssetStatus(String assetStatus) {
        this.assetStatus = assetStatus;
    }

    public Integer getPager() {
        return pager;
    }

    public void setPager(Integer pager) {
        this.pager = pager;
    }

    public Integer getPagerSize() {
        return pagerSize;
    }

    public void setPagerSize(Integer pagerSize) {
        this.pagerSize = pagerSize;
    }

    // ============= toUrl ============

    public String toUrl() {

        if (userId == null || assetName == null || companyId == null || deptId == null || employeeName == null || address == null
                || romeNumber == null || assetStatus == null || assetSort == null || pager == null || pagerSize == null) {
            return getClass().getSimpleName() + " instance contains null field!";
        }

        return "userid=" + userId +
                "&assetname=" + assetName +
                "&companyid=" + companyId +
                "&deptid=" + deptId +
                "&employeename=" + employeeName +
                "&address=" + address +
                "&romenumber=" + romeNumber +
                "&assetstatus=" + assetStatus +
                "&assetsort=" + assetSort +
                "&pager=" + pager +
                "&pagersize=" + pagerSize;
    }

}
