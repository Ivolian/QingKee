package com.unicorn.qingkee.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.andexert.library.RippleView;
import com.unicorn.qingkee.R;
import com.unicorn.qingkee.activity.base.ToolbarActivity;

import java.util.List;

import butterknife.InjectViews;
import butterknife.OnClick;


public class SelectActivity extends ToolbarActivity {

    @InjectViews({
            R.id.asset_add, R.id.asset_apply
    })
    List<RippleView> rippleViewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        initToolbar("青客资产管理");
        initViews();
    }

    @Override
    public int getLayoutResourceId() {

        return R.layout.activity_select;
    }

    private void initViews() {
        for (final RippleView rippleView : rippleViewList) {
            rippleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startMainActivity(rippleViewList.indexOf(rippleView));
                        }
                    }, 800);
                }
            });
        }
    }

    private void startMainActivity(final int fragmentIndex) {

        Intent intent = new Intent(SelectActivity.this, MainActivity.class);
        intent.putExtra("fragmentIndex", fragmentIndex);
        startActivity(intent);
    }

}
