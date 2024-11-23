package com.musa.iptv4.LiveTv.Iran;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.muddzdev.styleabletoast.StyleableToast;
import com.musa.iptv4.LiveTv.LiveTvAdapter;
import com.musa.iptv4.LiveTv.LiveTvModel;
import com.musa.iptv4.R;
import com.musa.iptv4.Utilities.TvDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class IranTab extends Fragment {

    private Context context;
    private RecyclerView tabRecyclerView;

    public static final String EXTRA_TITLE = "tvTitle";
    public static final String EXTRA_COVER = "tvCover";
    public static final String EXTRA_ICON = "tvIcon";
    public static final String EXTRA_LIVE_URL = "liveUrl";
    public static final String EXTRA_ABOUT_TV = "aboutTV";


    private LiveTvAdapter aAdapter;
    private List<LiveTvModel> liveTvModelList;
    private LiveTvAdapter.onItemClickListener mListener;

    private FirebaseDatabase myfdatabase;
    private DatabaseReference databaseReference;

    public interface onItemClickListener {
        void onItemClick (int position);
    }

    public void onItemclickListener (LiveTvAdapter.onItemClickListener listener){

        mListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tabs_layout,container,false);
        context = getActivity();

        myfdatabase = FirebaseDatabase.getInstance();
        // FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        databaseReference = myfdatabase.getReference("Iran");

        tabRecyclerView = view.findViewById(R.id.tab_tv_recycler_view);



        liveTvModelList = new ArrayList<>();

        RecyclerView.LayoutManager tLayoutManager = new GridLayoutManager(getActivity(), getSpanCount());
        tabRecyclerView.setLayoutManager(tLayoutManager);


        retrievingData();

        return view;


    }

    private void retrievingData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                liveTvModelList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Long id = (Long)snapshot.child("id").getValue();
                    String title = (String)snapshot.child("title").getValue();
                    String tvicon = (String)snapshot.child("tvicon").getValue();
                    String liveUrl = (String)snapshot.child("liveUrl").getValue();
                    String aboutTv = (String)snapshot.child("aboutTv").getValue();

                    liveTvModelList.add(new LiveTvModel(String.valueOf(id), title, liveUrl, tvicon, aboutTv ));
                }

                aAdapter = new LiveTvAdapter(getContext(), liveTvModelList);
                tabRecyclerView.setAdapter(aAdapter);

                aAdapter.onItemclickListener(position -> {

                    Intent detailIntent = new Intent(getActivity(), TvDetailActivity.class);
                    LiveTvModel clickItem = liveTvModelList.get(position);
                    detailIntent.putExtra(EXTRA_TITLE, clickItem.getTitle());
                    detailIntent.putExtra(EXTRA_ICON, clickItem.getTvicon());
                    detailIntent.putExtra(EXTRA_ABOUT_TV, clickItem.getAboutTv());
                    detailIntent.putExtra(EXTRA_LIVE_URL, clickItem.getLiveUrl());
                    startActivity(detailIntent);




                });



                aAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                StyleableToast.makeText(getActivity(), "check your internet connection", R.style.mytoast).show();

            }
        });
    }



    private int getSpanCount() {
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int spanSize = metrics.widthPixels;

        float spanLim = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                100,
                metrics
        );

        return (int) (spanSize / spanLim);
    }
}



