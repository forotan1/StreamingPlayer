package com.musa.iptv4.Iptv;

import android.content.Context;
import android.os.Bundle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.musa.iptv4.R;
import com.musa.iptv4.Utilities.MovableFloatingActionButton;

import java.util.ArrayList;


public class IptvFragment extends Fragment {

    private RecyclerView mRecyclerView;

    private RecyclerView.LayoutManager mLayoutManager;
    private IpTvDBHelper dbHelper;
    private IpTvAdapter adapter;
    private Context mContext;
    private ArrayList<Imodel> iPtvList;
    CoordinatorLayout rootLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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

        iPtvList = new ArrayList<>();
        //populate recyclerview
        populaterecyclerView();

        rootLayout = getActivity().findViewById(R.id.iptv_root);

        return view;
    }
    public void openDialog() {

        RecordDialog recordDialog = new RecordDialog(getActivity(), R.style.BottomSheetDialogTheme);
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