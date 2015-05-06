package com.unicorn.qingkee.fragment.other;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unicorn.qingkee.R;
import com.unicorn.qingkee.activity.main.MainActivity;
import com.unicorn.qingkee.adapter.list.SideMenuListAdapter;
import com.unicorn.qingkee.fragment.base.BaseFragment;

import butterknife.InjectView;


public class SideMenuFragment extends BaseFragment {

    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_side_menu;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        initRecyclerView();

        return view;
    }

    private void initRecyclerView() {

        recyclerView.setLayoutManager(getLinearLayoutManager());
        recyclerView.setAdapter(new SideMenuListAdapter((MainActivity) getActivity()));
    }

    private LinearLayoutManager getLinearLayoutManager() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return linearLayoutManager;
    }

}
