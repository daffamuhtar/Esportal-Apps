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

import com.majinor.esportal.MyEventTourFragment;
import com.majinor.esportal.MyEventTourRegistedDetailActivity;
import com.majinor.esportal.R;
import com.majinor.esportal.model.MyEventTourModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.majinor.esportal.Server.URL_IMAGETOUR;

public class MyEventTourAdapter extends RecyclerView.Adapter <MyEventTourAdapter.RecyclerViewHolder> {

    ArrayList<MyEventTourModel> arrayList;
    private Context mContext;
    private TournamentAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(TournamentAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public MyEventTourAdapter(Context context, ArrayList<MyEventTourModel> exampleList) {
        mContext = context;
    }
    public MyEventTourAdapter(ArrayList<MyEventTourModel> arrayList){
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_myeventtour, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        mContext=parent.getContext();
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {

        MyEventTourModel myEventTourModel = arrayList.get(position);

        String imageUrl = myEventTourModel.getImageUrl();
        String namaTour = myEventTourModel.getmNamaTour();
        String jenisTour = myEventTourModel.getmJenis();
        String tglTour = myEventTourModel.getmTglTour();
        String tempat = myEventTourModel.getmTempat();


        holder.mTextViewNama.setText(namaTour);
        holder.mTextViewJenis.setText(jenisTour);
        holder.mTextViewTglTour.setText(tglTour);
        holder.mTextViewTempat.setText(tempat);

        Picasso.with(mContext)
                .load(URL_IMAGETOUR+imageUrl)
                .centerCrop()
                .resize(700,335)
                .into(holder.mImageView);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pindah = new Intent(mContext, MyEventTourRegistedDetailActivity.class);
                Bundle data = new Bundle();
                data.putString(MyEventTourFragment.EXTRA_FOTO, arrayList.get(position).getImageUrl());
                data.putInt(MyEventTourFragment.EXTRA_IDREGIS, arrayList.get(position).getmIdRegis());
                data.putInt(MyEventTourFragment.EXTRA_IDTOUR, arrayList.get(position).getmIdTour());
                data.putInt(MyEventTourFragment.EXTRA_IDGAME, arrayList.get(position).getmIdGame());
                data.putInt(MyEventTourFragment.EXTRA_BIAYA, arrayList.get(position).getmBiaya());
                data.putString(MyEventTourFragment.EXTRA_TGLTM, arrayList.get(position).getmTglTM());
                data.putString(MyEventTourFragment.EXTRA_TGLTOUR, arrayList.get(position).getmTglTour());
                data.putString(MyEventTourFragment.EXTRA_NAMATOUR, arrayList.get(position).getmNamaTour());
                data.putString(MyEventTourFragment.EXTRA_JENIS, arrayList.get(position).getmJenis());
                data.putString(MyEventTourFragment.EXTRA_TEMPAT, arrayList.get(position).getmTempat());
                data.putString(MyEventTourFragment.EXTRA_NAMATIM, arrayList.get(position).getmNamaTim());
                data.putString(MyEventTourFragment.EXTRA_USRKETUA, arrayList.get(position).getmUsrKetua());
                data.putString(MyEventTourFragment.EXTRA_USRANG1, arrayList.get(position).getmUsrAng1());
                data.putString(MyEventTourFragment.EXTRA_USRANG2, arrayList.get(position).getmUsrAng2());
                data.putString(MyEventTourFragment.EXTRA_USRANG3, arrayList.get(position).getmUsrAng3());
                data.putString(MyEventTourFragment.EXTRA_USRANG4, arrayList.get(position).getmUsrAng4());
                data.putString(MyEventTourFragment.EXTRA_IGNKETUA, arrayList.get(position).getmIgnKetua());
                data.putString(MyEventTourFragment.EXTRA_IGNANG1, arrayList.get(position).getmIgnAng1());
                data.putString(MyEventTourFragment.EXTRA_IGNANG2, arrayList.get(position).getmIgnAng2());
                data.putString(MyEventTourFragment.EXTRA_IGNANG3, arrayList.get(position).getmIgnAng3());
                data.putString(MyEventTourFragment.EXTRA_IGNANG4, arrayList.get(position).getmIgnAng4());

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
        public TextView mTextViewJenis;
        public TextView mTextViewTglTour;
        public TextView mTextViewTempat;

        public RecyclerViewHolder(View view){
            super(view);

            mImageView = itemView.findViewById(R.id.iv_met_pamflet);
            mTextViewNama = itemView.findViewById(R.id.tv_met_nama);
            mTextViewJenis = itemView.findViewById(R.id.tv_met_jenis);
            mTextViewTglTour = itemView.findViewById(R.id.tv_met_tgltour);
            mTextViewTempat = itemView.findViewById(R.id.tv_met_tempat);



        }
    }

}