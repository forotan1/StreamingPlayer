package com.musa.iptv4.Iptv;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.musa.iptv4.About.AboutActivity;
import com.musa.iptv4.LiveTv.LiveTvActivity;
import com.musa.iptv4.R;
import com.musa.iptv4.Utilities.BottomNavigationViewHelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class IpTv extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private IpTvDBHelper dbHelper;
    private IpTvAdapter adapter;
    private String filter = "";
    private TextView addStation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip_tv);

        addStation = findViewById(R.id.add_new_ip_tv);


        //initialize the variables
        mRecyclerView = (RecyclerView)findViewById(R.id.tab_tv_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        RecyclerView.LayoutManager tLayoutManager = new GridLayoutManager(this, getSpanCount());
        mRecyclerView.setLayoutManager(tLayoutManager);



        //populate recyclerview
        populaterecyclerView(filter);

        addStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //goToAddUserActivity();
                openDialog();


            }
        });



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem =menu.getItem(1);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ip_tv :
                        break;

                    case R.id.about:
                        Intent ipIntent = new Intent(IpTv.this, AboutActivity.class);
                        startActivity(ipIntent);
                        break;

                    case R.id.live_tv:
                        Intent aboutIntent = new Intent(IpTv.this, LiveTvActivity.class);
                        startActivity(aboutIntent);
                        break;

                }


                return false;
            }
        });



    }

    public void openDialog() {

        RecordDialog recordDialog = new RecordDialog();
        recordDialog.show(getSupportFragmentManager(), "record dialog");




    }

    private void populaterecyclerView(String filter){
        dbHelper = new IpTvDBHelper(this);
        adapter = new IpTvAdapter(dbHelper.peopleList(filter), this, mRecyclerView);

        mRecyclerView.setAdapter(adapter);

    }





    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    private int getSpanCount() {

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int spanSize = metrics.widthPixels;

        float spanLim = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                100,
                metrics
        );

        return (int) (spanSize / spanLim);


    }
}
