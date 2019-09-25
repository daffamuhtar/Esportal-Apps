package com.majinor.esportal.model;

public class LeaderboardModel {
    private String mNickname;
    private int mRank;
    private int mPoint;

    public LeaderboardModel(int rank,String nickname,int point) {

        mRank=rank;
        mNickname = nickname;
        mPoint = point;
    }

    public String getMnickname() {
        return mNickname;
    }

    public int getMrank() {
        return mRank;
    }

    public int getmPoint() {
        return mPoint;
    }
}