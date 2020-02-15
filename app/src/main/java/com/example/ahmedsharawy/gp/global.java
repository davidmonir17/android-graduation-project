package com.example.ahmedsharawy.gp;

import android.app.Application;

public class global extends Application {
    Boolean sds;
    int ids;
    String us;

    public String getUs() {
        return us;
    }

    public void setUs(String us) {
        this.us = us;
    }

    public int getIds() {
        return ids;
    }

    public void setIds(int ids) {
        this.ids = ids;
    }

    public Boolean getSds() {
        return sds;
    }

    public void setSds(Boolean sds) {
        this.sds = sds;
    }
}
