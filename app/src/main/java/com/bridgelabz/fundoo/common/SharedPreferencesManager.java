package com.bridgelabz.fundoo.common;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {

    Context context ;
    public SharedPreferencesManager(Context context){
        this.context = context;
    }
    public  void setAccessToken(String token) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("key", token);
        editor.apply();
    }
}
