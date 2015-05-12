package com.unicorn.qingkee.volley.toolbox;

import com.android.volley.NetworkError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

// 旧版本，备份
public class VolleyErrorHelper2 {

    // 手机未连接到网络
    public static final int NETWORK_UNREACHABLE = 0;

    private static final String NETWORK_UNREACHABLE_STRING = "Network is unreachable";

    // 连接被拒（服务器未开启）
    public static final int CONNECTION_REFUSED = 1;

    private static final String CONNECTION_REFUSED_STRING = "Connection refused";

    // 连接超时（windows 防火墙未关闭，或者服务器来不及处理请求时，IP地址不正确）
    public static final int CONNECTION_TIMEOUT = 2;

    // 比如 404错误，500错误，只知道这两个
    public static final int SERVER_ERROR = 3;

    public static int getErrorType(VolleyError volleyError) {

        if (volleyError instanceof NetworkError) {
            String errorMessage = volleyError.getMessage();
            if (errorMessage.contains(NETWORK_UNREACHABLE_STRING)) {
                return NETWORK_UNREACHABLE;
            }
            if (errorMessage.contains(CONNECTION_REFUSED_STRING)) {
                return CONNECTION_REFUSED;
            }
        }

        if (volleyError instanceof TimeoutError) {
            return CONNECTION_TIMEOUT;
        }

        if (volleyError instanceof ServerError) {
            return SERVER_ERROR;
        }

        return -1;
    }

    public static String getErrorMessage(VolleyError volleyError) {

        int errorType = getErrorType(volleyError);
        switch (errorType) {
            case NETWORK_UNREACHABLE:
                return "手机未连接到网络";
            case CONNECTION_REFUSED:
                return "连接被拒，请联系开发人员。";
            case CONNECTION_TIMEOUT:
                return "连接超时，请稍后再试。";   // 事实上，Volley 自带重试机制
            case SERVER_ERROR:
                int statusCode = volleyError.networkResponse.statusCode;
                if (statusCode == 404) {
                    return "404错误";
                } else if (statusCode == 500) {
                    return "500错误";
                }
            default:
                return "未知错误";
        }
    }

    // Handle your error types accordingly.For Timeout & No connection error, you can show 'retry' button.
    // For AuthFailure, you can re login with user credentials.
    // For ClientError, 400 & 401, Errors happening on client side when sending api request.
    // In this case you can check how client is forming the api and debug accordingly.
    // For ServerError 5xx, you can do retry or handle accordingly.
//    if( error instanceof NetworkError) {
//    } else if( error instanceof ClientError) {
//    } else if( error instanceof ServerError) {
//    } else if( error instanceof AuthFailureError) {
//    } else if( error instanceof ParseError) {
//    } else if( error instanceof NoConnectionError) {
//    } else if( error instanceof TimeoutError) {
//    }


}