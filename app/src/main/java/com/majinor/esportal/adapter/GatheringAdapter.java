package com.majinor.esportal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.majinor.esportal.R;
import com.majinor.esportal.model.GatheringModel;
import com.majinor.esportal.model.TournamentModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.majinor.esportal.Server.URL_IMAGEGATH;

public class GatheringAdapter extends RecyclerView.Adapter<GatheringAdapter.ExampleViewHolder>implements Filterable {
    private Context mContext;
    private ArrayList<GatheringModel> mExampleList;
    private OnItemClickListener mListener;
    private ArrayList<GatheringModel> exampleListFull;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public GatheringAdapter(Context context, ArrayList<GatheringModel> exampleList) {
        mContext = context;
        mExampleList = exampleList;
        exampleListFull = new ArrayList<>(exampleList);
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_gathering, parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        GatheringModel currentItem = mExampleList.get(position);

        String imageUrl = currentItem.getImageUrl();
        String namaGath = currentItem.getmNama();
        String tglGath = currentItem.getmTglGath();
        String tempat = currentItem.getmTempat();

        int biaya = currentItem.getmBiaya();

        holder.mTextViewNama.setText(namaGath);
        holder.mTextViewTglGath.setText(tglGath);
        holder.mTextViewTempat.setText(tempat);
        holder.mTextViewBiaya.setText("Rp. " + biaya);

        Picasso.with(mContext)
                .load(URL_IMAGEGATH+imageUrl)
                .centerCrop()
                .resize(700,335)
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextViewNama;
        public TextView mTextViewBiaya;
        public TextView mTextViewTglGath;
        public TextView mTextViewTempat;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.iv_gath_pamflet);
            mTextViewNama = itemView.findViewById(R.id.tv_gath_nama);
            mTextViewBiaya = itemView.findViewById(R.id.tv_gath_biaya);
            mTextViewTglGath = itemView.findViewById(R.id.tv_gath_tglgath);
            mTextViewTempat = itemView.findViewById(R.id.tv_gath_tempat);

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
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<GatheringModel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (GatheringModel item : exampleListFull) {
                    if (item.getmNama().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mExampleList.clear();
            mExampleList.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };
}