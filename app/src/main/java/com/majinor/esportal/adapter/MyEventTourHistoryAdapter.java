package com.majinor.esportal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.majinor.esportal.R;
import com.majinor.esportal.model.MyEventTourHistoryModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.majinor.esportal.Server.URL_IMAGETOUR;


public class MyEventTourHistoryAdapter extends RecyclerView.Adapter<MyEventTourHistoryAdapter.ExampleViewHolder> {
    private Context mContext;
    private ArrayList<MyEventTourHistoryModel> mExampleList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public MyEventTourHistoryAdapter(Context context, ArrayList<MyEventTourHistoryModel> exampleList) {
        mContext = context;
        mExampleList = exampleList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_myeventhistory, parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        MyEventTourHistoryModel currentItem = mExampleList.get(position);

        String imageUrl = currentItem.getImageUrl();
        String namaTour = currentItem.getmNamaTour();
        String namaTim = currentItem.getmNamaTim();
        String jenisTour = currentItem.getmJenis();
        String tglTour = currentItem.getmTglDaftar();
        int id = currentItem.getmIdRegis();

        holder.mTextViewNamaTour.setText(namaTour);
        holder.mTextViewNamaTim.setText(namaTim);
        holder.mTextViewJenis.setText(jenisTour);
        holder.mTextViewTglDaftar.setText(tglTour);

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
        public TextView mTextViewTglDaftar;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.iv_meh_pamflet);
            mTextViewNamaTour = itemView.findViewById(R.id.tv_meh_namatour);
            mTextViewNamaTim = itemView.findViewById(R.id.tv_meh_namatim);
            mTextViewJenis = itemView.findViewById(R.id.tv_meh_jenis);
            mTextViewTglDaftar = itemView.findViewById(R.id.tv_meh_tgldaftar);


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