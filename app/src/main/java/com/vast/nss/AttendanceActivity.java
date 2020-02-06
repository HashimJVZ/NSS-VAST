package com.vast.nss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AttendanceActivity extends AppCompatActivity {
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        Bundle extras = getIntent().getExtras();
        key = extras.getString("key","0000");

        FloatingActionButton floatingActionButton = findViewById(R.id.scan_fab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AttendanceActivity.this,ScanningActivity.class);
                intent.putExtra("key", key);
                startActivity(intent);
            }
        });


    }
}
