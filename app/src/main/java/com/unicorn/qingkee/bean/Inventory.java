package com.unicorn.qingkee.bean;

import com.unicorn.qingkee.util.JSONUtils;

import org.json.JSONObject;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Inventory implements Serializable {


    String inventoryBatch;
    String description;
    Date publishDate;

    //

    public Inventory(String inventoryBatch, String description, Date publishDate) {
        this.inventoryBatch = inventoryBatch;
        this.description = description;
        this.publishDate = publishDate;
    }

    //

    public String getInventoryBatch() {
        return inventoryBatch;
    }

    public void setInventoryBatch(String inventoryBatch) {
        this.inventoryBatch = inventoryBatch;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    //

    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd\'T\'hh:mm:ss");

    public static Inventory parse(JSONObject jsonObject) {

        String inventoryBatch = JSONUtils.getString(jsonObject, "Inventorybatch", "");
        String description = JSONUtils.getString(jsonObject, "Descr", "");
        String dateString = JSONUtils.getString(jsonObject, "Publishdate", "");
        Date publishDate = null;
        try {
            publishDate = simpleDateFormat.parse(dateString);
        } catch (Exception e) {
            //
        }
        return new Inventory(inventoryBatch, description, publishDate);
    }
}
