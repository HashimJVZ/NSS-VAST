package com.vast.nss;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SplashActivity extends AppCompatActivity {
    public static int SPLASH_TIME_OUT = 2500;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();

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

//    private void checkAdmin() {
//        databaseReference.child("profile").child(getUser()).child("isAdmin").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                SharedPreferences sharedPreferences = getSharedPreferences("SharedPref", MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                if (Objects.equals(dataSnapshot.getValue(), true)) {
//                    editor.putBoolean("isAdmin", true);
//                } else {
//                    editor.putBoolean("isAdmin", false);
//                }
//                editor.apply();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//    }
}
