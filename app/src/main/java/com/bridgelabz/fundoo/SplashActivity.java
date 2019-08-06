package com.bridgelabz.fundoo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.bridgelabz.fundoo.Dashboard.DashboardActivity;
import com.bridgelabz.fundoo.LoginSignup.View.LoginActivity;
import com.bridgelabz.fundoo.common.SharedPreferencesManager;

public class SplashActivity extends AppCompatActivity {
    public static final int SPLASH_DISPLAY_TIME = 2000;
    private SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sharedPreferencesManager = new SharedPreferencesManager(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                openActivity();
            }
        }, SPLASH_DISPLAY_TIME);
    }

    private void openActivity() {
        Intent intent;
        if(sharedPreferencesManager.isLoggedIn()){
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            intent = new Intent(this, DashboardActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
