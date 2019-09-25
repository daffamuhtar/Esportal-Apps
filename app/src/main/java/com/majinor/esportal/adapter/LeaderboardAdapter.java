package com.majinor.esportal.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.majinor.esportal.LeaderboardFragment;
import com.majinor.esportal.R;
import com.majinor.esportal.model.LeaderboardModel;

import java.util.ArrayList;

import static com.majinor.esportal.Server.URL_IMAGETOUR;

public class LeaderboardAdapter extends RecyclerView.Adapter <LeaderboardAdapter.RecyclerViewHolder> {

    ArrayList<LeaderboardModel> arrayList;
    private Context mContext;
    private TournamentAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }


    public LeaderboardAdapter(Context context, ArrayList<LeaderboardModel> exampleList) {
        mContext = context;
    }
    public LeaderboardAdapter(ArrayList<LeaderboardModel> arrayList){
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leaderboard, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        mContext=parent.getContext();
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {

        LeaderboardModel leaderboardModel = arrayList.get(position);

        String nickname = leaderboardModel.getMnickname();
        int point = leaderboardModel.getmPoint();
        int rank = leaderboardModel.getMrank();

        holder.mTextViewNama.setText(nickname);
        holder.mTextViewNo.setText(""+rank+". ");
        holder.mTextViewPoint.setText("ESP. "+point);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextViewNama;
        public TextView mTextViewPoint;
        public TextView mTextViewNo;

        public RecyclerViewHolder(View view){
            super(view);

            mTextViewNama = itemView.findViewById(R.id.tv_lead_nama);
            mTextViewPoint= itemView.findViewById(R.id.tv_lead_point);
            mTextViewNo = itemView.findViewById(R.id.tv_lead_no);



        }
    }

}