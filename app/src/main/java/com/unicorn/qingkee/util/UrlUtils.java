package com.unicorn.qingkee.util;


// 1. 领用 == 闲置，本公司的人领用，领用部门，领用人
// 2. 转移: 本公司，被本人
// 3. 调拨: 跨公司，两边的资产管理员
// 4.


// 资产报废也是本公司


public class UrlUtils {

    static public final String SF_SERVER_ADDRESS = "server_address";

    static private final String DEFAULT_SERVER_ADDRESS = "115.28.222.110:9000";

    static private String serverAddress;

    //

    public static String getServerAddress() {

        if (serverAddress == null) {
            serverAddress = SharedPreferencesUtils.getString(SF_SERVER_ADDRESS, DEFAULT_SERVER_ADDRESS);
        }
        return serverAddress;
    }

    public static void setServerAddress(String serverAddress) {

        UrlUtils.serverAddress = serverAddress;
    }

    public static String getBaseUrl() {

        return "http://" + getServerAddress() + "/Mobile/interface";
    }


    public static String getUploadImagesUrl() {

        return "http://" + getServerAddress() + "/UploadFiles/AssetsImages";
    }

}
