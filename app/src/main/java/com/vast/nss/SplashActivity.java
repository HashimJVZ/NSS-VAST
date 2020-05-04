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
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getUser();
                if (getUser() == null) {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
//                } else if (!isProfileFilled()) {
//                    Log.d("keeri", "isProfileFilled: called second");
//                    startActivity(new Intent(SplashActivity.this, ProfileCreationActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }

                finish();
            }
        }, SPLASH_TIME_OUT);
    }

//    private boolean isProfileFilled() {
//
//        final boolean[] flag = new boolean[1];
//        databaseReference.child("profile").child(getUser()).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                if (dataSnapshot.child("name").getValue() == null) {
//                    Log.d("keeri", " 1");
//                    flag[0] = false;
//                } else if (dataSnapshot.child("collegeId").getValue() == null) {
//                    Log.d("keeri", " 2");
//                    flag[0] = false;
//                } else if (dataSnapshot.child("department").getValue() == null) {
//                    Log.d("keeri", " 3");
//                    flag[0] = false;
//                } else flag[0] = dataSnapshot.child("contact").getValue() != null;
//                Log.d("keeri", " 4"+flag[0]+dataSnapshot.child("contact").getValue());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//        Log.d("keeri", "isProfileFilled: "+ flag[0]);
//        return flag[0];
//    }

    private String getUser() {
        SharedPreferences sharedPreferences = getSharedPreferences("SharedPref", MODE_PRIVATE);
        return sharedPreferences.getString("userName", null);
    }

}
