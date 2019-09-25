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
import com.majinor.esportal.model.TournamentModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.majinor.esportal.Server.URL_IMAGETOUR;

public class TournamentAdapter extends RecyclerView.Adapter<TournamentAdapter.ExampleViewHolder> implements Filterable {
    private Context mContext;
    private ArrayList<TournamentModel> mExampleList;
    private OnItemClickListener mListener;
    private ArrayList<TournamentModel> exampleListFull;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public TournamentAdapter(Context context, ArrayList<TournamentModel> exampleList) {
        mContext = context;
        mExampleList = exampleList;
        exampleListFull = new ArrayList<>(exampleList);
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_tournament, parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        TournamentModel currentItem = mExampleList.get(position);

        String imageUrl = currentItem.getImageUrl();
        String namaTour = currentItem.getmNama();
        String jenisTour = currentItem.getmJenis();
        String tglTour = currentItem.getmTglTour();
        String tempat = currentItem.getmTempat();

        int id = currentItem.getmId();
        int slotMax = currentItem.getmSlotMax();
        int hadiah = currentItem.getmHadiah();

        holder.mTextViewNama.setText(namaTour);
        holder.mTextViewJenis.setText(jenisTour);
        holder.mTextViewTglTour.setText(tglTour);
        holder.mTextViewTempat.setText(tempat);
        holder.mTextViewSlotMax.setText("Slot : " + slotMax);
        holder.mTextViewHadiah.setText("Rp. " + hadiah);

        Picasso.with(mContext)
                .load(URL_IMAGETOUR+imageUrl)
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
        public TextView mTextViewJenis;
        public TextView mTextViewSlotMax;
        public TextView mTextViewHadiah;
        public TextView mTextViewTglTour;
        public TextView mTextViewTempat;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.iv_pamflet);
            mTextViewNama = itemView.findViewById(R.id.tv_nama);
            mTextViewJenis = itemView.findViewById(R.id.tv_jenis);
            mTextViewSlotMax = itemView.findViewById(R.id.tv_slotmax);
            mTextViewHadiah = itemView.findViewById(R.id.tv_hadiah);
            mTextViewTglTour = itemView.findViewById(R.id.tv_tgltour);
            mTextViewTempat = itemView.findViewById(R.id.tv_tempat);

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
            ArrayList<TournamentModel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (TournamentModel item : exampleListFull) {
                    if (item.getmNama().toLowerCase().contains(filterPattern)|
                            item.getmTempat().toLowerCase().contains(filterPattern)|
                            item.getmTglTour().toLowerCase().contains(filterPattern)|
                            item.getmPanitia().toLowerCase().contains(filterPattern)) {
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