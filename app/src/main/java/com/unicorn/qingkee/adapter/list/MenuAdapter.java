package com.unicorn.qingkee.adapter.list;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.unicorn.qingkee.R;
import com.unicorn.qingkee.activity.main.MainActivity;
import com.unicorn.qingkee.util.UrlUtils;
import com.unicorn.qingkee.volley.MyVolley;

import org.w3c.dom.Text;

import java.io.File;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private MainActivity mainActivity;


    public MenuAdapter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public int getItemCount() {
        return mainActivity.FRAGMENT_TITLES
                .length;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.menu_item, viewGroup, false);

        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.textView)
        TextView textView;


        public ViewHolder(View v) {
            super(v);
            ButterKnife.inject(this, v);


            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mainActivity.mDrawerLayout.closeDrawers();
                            mainActivity.viewPager.setCurrentItem(getAdapterPosition(),false);
                        }
                    },600);
                }
            });

        }
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.textView.setText(mainActivity.FRAGMENT_TITLES[position]);
    }

}
