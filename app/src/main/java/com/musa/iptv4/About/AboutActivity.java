package com.musa.iptv4.About;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.musa.iptv4.Iptv.IpTv;
import com.musa.iptv4.LiveTv.LiveTvActivity;
import com.musa.iptv4.R;
import com.musa.iptv4.Utilities.BottomNavigationViewHelper;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        changeLang = findViewById(R.id.change_language);
        changeLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChangeLanguage();

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


    public void openChangeLanguage() {
        final String[] arrayName = LanguageUtils.getLanguageNameList(this);
        final String[] array = LanguageUtils.getLanguageList(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_change_language)
                .setSingleChoiceItems(arrayName, LanguageUtils.getIndexLanguage(this),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String languageFirst = LanguageUtils.getLanguage();
                                if (which == arrayName.length - 1) {
                                    SpManager.getInstance().putString(LanguageUtils.LANGUAGE,
                                            LanguageUtils.getLanguageLocal());
                                    SpManager.getInstance()
                                            .putBoolean(LanguageUtils.IS_AUTO_LANGUAGE, true);
                                } else {
                                    SpManager.getInstance().putString(LanguageUtils.LANGUAGE, array[which]);
                                    SpManager.getInstance()
                                            .putBoolean(LanguageUtils.IS_AUTO_LANGUAGE, false);
                                }
                                mUnitSettingDialog.dismiss();
                                if (!TextUtils.equals(array[which], languageFirst)) {
                                    restartActivity();
                                }
                            }
                        })
                .setNegativeButton(R.string.text_dialog_ok, null);
        mUnitSettingDialog = builder.create();
        mUnitSettingDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SpManager.getInstance().getBoolean(LanguageUtils.IS_RESTART, false)) {
            SpManager.getInstance().putBoolean(LanguageUtils.IS_RESTART, false);
            restartActivity();
        }
    }

    private void restartActivity() {
        LanguageUtils.changeLanguageType(new Locale(LanguageUtils.getLanguage()));
        Intent intent = new Intent(this, Loading_activity.class);
        startActivity(intent);
        finish();
    }






}
