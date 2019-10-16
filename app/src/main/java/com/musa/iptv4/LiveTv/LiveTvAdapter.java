package com.musa.iptv4.LiveTv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.musa.iptv4.R;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class LiveTvAdapter extends RecyclerView.Adapter<LiveTvAdapter.MyViewHolder> {

    private Context mContext;
    private List<LiveTvModel> live_ModelList = new ArrayList<>();

    private onItemClickListener mListener;

    public interface onItemClickListener {
        void onItemClick(int position);
    }

    public void onItemclickListener (onItemClickListener listener){

        mListener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView tvicon;
        // public CircularImageView tvicon;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.tabTvTitle);
            tvicon = view.findViewById(R.id.tabvTvIcon);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mListener != null){

                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){

                            mListener.onItemClick(position);
                        }

                    }

                }
            });
        }
    }


    public LiveTvAdapter(Context mContext, List<LiveTvModel> live_ModelList) {
        this.mContext = mContext;
        this.live_ModelList = live_ModelList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.title.setText(live_ModelList.get(position).getTitle());
        Glide.with(mContext).load(live_ModelList.get(position).getTvicon()).into(holder.tvicon);

    }


    @Override
    public int getItemCount() {
        return live_ModelList.size();
    }
}