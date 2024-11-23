package com.musa.iptv4.LiveTv;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.musa.iptv4.LiveTv.Afghanistan.AfghanTab;
import com.musa.iptv4.LiveTv.Iran.IranTab;
import com.musa.iptv4.LiveTv.Others.OthersTab;
import com.musa.iptv4.LiveTv.Sport.SportTab;
import com.musa.iptv4.R;
import com.musa.iptv4.Utilities.SectionsPageAdapter;


public class LiveFragment extends Fragment {
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;


    public LiveFragment() {
        // Required empty public constructor
    }
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_live, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSectionsPageAdapter = new SectionsPageAdapter(getActivity().getSupportFragmentManager());

        mViewPager = view.findViewById(R.id.live_tv_View_pager);
        setViewPager(mViewPager);

        TabLayout tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(0).setText(getText(R.string.af_tab));
        tabLayout.getTabAt(1).setText(getText(R.string.iran_tab));
        tabLayout.getTabAt(2).setText("Sport");
        //tabLayout.getTabAt(3).setIcon(R.drawable.ic_more);
        tabLayout.getTabAt(3).setText("Others");
    }

    private void setViewPager(ViewPager viewPager){

        SectionsPageAdapter adapter = new SectionsPageAdapter(getChildFragmentManager());
        adapter.addFragment(new AfghanTab());
        adapter.addFragment(new IranTab());
        adapter.addFragment(new SportTab());
        adapter.addFragment(new OthersTab());
        viewPager.setAdapter(adapter);

    }
    public void onPause(){
        super.onPause();
        getActivity().overridePendingTransition(0,0);
    }


}