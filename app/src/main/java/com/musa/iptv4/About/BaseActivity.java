package com.musa.iptv4.About;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;


import java.util.Locale;

public abstract class BaseActivity extends AppCompatActivity {
    // change language of app
    @Override
    protected void attachBaseContext(Context newBase) {
        Locale languageType = LanguageUtils.getLanguageType();
        super.attachBaseContext(MyContextWrapper.wrap(newBase, languageType));
    }
}
