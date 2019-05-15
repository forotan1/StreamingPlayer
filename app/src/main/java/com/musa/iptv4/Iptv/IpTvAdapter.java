package com.musa.iptv4.Iptv;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.musa.iptv4.LiveTv.LiveTvAdapter;
import com.musa.iptv4.R;

import java.util.List;

import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;


public class IpTvAdapter extends RecyclerView.Adapter<IpTvAdapter.ViewHolder> {
    private List<iModel> mPeopleList;
    private Context mContext;
    private RecyclerView mRecyclerV;
    private static final String TAG = "IpTvAdapter";


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView iTitle;
       // public TextView personAgeTxtV;
       // public TextView personOccupationTxtV;
        public ImageView iIcon, popUp;


        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            iTitle = (TextView) v.findViewById(R.id.tabTvTitle);
            iIcon = (ImageView) v.findViewById(R.id.tabvTvIcon);
            popUp = v.findViewById(R.id.pop_up);





        }
    }

    public void add(int position, iModel iModel) {
        mPeopleList.add(position, iModel);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mPeopleList.remove(position);
        notifyItemRemoved(position);
    }



    // Provide a suitable constructor (depends on the kind of dataset)
    public IpTvAdapter(List<iModel> myDataset, Context context, RecyclerView recyclerView) {
        mPeopleList = myDataset;
        mContext = context;
        mRecyclerV = recyclerView;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public IpTvAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.ip_tv_card, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        final iModel iModel1 = mPeopleList.get(position);
        holder.iTitle.setText( iModel1.getiTitle());
        Glide.with(mContext).load(iModel1.getImage()).placeholder(R.drawable.khurshid).into(holder.iIcon);

        holder.popUp.setOnClickListener(v -> showPopupMenu(holder.popUp));
        //listen to single view layout click
        holder.layout.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Choose option");
            builder.setMessage("Update or delete user?");
            builder.setPositiveButton("Update", (dialog, which) -> {
                Log.d(TAG, "onBindViewHolder: searching");

                //go to update activity
                goToUpdateActivity(iModel1.getId());

            });
            builder.setNeutralButton("Delete", (dialog, which) -> {
                IpTvDBHelper dbHelper = new IpTvDBHelper(mContext);
                dbHelper.deletePersonRecord(iModel1.getId(), mContext);

                mPeopleList.remove(position);
                mRecyclerV.removeViewAt(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mPeopleList.size());
                notifyDataSetChanged();
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
            builder.create().show();
        });


    }

    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {

            switch (menuItem.getItemId()) {
                case R.id.action_update:


                case R.id.action_delete:

                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play:
                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }



    private void goToUpdateActivity(long personId){
        Log.d(TAG, "goToUpdateActivity: opening update activity");
        Intent goToUpdate = new Intent(mContext, UpdateRecordActivity.class);
        goToUpdate.putExtra("USER_ID", personId);
        mContext.startActivity(goToUpdate);
    }



    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mPeopleList.size();
    }



}