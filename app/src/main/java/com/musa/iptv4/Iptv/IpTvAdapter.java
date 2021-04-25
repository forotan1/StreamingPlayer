package com.musa.iptv4.Iptv;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.musa.iptv4.R;
import com.musa.iptv4.Utilities.TvDetailActivity;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

public class IpTvAdapter extends RecyclerView.Adapter<IpTvAdapter.ViewHolder> {
    private List<Imodel> mPeopleList;
    private Context mContext;
    private RecyclerView mRecyclerV;
    private static final String EXTRA_TITLE = "tvTitle";
    private static final String EXTRA_ICON = "tvIcon";
    private static final String EXTRA_LIVE_URL = "liveUrl";
    private static final String EXTRA_ABOUT_TV = "aboutTV";


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView iTitle;
        ImageView iIcon, popUp;
        private View layout;

        ViewHolder(View v) {
            super(v);
            layout = v;
            iTitle = v.findViewById(R.id.ip_tv_card_title);
            iIcon = v.findViewById(R.id.ip_tv_card_icon);
            popUp = v.findViewById(R.id.pop_up);
        }


    }
    IpTvAdapter(List<Imodel> myDataset, Context context, RecyclerView recyclerView) {
        mPeopleList = myDataset;
        mContext = context;
        mRecyclerV = recyclerView;

    }
    @NonNull
    @Override
    public IpTvAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.ip_tv_card, parent, false);
        return new ViewHolder(v);
    }
    @SuppressLint("RestrictedApi")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final Imodel imodel1 = mPeopleList.get(position);
        holder.iTitle.setText( imodel1.getiTitle());
        Glide.with(mContext).load(imodel1.getImage()).placeholder(R.drawable.khurshid).into(holder.iIcon);

        holder.popUp.setOnClickListener(v -> {
            MenuBuilder menuBuilder = new MenuBuilder(mContext);
            PopupMenu popup = new PopupMenu(mContext, holder.popUp);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_album, popup.getMenu());
            @SuppressLint("RestrictedApi") MenuPopupHelper menuPopupHelper =new MenuPopupHelper(mContext,menuBuilder);
            menuPopupHelper.setForceShowIcon(true);
            popup.show();
            popup.setOnMenuItemClickListener(item -> {

                switch (item.getItemId()) {
                    case R.id.action_update:
                        FragmentActivity activity = (FragmentActivity)(mContext);
                        FragmentManager fm = activity.getSupportFragmentManager();
                        UpdateDialog updateDialog = new UpdateDialog();
                        Bundle bundle = new Bundle();
                        bundle.putLong("USER_ID", mPeopleList.get(position).getId());
                        updateDialog.setArguments(bundle);
                        updateDialog.show(fm,"update_dialog");
                        return true;
                    case R.id.action_delete:
                        IpTvDBHelper helper = new IpTvDBHelper(mContext);
                        helper.deletePersonRecord(mPeopleList.get(position).getId(),mContext);
                        mPeopleList.remove(position);
                        mRecyclerV.removeViewAt(position);
                        notifyItemRemoved(position);
                        notifyDataSetChanged();
                        Toast.makeText(mContext, "Item Deleted", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                }

                return false;
            });

        });
        holder.layout.setOnClickListener(v -> {

            Intent detailIntent = new Intent(mContext, TvDetailActivity.class);
            Imodel clickediatem = mPeopleList.get(position);
            detailIntent.putExtra(EXTRA_TITLE, clickediatem.getiTitle());
            detailIntent.putExtra(EXTRA_LIVE_URL, clickediatem.getiUrl());
            detailIntent.putExtra(EXTRA_ABOUT_TV, clickediatem.getiAbout());
            detailIntent.putExtra(EXTRA_ICON, clickediatem.getImage());
            mContext.startActivity(detailIntent);

        });
    }
    @Override
    public int getItemCount() {
        return mPeopleList.size();
    }
}