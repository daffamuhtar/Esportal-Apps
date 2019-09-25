package com.majinor.esportal.model;

public class GatheringModel {
    private String mImageUrl;
    private int mId;
    private String mNama;
    private String mDesk;
//    private int mIdGame;
    private int mIdUser;
    private String mNick;
    private String mTglGath;
    private String mTempat;
    private int mBiaya;
    private String mNoHP;

    public GatheringModel(String imageUrl, int id, String nama, String desk,int iduser, String nick,
                           String tglGath, String tempat, int biaya, String noHP) {

        mImageUrl = imageUrl;
        mId = id;
        mNama = nama;
        mDesk = desk;
        mIdUser=iduser;
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

    public int getmId() {
        return mId;
    }

    public int getmIdUser() {
        return mIdUser;
    }

    public String getmNick() {
        return mNick;
    }

    public String getmDesk() {
        return mDesk;
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