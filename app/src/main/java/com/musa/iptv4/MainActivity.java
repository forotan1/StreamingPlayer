package com.musa.iptv4;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.musa.iptv4.About.AboutActivity;
import com.musa.iptv4.Iptv.IpTv;
import com.musa.iptv4.LiveTv.LiveTvActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navi_bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(naviListener);

        getSupportFragmentManager()
                .beginTransaction().replace(R.id.main_fragment_layout,
                new LiveTvActivity()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener naviListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment clickedFragment = null;

            switch (item.getItemId()) {
                case R.id.live_tv:
                    clickedFragment = new LiveTvActivity();
                    break;
                case R.id.ip_tv:
                    clickedFragment = new IpTv();
                    break;
                case R.id.about:
                    clickedFragment = new AboutActivity();
                    break;
            }
            assert clickedFragment != null;
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_layout, clickedFragment).commit();
            return true;
        }
    };
}