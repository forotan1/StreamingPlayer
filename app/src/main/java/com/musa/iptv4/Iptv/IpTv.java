package com.musa.iptv4.Iptv;


import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.musa.iptv4.R;
import com.musa.iptv4.Utilities.MovableFloatingActionButton;

import java.util.ArrayList;

public class IpTv extends Fragment {

    private RecyclerView mRecyclerView;

    private RecyclerView.LayoutManager mLayoutManager;
    private IpTvDBHelper dbHelper;
    private IpTvAdapter adapter;
    private String filter = "";
    private Context mContext;
    private ArrayList<Imodel> iPtvList;
    public static final String TAG ="IpTvActivityIsStarted";

    private TextView addUrlText;
    CoordinatorLayout rootLayout;

    View emptyView;

    public static final String EXTRA_TITLE = "tvTitle";
    public static final String EXTRA_COVER = "tvCover";
    public static final String EXTRA_ICON = "tvIcon";
    public static final String EXTRA_LIVE_URL = "liveUrl";
    public static final String EXTRA_ABOUT_TV = "aboutTV";
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.activity_ip_tv, container, false);

        MovableFloatingActionButton fab =  view.findViewById(R.id.fab);
        CoordinatorLayout.LayoutParams mp = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
        fab.setCoordinatorLayout(mp);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();

            }
        });

        mContext = getActivity();

        //initialize the variables
        mRecyclerView = view.findViewById(R.id.tab_tv_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        RecyclerView.LayoutManager tLayoutManager = new GridLayoutManager(getContext(), getSpanCount());
        mRecyclerView.setLayoutManager(tLayoutManager);
        // mRecyclerView.hideIfEmpty(emptyView);
        // mRecyclerView.showIfEmpty(emptyView);

        iPtvList = new ArrayList<>();
        //populate recyclerview
        populaterecyclerView();
//        addUrlText = view.findViewById(R.id.empty_tv);
//
//        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
//            @Override
//            public void onChanged() {
//                super.onChanged();
//                if (adapter.getItemCount()==0){
//                    mRecyclerView.setVisibility(View.GONE);
//                    addUrlText.setVisibility(View.VISIBLE);
//                }else {
//                    mRecyclerView.setVisibility(View.VISIBLE);
//                    addUrlText.setVisibility(View.GONE);
//
//                }
//            }
//        });

        rootLayout = getActivity().findViewById(R.id.iptv_root);
//        if (adapter.equals(0)){
//            addUrlText.isShown();
//        }
//        else
//        {addUrlText.setVisibility(View.GONE);}




        return view;
    }

    public void openDialog() {

        RecordDialog recordDialog = new RecordDialog();
        recordDialog.show(getActivity().getSupportFragmentManager(), "record dialog");
    }

    private void populaterecyclerView(){
        dbHelper = new IpTvDBHelper(getContext());
        adapter = new IpTvAdapter(dbHelper.peopleList(), getContext(), mRecyclerView);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        //adapter.notifyDataSetChanged();

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
