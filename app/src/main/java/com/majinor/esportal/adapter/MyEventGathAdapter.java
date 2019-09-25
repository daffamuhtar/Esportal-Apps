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

import com.majinor.esportal.GatheringActivity;
import com.majinor.esportal.GatheringDetailActivity;
import com.majinor.esportal.MyEventGathFollowedDetailActivity;
import com.majinor.esportal.MyEventGathFragment;
import com.majinor.esportal.MyEventTourFragment;
import com.majinor.esportal.MyEventTourRegistedDetailActivity;
import com.majinor.esportal.R;
import com.majinor.esportal.model.MyEventGathModel;
import com.majinor.esportal.model.MyEventTourModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.majinor.esportal.Server.URL_IMAGEGATH;
import static com.majinor.esportal.Server.URL_IMAGETOUR;

public class MyEventGathAdapter extends RecyclerView.Adapter <MyEventGathAdapter.RecyclerViewHolder> {

    ArrayList<MyEventGathModel> arrayList;
    private Context mContext;
    private TournamentAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(TournamentAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public MyEventGathAdapter(Context context, ArrayList<MyEventGathModel> exampleList) {
        mContext = context;
    }
    public MyEventGathAdapter(ArrayList<MyEventGathModel> arrayList){
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_myeventgath, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        mContext=parent.getContext();
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {

        MyEventGathModel myEventGathModel = arrayList.get(position);

        String imageUrl = myEventGathModel.getImageUrl();
        String namaGath = myEventGathModel.getmNama();
        String nick = myEventGathModel.getmNick();
        String tglGath = myEventGathModel.getmTglGath();
        String tempat = myEventGathModel.getmTempat();


        holder.mTextViewNama.setText(namaGath);
        holder.mTextViewNick.setText("By : "+nick);
        holder.mTextViewTglGath.setText(tglGath);
        holder.mTextViewTempat.setText(tempat);

        Picasso.with(mContext)
                .load(URL_IMAGEGATH+imageUrl)
                .centerCrop()
                .resize(700,335)
                .into(holder.mImageView);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pindah = new Intent(mContext, GatheringDetailActivity.class);
                Bundle data = new Bundle();
                data.putString(GatheringActivity.EXTRA_FOTO, arrayList.get(position).getImageUrl());
                data.putInt(GatheringActivity.EXTRA_ID, arrayList.get(position).getmIdGath());
                data.putInt(MyEventGathFragment.EXTRA_IDFOLLOW, arrayList.get(position).getmId());
                data.putString(GatheringActivity.EXTRA_NAMA, arrayList.get(position).getmNama());
                data.putString(GatheringActivity.EXTRA_DESK, arrayList.get(position).getmDesk());
                data.putString(GatheringActivity.EXTRA_TGLGATH, arrayList.get(position).getmTglGath());
                data.putString(GatheringActivity.EXTRA_NICK, arrayList.get(position).getmNick());
                data.putInt(GatheringActivity.EXTRA_BIAYA, arrayList.get(position).getmBiaya());
                data.putString(GatheringActivity.EXTRA_NOHP, arrayList.get(position).getmNoHP());
                data.putString(GatheringActivity.EXTRA_TEMPAT, arrayList.get(position).getmTempat());

                pindah.putExtras(data);
                mContext.startActivity(pindah);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;
        public TextView mTextViewNama;
        public TextView mTextViewNick;
        public TextView mTextViewTglGath;
        public TextView mTextViewTempat;

        public RecyclerViewHolder(View view){
            super(view);

            mImageView = itemView.findViewById(R.id.iv_meg_pamflet);
            mTextViewNama = itemView.findViewById(R.id.tv_meg_nama);
            mTextViewNick = itemView.findViewById(R.id.tv_meg_nick);
            mTextViewTglGath = itemView.findViewById(R.id.tv_meg_tglgath);
            mTextViewTempat = itemView.findViewById(R.id.tv_meg_tempat);



        }
    }

}