package com.unicorn.qingkee.activity.address.event;

import com.unicorn.qingkee.activity.address.model.Address;


public class AddressChooseEvent {

    private Address address;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}
