package com.bridgelabz.fundoo.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharedPreferencesManager {

    private static final String PREFERENCES_FILE_NAME = "SharedPreferences";
    private static final String TAG = "PreferencesManager";
    private SharedPreferences sharedPreferences;

    public SharedPreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
    }

    public void setAccessToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("key", token);
        editor.apply();
    }

    public String getAccessToken() {
        String authKey = sharedPreferences.getString("key", "NULL");
        Log.e(TAG, "getAccessToken: " + authKey);

        return authKey;
    }
}
