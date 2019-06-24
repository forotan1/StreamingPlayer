package com.musa.iptv4.About;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.musa.iptv4.Iptv.IpTv;
import com.musa.iptv4.LiveTv.LiveTvActivity;
import com.musa.iptv4.R;
import com.musa.iptv4.Utilities.BottomNavigationViewHelper;
import com.musa.iptv4.Utilities.SharedPref;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.Locale;

public class AboutActivity extends AppCompatActivity   {
    private static final int CHANGE_LANGUAGE_REQUEST_CODE = 1;

    CircularImageView circularImageView;
    TextView changeLang;
    private AlertDialog mUnitSettingDialog;
    protected Switch mySwitch;
    SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);
        if (sharedPref.loadNightMode()== true) {
            setTheme(R.style.LightAppTheme);
        }
        else setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        mySwitch = findViewById(R.id.my_switch);

        if (sharedPref.loadNightMode()==true) {
            mySwitch.setChecked(true);
        }

        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    sharedPref.setNightModeState(true);
                    restartApp();
                }
                else {
                    sharedPref.setNightModeState(false);
                    restartApp();
                }
            }
        });



        changeLang = findViewById(R.id.change_language);
        changeLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem =menu.getItem(2);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.about :
                        break;

                    case R.id.ip_tv:
                        Intent ipIntent = new Intent(AboutActivity.this, IpTv.class);
                        startActivity(ipIntent);
                        break;

                    case R.id.live_tv:
                        Intent aboutIntent = new Intent(AboutActivity.this, LiveTvActivity.class);
                        startActivity(aboutIntent);
                        break;

                }


                return false;
            }
        });


    }

    private void restartApp() {
        Intent i = new Intent(getApplicationContext(), AboutActivity.class);
        startActivity(i);
        finish();
    }







}
