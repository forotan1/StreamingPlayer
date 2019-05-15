package com.musa.iptv4.About;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;


import com.musa.iptv4.R;

import java.util.Locale;

public class LanguageUtils {
    public static final String LANGUAGE_LOCAL = "LANGUAGE_LOCAL";
    public static final String LANGUAGE = "LANGUAGE";
    public static final String LANGUAGE_DEFAULT = "LANGUAGE_DEFAULT";
    public static final String IS_AUTO_LANGUAGE = "IS_AUTO_LANGUAGE";
    public static final String IS_RESTART = "IS_RESTART";

    public static void changeLanguageType(Locale locale) {
        Resources resources = App.getInstance().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale);
        } else {
            config.locale = locale;
            resources.updateConfiguration(config, dm);
        }
    }

    public static Locale getLanguageType() {
        Resources resources = App.getInstance().getResources();
        Configuration config = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return config.getLocales().get(0);
        } else {
            return config.locale;
        }
    }

    public static String getLanguage() {
        return SpManager.getInstance().getString(LANGUAGE, LANGUAGE_DEFAULT);
    }

    public static void setLanguage() {
        if (isAutoLanguage()) {
            SpManager.getInstance().putString(LANGUAGE, LanguageUtils.getLanguageLocal());
        } else {
            LanguageUtils.changeLanguageType(new Locale(LanguageUtils.getLanguage()));
        }
    }

    public static String getLanguageLocal() {
        return SpManager.getInstance().getString(LANGUAGE_LOCAL, LANGUAGE_DEFAULT);
    }

    private static boolean isAutoLanguage() {
        return SpManager.getInstance().getBoolean(IS_AUTO_LANGUAGE, false);
    }

    public static boolean isContainLanguage(Context context, String language) {
        String[] array = getLanguageList(context);
        for (String text : array) {
            if (TextUtils.equals(text, language)) {
                return true;
            }
        }
        return false;
    }

    public static String[] getLanguageList(Context context) {
        return context.getResources().getStringArray(R.array.language);
    }

    public static String[] getLanguageNameList(Context context) {
        return context.getResources().getStringArray(R.array.language_name);
    }

    public static int getIndexLanguage(Context context) {
        String language = getLanguage();
        String[] array = getLanguageList(context);
        if (isAutoLanguage()) return array.length - 1;
        for (int i = 0; i < array.length; i++) {
            if (TextUtils.equals(array[i], language)) {
                return i;
            }
        }
        return 0;
    }
}
