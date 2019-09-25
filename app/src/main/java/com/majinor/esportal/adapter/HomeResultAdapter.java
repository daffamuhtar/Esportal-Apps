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

import com.majinor.esportal.MyEventTourRegistedHasilActivity;
import com.majinor.esportal.R;
import com.majinor.esportal.model.HomeResultModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.majinor.esportal.MyEventTourFragment.EXTRA_IDTOUR;
import static com.majinor.esportal.Server.URL_IMAGEGATH;
import static com.majinor.esportal.Server.URL_IMAGETOUR;

public class HomeResultAdapter extends RecyclerView.Adapter <HomeResultAdapter.RecyclerViewHolder> {

    ArrayList<HomeResultModel> arrayList;
    private Context mContext;
    private TournamentAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

//    public void setOnItemClickListener(HomeResultAdapter.OnItemClickListener listener) {
//        mListener = listener;
//    }

    public HomeResultAdapter(Context context, ArrayList<HomeResultModel> exampleList) {
        mContext = context;
    }
    public HomeResultAdapter(ArrayList<HomeResultModel> arrayList){
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_homeresult, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        mContext=parent.getContext();
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {

        HomeResultModel homeResultModel = arrayList.get(position);

        String imageUrl = homeResultModel.getImageUrl();
        String namaTour = homeResultModel.getmNamaTurnamen();
        String tglTour = homeResultModel.getmTglTour();
        String juara = homeResultModel.getmJuara1();

        holder.mTextViewNama.setText(namaTour);
        holder.mTextViewTglTour.setText(tglTour);
        holder.mTextViewJuara.setText(juara);
        Picasso.with(mContext)
                .load(URL_IMAGETOUR+imageUrl)
                .fit()
                .into(holder.mImageView);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent pindah = new Intent(mContext, MyEventTourRegistedHasilActivity.class);
                    Bundle data = new Bundle();
                    data.putInt(EXTRA_IDTOUR, arrayList.get(position).getmIdTurnamen());

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
        public TextView mTextViewJuara;
        public TextView mTextViewTglTour;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.iv_hr_pamflet);
            mTextViewNama = itemView.findViewById(R.id.tv_hr_namatour);
            mTextViewJuara = itemView.findViewById(R.id.tv_hr_juara1);
            mTextViewTglTour = itemView.findViewById(R.id.tv_hr_tgltour);



        }
    }

}