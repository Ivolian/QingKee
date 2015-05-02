package com.unicorn.qingkee.my;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;

import com.unicorn.qingkee.MyApplication;
import com.unicorn.qingkee.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;


public class BetterSpinner extends MaterialBetterSpinner {

    private List<SpinnerData> spinnerDataList = new ArrayList<>();

    private ArrayAdapter<String> adapter;

    public BetterSpinner(Context arg0, AttributeSet arg1) {

        super(arg0, arg1);
        adapter = new ArrayAdapter<>(MyApplication.getInstance().getApplicationContext(),
              R.layout.better_spinner, new ArrayList<String>());
        setAdapter(adapter);
    }

    public void setSpinnerDataList(List<SpinnerData> spinnerDataList) {

        this.spinnerDataList = spinnerDataList;
        for (SpinnerData spinnerData : spinnerDataList) {
            adapter.add(spinnerData.getText());
        }
    }

    public String getSelectedValue() {

        String selectText = getText().toString();
        for (SpinnerData spinnerData : spinnerDataList) {
            if (selectText.equals(spinnerData.getText())) {
                return spinnerData.getValue();
            }
        }
        return "";
    }

    public void clear() {

        spinnerDataList.clear();
        adapter.clear();
        setText("");
    }

}