package com.musa.iptv4.About;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;


import com.musa.iptv4.BuildConfig;

import java.util.Locale;

public class App extends Application {
    private static App sInstance;

    public static synchronized App getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        SpManager.getInstance().init(this, BuildConfig.APPLICATION_ID);
        // lưu ngôn ngữ mặc định của máy
        // save default language of device
        SpManager.getInstance()
            .putString(LanguageUtils.LANGUAGE_LOCAL, Locale.getDefault().getLanguage());
        // bắt sự kiện thay đổi ngôn ngữ của máy điện thoại
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                SpManager.getInstance()
                    .putString(LanguageUtils.LANGUAGE_LOCAL, Locale.getDefault().getLanguage());
                SpManager.getInstance().putBoolean(LanguageUtils.IS_RESTART, true);
                LanguageUtils.setLanguage();
            }
        };
        IntentFilter filter = new IntentFilter(Intent.ACTION_LOCALE_CHANGED);
        registerReceiver(broadcastReceiver, filter);
        // setup language default first open app
        if (!SpManager.getInstance().contains(LanguageUtils.LANGUAGE)) {
            String language = Locale.getDefault().getLanguage();
            if (LanguageUtils.isContainLanguage(this, Locale.getDefault().getLanguage())) {
                SpManager.getInstance().putString(LanguageUtils.LANGUAGE, language);
                LanguageUtils.changeLanguageType(new Locale(language));
            } else {
                SpManager.getInstance().putBoolean(LanguageUtils.IS_AUTO_LANGUAGE, true);
                SpManager.getInstance()
                    .putString(LanguageUtils.LANGUAGE, LanguageUtils.getLanguageLocal());
            }
        } else {
            LanguageUtils.setLanguage();
        }
    }
}
