package com.majinor.esportal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.majinor.esportal.R;
import com.majinor.esportal.model.MyEventWaitingModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.majinor.esportal.Server.URL_IMAGETOUR;


public class MyEventWaitingAdapter extends RecyclerView.Adapter<MyEventWaitingAdapter.ExampleViewHolder> {
    private Context mContext;
    private ArrayList<MyEventWaitingModel> mExampleList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public MyEventWaitingAdapter(Context context, ArrayList<MyEventWaitingModel> exampleList) {
        mContext = context;
        mExampleList = exampleList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_myeventwaiting, parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        MyEventWaitingModel currentItem = mExampleList.get(position);

        String imageUrl = currentItem.getImageUrl();
        String namaTour = currentItem.getmNamaTour();
        String namaTim = currentItem.getmNamaTim();
        String jenisTour = currentItem.getmJenis();
        String tglDaftar = currentItem.getmTglDaftar();
        int status = currentItem.getmStatus();

        int idtour = currentItem.getmIdTour();
        int biaya =currentItem.getmBiaya();

        holder.mTextViewNamaTour.setText(namaTour);
        holder.mTextViewNamaTim.setText(namaTim);
        holder.mTextViewJenis.setText(jenisTour);
        holder.mTextViewBiaya.setText("Rp. " +biaya);
        holder.mTextViewTglDaftar.setText(tglDaftar);

        if (status==3) {
            holder.mTextViewStatus.setText("Menunggu Pembayaran");
        }else{holder.mTextViewStatus.setText("Menunggu Konfirmasi");
        }

        Picasso.with(mContext)
                .load(URL_IMAGETOUR+imageUrl)
                .fit()
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextViewNamaTour;
        public TextView mTextViewNamaTim;
        public TextView mTextViewJenis;
        public TextView mTextViewBiaya;
        public TextView mTextViewTglDaftar;
        public TextView mTextViewStatus;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.iv_mew_pamflet);
            mTextViewNamaTour = itemView.findViewById(R.id.tv_mew_namatour);
            mTextViewNamaTim = itemView.findViewById(R.id.tv_mew_namatim);
            mTextViewJenis = itemView.findViewById(R.id.tv_mew_jenis);
            mTextViewBiaya = itemView.findViewById(R.id.tv_mew_biaya);
            mTextViewTglDaftar = itemView.findViewById(R.id.tv_mew_tgldaftar);
            mTextViewStatus = itemView.findViewById(R.id.tv_mew_status);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}