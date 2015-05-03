package com.unicorn.qingkee.mycode;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.unicorn.qingkee.util.JSONUtils;
import com.unicorn.qingkee.util.ToastUtils;
import com.unicorn.qingkee.util.UrlUtils;
import com.unicorn.qingkee.volley.MyVolley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/5/3.
 */
public class FetchUtil {

    public static void fetchCompanyList(final BetterSpinner spCompany) {

        MyVolley.getRequestQueue().add(new JsonObjectRequest(UrlUtils.getBaseUrl() + "/GetCompany",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        int result = JSONUtils.getInt(response, "Result", 1);
                        if (result != 0) {
                            ToastUtils.show(JSONUtils.getString(response, "Msg", ""));
                        } else {
                            JSONArray companyJSONArray = JSONUtils.getJSONArray(response, "lstCompany", null);
                            List<SpinnerData> spinnerDataList = new ArrayList<>();
                            for (int i = 0; i != companyJSONArray.length(); i++) {
                                JSONObject jsonObject = JSONUtils.getJSONObject(companyJSONArray, i);
                                String companyId = JSONUtils.getString(jsonObject, "ID", "");
                                String companyName = JSONUtils.getString(jsonObject, "Commanyname", "");
                                spinnerDataList.add(new SpinnerData(companyId, companyName));
                            }
                            spCompany.setSpinnerDataList(spinnerDataList);
                        }
                    }
                },
                MyVolley.getDefaultErrorListener()));
    }

    public static void fetchDeptList(final BetterSpinner spDept, final String companyId) {

        String url = UrlUtils.getBaseUrl() + "/GetDept?companyid=" + companyId;
        MyVolley.getRequestQueue().add(new JsonObjectRequest(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        int result = JSONUtils.getInt(response, "Result", 1);
                        if (result != 0) {
                            ToastUtils.show(JSONUtils.getString(response, "Msg", ""));
                        } else {
                            JSONArray deptJSONArray = JSONUtils.getJSONArray(response, "lstDept", null);
                            List<SpinnerData> spinnerDataList = new ArrayList<>();
                            for (int i = 0; i != deptJSONArray.length(); i++) {
                                JSONObject jsonObject = JSONUtils.getJSONObject(deptJSONArray, i);
                                String deptId = JSONUtils.getString(jsonObject, "ID", "");
                                String deptName = JSONUtils.getString(jsonObject, "Deptname", "");
                                spinnerDataList.add(new SpinnerData(deptId, deptName));
                            }
                            spDept.setSpinnerDataList(spinnerDataList);
                        }
                    }
                },
                MyVolley.getDefaultErrorListener()));
    }

    public static void fetchEmployeeList(final BetterSpinner spEmployee, final String companyId, final String deptId) {
        String url = UrlUtils.getBaseUrl() + "/GetEmployee?companyid=" + companyId + "&deptid=" + deptId;
        MyVolley.getRequestQueue().add(new JsonObjectRequest(url,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                int result = JSONUtils.getInt(response, "Result", 1);
                                if (result != 0) {
                                    ToastUtils.show(JSONUtils.getString(response, "Msg", ""));
                                } else {
                                    JSONArray deptJSONArray = JSONUtils.getJSONArray(response, "lstEmployee", null);
                                    List<SpinnerData> spinnerDataList = new ArrayList<>();
                                    for (int i = 0; i != deptJSONArray.length(); i++) {
                                        JSONObject jsonObject = JSONUtils.getJSONObject(deptJSONArray, i);
                                        String deptId = JSONUtils.getString(jsonObject, "ID", "");
                                        String deptName = JSONUtils.getString(jsonObject, "Employeename", "");
                                        spinnerDataList.add(new SpinnerData(deptId, deptName));
                                    }
                                    spEmployee.setSpinnerDataList(spinnerDataList);
                                }
                            }
                        },
                        MyVolley.getDefaultErrorListener())
        );
    }

}
