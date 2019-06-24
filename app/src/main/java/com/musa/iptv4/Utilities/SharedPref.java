package com.musa.iptv4.Utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    SharedPreferences mySharedPref;
    public SharedPref(Context context) {
        mySharedPref = context.getSharedPreferences("filename", Context.MODE_PRIVATE);



    }
    // thios a method willsave the nightModew State : ture pr false
    public void setNightModeState (Boolean state){
        SharedPreferences.Editor editor = mySharedPref.edit();
        editor.putBoolean("NightMode", state);
        editor.apply();
    }
    // this method will load the night mode state
    public Boolean loadNightMode(){
        Boolean state = mySharedPref.getBoolean("NightMode", false);
        return state;
    }






}
