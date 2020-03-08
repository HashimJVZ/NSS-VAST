package com.vast.nss;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class AttendanceActivity extends AppCompatActivity {

    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        Bundle extras = getIntent().getExtras();
        key = Objects.requireNonNull(extras).getString("key", "0000");

        FloatingActionButton floatingActionButton = findViewById(R.id.scan_fab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AttendanceActivity.this, ScanningActivity.class);
                intent.putExtra("key", key);
                startActivity(intent);
            }
        });


    }
}
