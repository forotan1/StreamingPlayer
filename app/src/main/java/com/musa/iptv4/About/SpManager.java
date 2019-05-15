package com.musa.iptv4.About;

import android.content.Context;
import android.content.SharedPreferences;

public class SpManager {
    private static final Object LOCK = new Object();
    private static SpManager sInstance;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mSettings;

    public static synchronized SpManager getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new SpManager();
            }
        }
        return sInstance;
    }

    public void init(Context context, String name) {
        mSettings = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        mEditor = mSettings.edit();
    }

    public String getString(String key, String defaultValue) {
        return mSettings.getString(key, defaultValue);
    }

    public void putString(String key, String value) {
        mEditor.putString(key, value).apply();
    }

    public int getInt(String key, int defaultValue) {
        return mSettings.getInt(key, defaultValue);
    }

    public void putBoolean(String key, boolean value) {
        mEditor.putBoolean(key, value).apply();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return mSettings.getBoolean(key, defaultValue);
    }

    public void putInt(String key, int value) {
        mEditor.putInt(key, value).apply();
    }

    public void removeKey(String key) {
        mEditor.remove(key).apply();
    }

    public boolean contains(String key) {
        return mSettings.contains(key);
    }

    public void registerOnSharedPreferenceChangeListener(
        SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
        mSettings.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }

    public void unregisterOnSharedPreferenceChangeListener(
        SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
        mSettings.unregisterOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }
}
