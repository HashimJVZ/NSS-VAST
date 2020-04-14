package com.vast.nss;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class AttendanceActivity extends AppCompatActivity {

    String dbEventKey;
    String collegeID;
    String enrollmentNumber;
    EditText enrollmentEditText;
    Button markAttendanceButton;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        Bundle extras = getIntent().getExtras();
        dbEventKey = Objects.requireNonNull(extras).getString("dbEventKey", "0000");

        enrollmentEditText = findViewById(R.id.attendance_enrollment);

        markAttendanceButton = findViewById(R.id.mark_attendance_button);
        markAttendanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCollegeID();
                Log.d("data", "dbEventKey=" + dbEventKey + " collegeID=" + collegeID + " enrollmentNumber=" + enrollmentNumber);

            }
        });

        FloatingActionButton floatingActionButton = findViewById(R.id.scan_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AttendanceActivity.this, ScanningActivity.class);
                intent.putExtra("dbEventKey", dbEventKey);
                startActivity(intent);
            }
        });

    }

    private void getCollegeID() {
        enrollmentNumber = enrollmentEditText.getText().toString();
        databaseReference.child("profile").child(enrollmentNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                collegeID = (String) dataSnapshot.child("collegeId").getValue();
                databaseReference.child("participants").child(dbEventKey).child(collegeID).setValue(enrollmentNumber);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("my_log", "onCancelled: " + databaseError);
            }
        });

    }
}
