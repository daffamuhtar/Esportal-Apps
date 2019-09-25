package com.majinor.esportal.model;

public class HomeResultModel {
    private String mImageUrl;
    private int mIdPemenan;
    private int mIdGame;
    private int mIdTurnamen;
    private String mNamaTurnamen;
    private String mJuara1;
    private String mTglTour;


    public HomeResultModel(String imageUrl, int idpem,int idgame, int idtour, String namatour, String juara1,String tgltour) {

        mImageUrl = imageUrl;
        mIdPemenan = idpem;
        mIdGame = idgame;
        mIdTurnamen = idtour;
        mNamaTurnamen = namatour;
        mTglTour=tgltour;
        mJuara1 = juara1;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getmNamaTurnamen() {
        return mNamaTurnamen;
    }

    public int getmIdPemenan() {
        return mIdPemenan;
    }
    public int getmIdGame() {
        return mIdPemenan;
    }
    public int getmIdTurnamen() {
        return mIdTurnamen;
    }
    public String getmTglTour() {
        return mTglTour;
    }

    public String getmJuara1() {
        return mJuara1;
    }




}