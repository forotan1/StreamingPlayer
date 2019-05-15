package com.musa.iptv4.LiveTv;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.musa.iptv4.About.AboutActivity;
import com.musa.iptv4.Iptv.IpTv;
import com.musa.iptv4.LiveTv.Afghanistan.AfghanTab;
import com.musa.iptv4.LiveTv.Iran.IranTab;
import com.musa.iptv4.LiveTv.Others.OthersTab;
import com.musa.iptv4.LiveTv.Sport.SportTab;
import com.musa.iptv4.R;
import com.musa.iptv4.Utilities.BottomNavigationViewHelper;
import com.musa.iptv4.Utilities.SectionsPageAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class LiveTvActivity extends AppCompatActivity {

    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        mViewPager = findViewById(R.id.live_tv_View_pager);
        setViewPager(mViewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_af);
        tabLayout.getTabAt(0).setText(getText(R.string.af_tab));
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_azadi);
        tabLayout.getTabAt(1).setText(getText(R.string.af_tab));
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_android);
        tabLayout.getTabAt(2).setText("Sport");
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_about);
        tabLayout.getTabAt(3).setText("Others");


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem =menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.live_tv:
                        break;

                    case R.id.ip_tv:
                        Intent ipIntent = new Intent(LiveTvActivity.this, IpTv.class);
                        startActivity(ipIntent);
                        break;

                    case R.id.about:
                        Intent aboutIntent = new Intent(LiveTvActivity.this, AboutActivity.class);
                        startActivity(aboutIntent);
                        break;

                }


                return false;
            }
        });

    }

    private void setViewPager(ViewPager viewPager){

        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new AfghanTab());
        adapter.addFragment(new IranTab());
        adapter.addFragment(new SportTab());
        adapter.addFragment(new OthersTab());
        viewPager.setAdapter(adapter);


    }



    public void onPause(){
        super.onPause();
        overridePendingTransition(0,0);
    }

}
