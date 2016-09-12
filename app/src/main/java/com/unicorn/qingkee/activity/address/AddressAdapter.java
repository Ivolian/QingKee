package com.unicorn.qingkee.activity.address;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unicorn.qingkee.R;
import com.unicorn.qingkee.activity.address.event.AddressChooseEvent;
import com.unicorn.qingkee.activity.address.model.Address;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {


    // ================================== data ==================================

    List<Address> addressList = new ArrayList<>();

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }


    // ================================== data ==================================

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Address address = addressList.get(position);
        holder.address.setText(address.getDocname());
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.address)
        TextView address;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        @OnClick(R.id.item)
        public void itemOnClick() {
            Address address = addressList.get(getAdapterPosition());
            AddressChooseEvent addressChooseEvent = new AddressChooseEvent();
            addressChooseEvent.setAddress(address);
            EventBus.getDefault().post(addressChooseEvent);
        }
    }


}
