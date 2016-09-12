package com.unicorn.qingkee.activity.address.model;


import java.io.Serializable;

public class Address implements Serializable{

    /**
     * doccode : TFL-236-10-604
     * docname : 上海市嘉定区泰富路236弄10号604室
     */

    private String doccode;
    private String docname;

    public String getDoccode() {
        return doccode;
    }

    public void setDoccode(String doccode) {
        this.doccode = doccode;
    }

    public String getDocname() {
        return docname;
    }

    public void setDocname(String docname) {
        this.docname = docname;
    }

}
