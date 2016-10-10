package com.unicorn.qingkee.activity.address;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.unicorn.qingkee.R;
import com.unicorn.qingkee.activity.address.event.AddressChooseEvent;
import com.unicorn.qingkee.activity.address.model.Address;
import com.unicorn.qingkee.activity.base.ToolbarActivity;
import com.unicorn.qingkee.util.DensityUtils;
import com.unicorn.qingkee.util.ToastUtils;
import com.unicorn.qingkee.util.UrlUtils;
import com.unicorn.qingkee.volley.MyVolley;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import butterknife.InjectView;


public class AddressSearchActivity extends ToolbarActivity {

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_address_search;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar("选择地址", true);
        initSearchText();
        initRecyclerView();
    }

    @InjectView(R.id.searchText)
    EditText searchText;

    private void initSearchText() {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(10);
        gradientDrawable.setColor(ContextCompat.getColor(this, R.color.md_white));
        searchText.setBackgroundDrawable(gradientDrawable);
        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
                if (arg1 == EditorInfo.IME_ACTION_SEARCH) {
                    search(arg0.getText().toString());
                }
                return false;
            }

        });
    }


    //

    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;

    AddressAdapter adapter;

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AddressAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(getItemDecoration(this));
    }

    public static RecyclerView.ItemDecoration getItemDecoration(Context context) {
        int dividerColor = ContextCompat.getColor(context, R.color.md_grey_300);
        int marginPx = DensityUtils.dip2px(context, 20);
        return new HorizontalDividerItemDecoration.Builder(context)
                .color(dividerColor)
                .size(1)
                .margin(marginPx, marginPx)
                .build();
    }

    //

    private void search(String keyword) {
        String url = getUrl(keyword);
        Request request = new StringRequest(
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            copeResponse(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                MyVolley.getDefaultErrorListener()
        );
        MyVolley.getRequestQueue().add(request);
    }

    private void copeResponse(String responseString) throws Exception {
        JSONObject response = new JSONObject(responseString);
        int result = response.getInt("Result");
        if (result != 0) {
            return;
        }
        JSONArray lstAddress = response.getJSONArray("lstAddress");
        List<Address> addressList = new Gson().fromJson(lstAddress.toString(), new TypeToken<List<Address>>() {
        }.getType());
        adapter.setAddressList(addressList);
        adapter.notifyDataSetChanged();
        if (addressList.size() == 0){
            ToastUtils.show("无符合查询条件的地址");
        }
    }

    private String getUrl(String keyword) {
        Uri.Builder builder = Uri.parse(UrlUtils.getBaseUrl() + "/GetAddress?").buildUpon();
        builder.appendQueryParameter("keyaddr", keyword);
        builder.appendQueryParameter("pager", 1 + "");
        builder.appendQueryParameter("pagersize", 20 + "");
        return builder.toString();
    }

    //

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void onAddressChosen(AddressChooseEvent addressChooseEvent) {
        Intent data = new Intent();
        data.putExtra("address", addressChooseEvent.getAddress());
        setResult(233, data);
        finish();
    }

}
