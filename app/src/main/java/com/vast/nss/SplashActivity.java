package com.vast.nss;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    public static int SPLASH_TIME_OUT = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getUser();
                if (getUser() == null) {
                    Intent splashIntent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(splashIntent);
                } else {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }

                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    private String getUser() {
        SharedPreferences sharedPreferences = getSharedPreferences("SharedPref", MODE_PRIVATE);
        return sharedPreferences.getString("userName", null);
    }
}
