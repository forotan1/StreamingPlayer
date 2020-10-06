package com.musa.iptv4.Utilities;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class DarkModeHelper extends Application {
    public static DarkModeHelper giveMode = null;

    public static DarkModeHelper getInstance(){

        if (giveMode == null){
            giveMode = new DarkModeHelper();
        }
        return giveMode;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        giveMode = this;
    }

    public void setPref (String tp, Context context){
        SharedPreferences sharedPreferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("prefTh", tp);
        editor.apply();
    }

    public String getPref (Context context){
        SharedPreferences sharedPreferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("prefTh", "default");
    }

}
