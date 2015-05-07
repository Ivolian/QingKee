package com.unicorn.qingkee.fragment.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

import butterknife.ButterKnife;


public abstract class BaseFragment extends Fragment {

    MaterialDialog progressDialog;

    abstract public int getLayoutResourceId();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        Log.e("result", getClass().getName() + "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        Log.e("result", getClass().getName() + "onDestroy");
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(getLayoutResourceId(), container, false);
        ButterKnife.inject(this, view);

        return view;
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
        ButterKnife.reset(this);
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            visibleToUser();
        }
    }

    public void visibleToUser() {

    }

    public void finishActivity() {

        getActivity().finish();
    }


    public void showProgressDialog() {

        showProgressDialog("处理中...");
    }

    public void showProgressDialog(String title) {

        if (progressDialog == null) {
            progressDialog = new MaterialDialog.Builder(getActivity())
                    .theme(Theme.LIGHT)
                    .title(title)
                    .content("请稍后...")
                    .cancelable(false)
                    .progress(true, 0)
                    .show();
        } else {
            progressDialog.show();
        }
    }

    public void hideProgressDialog() {

        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
