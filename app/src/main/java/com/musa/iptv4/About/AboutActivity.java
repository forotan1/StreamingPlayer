package com.musa.iptv4.About;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
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
    ImageView shareApp;

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



        shareApp = findViewById(R.id.share_app);
        shareApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shaIntent = new Intent(Intent.ACTION_SEND);
                String sharedBody = "Download the App from this link https://farsitv.000webhostapp.com/";
                shaIntent.setType("text/plain");
                shaIntent.putExtra(Intent.EXTRA_SUBJECT, "Download the App");
                shaIntent.putExtra(Intent.EXTRA_TEXT, sharedBody);
                startActivity(Intent.createChooser(shaIntent, "share via"));
            }
        });
        changeLang = findViewById(R.id.change_language);
        changeLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLangChangeDialo();

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
        loadLocale();

    }

    private void restartApp() {
        Intent i = new Intent(getApplicationContext(), AboutActivity.class);
        startActivity(i);
        finish();
    }


    private void showLangChangeDialo() {
        final  String[] listItem ={"English", "Deutsch", "پارسی"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(AboutActivity.this);
        mBuilder.setTitle("Chnage Language");
        mBuilder.setSingleChoiceItems(listItem, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (i == 0){
                    changeLang("en");
                    recreate();
                }
                if (i == 1){
                    changeLang("de");
                    recreate();
                }
                if (i == 2){
                    changeLang("fa");
                    recreate();
                }

                dialog.dismiss();
            }
        });
        AlertDialog mdialog = mBuilder.create();
        mdialog.show();
    }

    public void loadLocale(){
        String langPref = "language";
        SharedPreferences preferences = getSharedPreferences("com.musa.nightmode.PREFRENCES", Context.MODE_PRIVATE);
        String language = preferences.getString(langPref,"");
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();

        configuration.locale = locale;
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());

    }

    private void changeLang(String lang) {
        if (lang.equalsIgnoreCase(""))
            return;

        Locale locale = new Locale(lang);
        savelocale(lang);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();

        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());




    }

    public void savelocale (String lang){
        String langPref = "language";
        SharedPreferences prefs = getSharedPreferences("com.musa.nightmode.PREFRENCES", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(langPref, lang);
        editor.apply();
    }







}
