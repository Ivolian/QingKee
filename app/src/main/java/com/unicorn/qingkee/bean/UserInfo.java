package com.unicorn.qingkee.bean;


import com.unicorn.qingkee.util.JSONUtils;

import org.json.JSONObject;

import java.io.Serializable;


public class UserInfo implements Serializable {

    private String userId;
    private String loginCode;
    private String username;
    private String password;
    private String companyId;
    private String deptId;
    private String telephone;
    private String Email;
    private Integer loginCount;

    // getter & setter

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    //

    public static UserInfo parse(JSONObject jsonObject) {

        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(JSONUtils.getString(jsonObject, "Userid", ""));
        userInfo.setLoginCode(JSONUtils.getString(jsonObject, "Logincode", ""));
        userInfo.setUsername(JSONUtils.getString(jsonObject, "Username", ""));
        userInfo.setPassword(JSONUtils.getString(jsonObject, "Password", ""));
        userInfo.setCompanyId(JSONUtils.getString(jsonObject, "Company", ""));
        userInfo.setDeptId(JSONUtils.getString(jsonObject, "Dept", ""));
        userInfo.setTelephone(JSONUtils.getString(jsonObject, "Telephone", ""));
        userInfo.setEmail(JSONUtils.getString(jsonObject, "Email", ""));
        userInfo.setLoginCount(JSONUtils.getInt(jsonObject, "Logincount", 0));
        return userInfo;
    }

}
