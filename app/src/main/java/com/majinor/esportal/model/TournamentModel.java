package com.majinor.esportal.model;

public class TournamentModel {
    private String mImageUrl;
    private int mId;
    private int mStatus;
    private String mNama;
    private String mDesk;
    private String mJenis;
    private int mIdGame;
    private String mPanitia;
    private String mTglDaftar;
    private String mTglTM;
    private String mTglTour;
    private String mTempat;
    private int mSlotMax;
    private int mSlotIsi;
    private int mBiaya;
    private int mHadiah;
    private int mRoleid;
    private String mNoHP;

    public TournamentModel(String imageUrl, int id,int status, String nama, String desk,
                           String jenis, int idgame, String panitia,
                           String tglDaftar, String tglTM, String tglTour, String tempat, int biaya,
                           int hadiah, int slotMax, int slotIsi, String noHP, int roleid) {

        mImageUrl = imageUrl;
        mId = id;
        mStatus = status;
        mNama = nama;
        mDesk = desk;
        mJenis = jenis;
        mIdGame = idgame;
        mPanitia = panitia;
        mTglDaftar = tglDaftar;
        mTglTM = tglTM;
        mTglTour = tglTour;
        mTempat = tempat;
        mBiaya = biaya;
        mHadiah = hadiah;
        mSlotMax = slotMax;
        mSlotIsi = slotIsi;
        mNoHP = noHP;
        mRoleid = roleid;
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

    public int getmStatus() {
        return mStatus;
    }

    public String getmDesk() {
        return mDesk;
    }

    public String getmJenis() {
        return mJenis;
    }

    public int getmSlotMax() {
        return mSlotMax;
    }

    public int getmSlotIsi() {
        return mSlotIsi;
    }

    public int getmIdGame() {
        return mIdGame;
    }

    public String getmPanitia() {
        return mPanitia;
    }

    public int getmBiaya() {
        return mBiaya;
    }

    public int getmHadiah() {
        return mHadiah;
    }

    public String getmTglDaftar() {
        return mTglDaftar;
    }

    public String getmTglTM() {
        return mTglTM;
    }

    public String getmTglTour() {
        return mTglTour;
    }

    public String getmTempat() {
        return mTempat;
    }

    public String getmNoHP() {
        return mNoHP;
    }

    public int getmRoleid() {
        return mRoleid;
    }


}