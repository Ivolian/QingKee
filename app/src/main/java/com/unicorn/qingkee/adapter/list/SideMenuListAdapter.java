package com.unicorn.qingkee.adapter.list;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unicorn.qingkee.R;
import com.unicorn.qingkee.activity.main.MainActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class SideMenuListAdapter extends RecyclerView.Adapter<SideMenuListAdapter.ViewHolder> {

    private MainActivity mainActivity;

    public SideMenuListAdapter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public int getItemCount() {
        return mainActivity.FRAGMENT_TITLES.length;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.side_menu_item, viewGroup, false);
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
                            mainActivity.onSideMenuItemClick(getAdapterPosition());
                        }
                    }, 600);
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        viewHolder.textView.setText(mainActivity.FRAGMENT_TITLES[position]);
    }

}
