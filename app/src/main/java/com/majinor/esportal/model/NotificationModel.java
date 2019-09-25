package com.majinor.esportal.model;

public class NotificationModel {
    private String mTglupdate;
    private int mPoint;

    public NotificationModel(String tglupdate,int point) {

        mTglupdate = tglupdate;
        mPoint = point;
    }

    public String getMtglupdate() {
        return mTglupdate;
    }

    public int getmPoint() {
        return mPoint;
    }
}