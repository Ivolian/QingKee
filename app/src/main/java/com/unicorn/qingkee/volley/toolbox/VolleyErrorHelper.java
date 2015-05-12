package com.unicorn.qingkee.volley.toolbox;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

public class VolleyErrorHelper {

    public static String getErrorMessage(VolleyError volleyError) {

        if (volleyError instanceof NoConnectionError) {
            return "手机未连接到网络";
        } else if (volleyError instanceof ServerError) {
            return "服务器内部错误，错误码:" + volleyError.networkResponse.statusCode;
        } else if (volleyError instanceof AuthFailureError) {
            return "AuthFailureError";
        } else if (volleyError instanceof ParseError) {
            return "ParseError";
        } else if (volleyError instanceof TimeoutError) {
            return "连接超时，请稍后再试";
        } else {
            return "未知错误";
        }
    }

}