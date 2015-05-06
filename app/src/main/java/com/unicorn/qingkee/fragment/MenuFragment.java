package com.unicorn.qingkee.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unicorn.qingkee.R;
import com.unicorn.qingkee.activity.main.MainActivity;
import com.unicorn.qingkee.adapter.list.MenuAdapter;
import com.unicorn.qingkee.fragment.base.BaseFragment;

import butterknife.InjectView;


public class MenuFragment extends BaseFragment {

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_menu;
    }

    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        initRecyclerView();

        return view;
    }

    private void initRecyclerView() {

        MainActivity mainActivity = (MainActivity)getActivity();

        recyclerView.setLayoutManager(getLinearLayoutManager());
        recyclerView.setAdapter(new MenuAdapter((MainActivity)getActivity()));
    }

    private LinearLayoutManager getLinearLayoutManager() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return linearLayoutManager;
    }

}
