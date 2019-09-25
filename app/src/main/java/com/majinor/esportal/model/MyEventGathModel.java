package com.majinor.esportal.model;

public class MyEventGathModel {
    private String mImageUrl;
    private int mId;
    private String mNama;
    private String mDesk;
    //    private int mIdGame;
    private int mIdGath;
    private String mNick;
    private String mTglGath;
    private String mTempat;
    private int mBiaya;
    private String mNoHP;

    public MyEventGathModel(String imageUrl, int id, String nama, String desk, int idgath, String nick,
                          String tglGath, String tempat, int biaya, String noHP) {

        mImageUrl = imageUrl;
        mId = id;
        mNama = nama;
        mDesk = desk;
        mIdGath=idgath;
        mNick=nick;
        mTglGath=tglGath;
        mTempat = tempat;
        mBiaya = biaya;
        mNoHP = noHP;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getmNama() {
        return mNama;
    }
    public String getmDesk() {
        return mDesk;
    }

    public int getmId() {
        return mId;
    }

    public int getmIdGath() {
        return mIdGath;
    }

    public String getmNick() {
        return mNick;
    }

    public int getmBiaya() {
        return mBiaya;
    }

    public String getmTglGath() {
        return mTglGath;
    }

    public String getmTempat() {
        return mTempat;
    }

    public String getmNoHP() {
        return mNoHP;
    }



}