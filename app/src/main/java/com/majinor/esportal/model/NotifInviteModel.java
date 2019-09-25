package com.majinor.esportal.model;

public class NotifInviteModel {
    private String mImageUrl;
    private int mIdRegis;
    private int mIdTour;
    private String mNamaTour;
    private String mNamaTim;
    private String mJenis;
    private int mBiaya;
    private int mStatus;
    private String mTglTour;
    private String mTglTM;
    private String mTglDaftar;
    private String mUsrKetua;
    private String mUsrAng1;
    private String mUsrAng2;
    private String mUsrAng3;
    private String mUsrAng4;
    private String mIgnKetua;
    private String mIgnAng1;
    private String mIgnAng2;
    private String mIgnAng3;
    private String mIgnAng4;
    private int mAccAng1;
    private int mAccAng2;
    private int mAccAng3;
    private int mAccAng4;


    public NotifInviteModel(String imageUrl, int idRegis,int idTour,int status, String namaTour, String jenis, String tglTour, String tglDaftar,
                               String tglTM, int biaya, String namaTim, String usrKetua, String usrAng1,
                               String usrAng2, String usrAng3, String usrAng4, String ignKetua,
                               String ignAng1, String ignAng2, String ignAng3, String ignAng4,
                               int accAng1, int accAng2, int accAng3, int accAng4) {

        mImageUrl = imageUrl;
        mIdRegis = idRegis;
        mIdTour = idTour;
        mStatus = status;
        mNamaTour = namaTour;
        mJenis = jenis;
        mTglTour=tglTour;
        mTglTM = tglTM;
        mTglDaftar = tglDaftar;
        mBiaya = biaya;
        mNamaTim = namaTim;
        mUsrKetua = usrKetua;
        mUsrAng1 = usrAng1;
        mUsrAng2 = usrAng2;
        mUsrAng3 = usrAng3;
        mUsrAng4 = usrAng4;
        mIgnKetua = ignKetua;
        mIgnAng1  = ignAng1;
        mIgnAng2  = ignAng2;
        mIgnAng3  = ignAng3;
        mIgnAng4  = ignAng4;
        mAccAng1  = accAng1;
        mAccAng2  = accAng2;
        mAccAng3  = accAng3;
        mAccAng4  = accAng4;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getmNamaTour() {
        return mNamaTour;
    }

    public int getmIdRegis() {
        return mIdRegis;
    }

    public int getmIdTour() {
        return mIdTour;
    }

    public int getmStatus() {
        return mStatus;
    }

    public int getmAccAng1() {
        return mAccAng1;
    }
    public int getmAccAng2() {
        return mAccAng2;
    }
    public int getmAccAng3() {
        return mAccAng3;
    }
    public int getmAccAng4() {
        return mAccAng4;
    }

    public String getmJenis() {
        return mJenis;
    }

    public String getmTglTM() {
        return mTglTM;
    }

    public String getmTglTour() {
        return mTglTour;
    }

    public String getmTglDaftar() {
        return mTglDaftar;
    }

    public int getmBiaya() {
        return mBiaya;
    }

    public String getmNamaTim() {
        return mNamaTim;
    }

    public String getmUsrKetua() {
        return mUsrKetua;
    }

    public String getmUsrAng1() {
        return mUsrAng1;
    }

    public String getmUsrAng2() {
        return mUsrAng2;
    }

    public String getmUsrAng3() {
        return mUsrAng3;
    }

    public String getmUsrAng4() {
        return mUsrAng4;
    }

    public String getmIgnKetua() {
        return mIgnKetua;
    }

    public String getmIgnAng1() {
        return mIgnAng1;
    }

    public String getmIgnAng2() {
        return mIgnAng2;
    }

    public String getmIgnAng3() {
        return mIgnAng3;
    }

    public String getmIgnAng4() {
        return mIgnAng4;
    }

}