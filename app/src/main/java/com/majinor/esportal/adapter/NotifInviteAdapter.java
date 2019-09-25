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
import com.majinor.esportal.NotifInviteDetailActivity;
import com.majinor.esportal.R;
import com.majinor.esportal.model.NotifInviteModel;
import com.majinor.esportal.model.NotificationModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


import static com.majinor.esportal.MyEventTourFragment.EXTRA_IDTOUR;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_ACCANG1;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_ACCANG2;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_ACCANG3;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_ACCANG4;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_IDREGIS;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_TGLTM;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_TGLDAFTAR;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_TGLTOUR;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_BIAYA;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_JENIS;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_FOTO;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_NAMATOUR;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_NAMATIM;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_IGNKETUA;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_IGNANG1;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_IGNANG2;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_IGNANG3;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_IGNANG4;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_USRKETUA;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_USRANG1;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_USRANG2;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_USRANG3;
import static com.majinor.esportal.MyEventWaitingActivity.EXTRA_USRANG4;
import static com.majinor.esportal.Server.URL_IMAGETOUR;

public class NotifInviteAdapter extends RecyclerView.Adapter <NotifInviteAdapter.RecyclerViewHolder> {

    ArrayList<NotifInviteModel> arrayList;
    private Context mContext;
    private NotificationAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }


    public NotifInviteAdapter(Context context, ArrayList<NotificationModel> exampleList) {
        mContext = context;
    }
    public NotifInviteAdapter(ArrayList<NotifInviteModel> arrayList){
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_invitenotif, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        mContext=parent.getContext();
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {

        NotifInviteModel notificationModel = arrayList.get(position);

        String namatour = notificationModel.getmNamaTour();
        String tglupdate = notificationModel.getmTglTour();
        String usrketua = notificationModel.getmUsrKetua();
        String imageUrl = notificationModel.getImageUrl();

        holder.mTextViewTgl.setText(tglupdate);
        holder.mTextViewNama.setText(namatour);
        holder.mTextViewText.setText(usrketua+" mengajak Anda untuk bergabung dalam timnya.");

        Picasso.with(mContext)
                .load(URL_IMAGETOUR+imageUrl)
                .fit()
                .into(holder.mImageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pindah = new Intent(mContext, NotifInviteDetailActivity.class);
                Bundle data = new Bundle();
                data.putString(EXTRA_FOTO, arrayList.get(position).getImageUrl());
                data.putInt(EXTRA_IDREGIS, arrayList.get(position).getmIdRegis());
                data.putInt(EXTRA_IDTOUR, arrayList.get(position).getmIdTour());
                data.putInt(EXTRA_BIAYA, arrayList.get(position).getmBiaya());
                data.putString(EXTRA_TGLTM, arrayList.get(position).getmTglTM());
                data.putString(EXTRA_TGLTOUR, arrayList.get(position).getmTglTour());
                data.putString(EXTRA_NAMATOUR, arrayList.get(position).getmNamaTour());
                data.putString(EXTRA_JENIS, arrayList.get(position).getmJenis());
                data.putString(EXTRA_NAMATIM, arrayList.get(position).getmNamaTim());
                data.putString(EXTRA_USRKETUA, arrayList.get(position).getmUsrKetua());
                data.putString(EXTRA_USRANG1, arrayList.get(position).getmUsrAng1());
                data.putString(EXTRA_USRANG2, arrayList.get(position).getmUsrAng2());
                data.putString(EXTRA_USRANG3, arrayList.get(position).getmUsrAng3());
                data.putString(EXTRA_USRANG4, arrayList.get(position).getmUsrAng4());
                data.putString(EXTRA_IGNKETUA, arrayList.get(position).getmIgnKetua());
                data.putString(EXTRA_IGNANG1, arrayList.get(position).getmIgnAng1());
                data.putString(EXTRA_IGNANG2, arrayList.get(position).getmIgnAng2());
                data.putString(EXTRA_IGNANG3, arrayList.get(position).getmIgnAng3());
                data.putString(EXTRA_IGNANG4, arrayList.get(position).getmIgnAng4());
                data.putInt(EXTRA_ACCANG1, arrayList.get(position).getmAccAng1());
                data.putInt(EXTRA_ACCANG2, arrayList.get(position).getmAccAng2());
                data.putInt(EXTRA_ACCANG3, arrayList.get(position).getmAccAng3());
                data.putInt(EXTRA_ACCANG4, arrayList.get(position).getmAccAng4());

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

        public TextView mTextViewTgl;
        public TextView mTextViewNama;
        public TextView mTextViewText;
        public ImageView mImageView;

        public RecyclerViewHolder(View view){
            super(view);

            mTextViewTgl = itemView.findViewById(R.id.tv_in_tgl);
            mTextViewNama= itemView.findViewById(R.id.tv_in_namatour);
            mTextViewText= itemView.findViewById(R.id.tv_in_text);
            mImageView= itemView.findViewById(R.id.iv_in_pamflet);


        }
    }

}