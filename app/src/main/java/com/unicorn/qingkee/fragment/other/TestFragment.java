package com.unicorn.qingkee.fragment.other;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unicorn.qingkee.R;

public class TestFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        view.getBackground().setAlpha(10);
        TextView viewhello = (TextView) view.findViewById(R.id.tv);
        viewhello.setText("hehe");
        return view;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}