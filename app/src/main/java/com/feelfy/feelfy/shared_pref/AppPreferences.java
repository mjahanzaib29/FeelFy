package com.feelfy.feelfy.shared_pref;

import android.content.Context;
import android.content.SharedPreferences;

import com.feelfy.feelfy.modules.RequestProfileUpdate;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AppPreferences {

    public static final String PREFS_NAME = "FeelFy";

    public static String savePreferences(Context context, String key, String value) {

        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor;
        editor = sharedPreferences.edit();// edit() fun is used save the data into sharedPreferences
        editor.putString(key, value);
        editor.apply();

        return key;
    }    public static String savePreference(Context context, String key, RequestProfileUpdate value) {

        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor;
        editor = sharedPreferences.edit();// edit() fun is used save the data into sharedPreferences
        editor.putString(key, String.valueOf(value));
        editor.apply();

        return key;
    }

    //custom Method To Get the Data
    public static String loadPreferences(Context context, String key) {
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String value = sharedPreferences.getString(key, "");

        return value;
    }

    public static void saveSharedPreferencesLogList(Context context, RequestProfileUpdate requestProfileUpdate) {
        SharedPreferences mPrefs = context.getSharedPreferences(PREFS_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(requestProfileUpdate);
        prefsEditor.putString("myJson", json);
        prefsEditor.commit();
    }

    public static RequestProfileUpdate loadSharedPreferencesLogList(Context context) {
        RequestProfileUpdate requestProfileUpdate = new RequestProfileUpdate();
        SharedPreferences mPrefs = context.getSharedPreferences(PREFS_NAME, context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("myJson", "");
        if (json.isEmpty()) {
            requestProfileUpdate  = new RequestProfileUpdate();
        } else {
            Type type = new TypeToken<RequestProfileUpdate>() {
            }.getType();
            requestProfileUpdate = gson.fromJson(json,type);
        }
        return requestProfileUpdate;
    }

}
