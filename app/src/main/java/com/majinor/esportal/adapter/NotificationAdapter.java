package com.majinor.esportal.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.majinor.esportal.R;
import com.majinor.esportal.model.NotificationModel;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter <NotificationAdapter.RecyclerViewHolder> {

    ArrayList<NotificationModel> arrayList;
    private Context mContext;
    private NotificationAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }


    public NotificationAdapter(Context context, ArrayList<NotificationModel> exampleList) {
        mContext = context;
    }
    public NotificationAdapter(ArrayList<NotificationModel> arrayList){
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notifikasi, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        mContext=parent.getContext();
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {

        NotificationModel notificationModel = arrayList.get(position);

        String tglupdate = notificationModel.getMtglupdate();
        int point = notificationModel.getmPoint();

        holder.mTextViewTgl.setText(tglupdate);
        holder.mTextViewPoint.setText(""+point+" Point");

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextViewTgl;
        public TextView mTextViewPoint;

        public RecyclerViewHolder(View view){
            super(view);

            mTextViewTgl = itemView.findViewById(R.id.tv_notif_tgl);
            mTextViewPoint= itemView.findViewById(R.id.tv_notif_point);


        }
    }

}