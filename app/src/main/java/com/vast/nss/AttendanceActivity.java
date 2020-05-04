package com.vast.nss;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

    long hours;
    String category;
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
        category = extras.getString("category", "default");
        hours = extras.getLong("hours", 0);

        enrollmentEditText = findViewById(R.id.attendance_enrollment);

        markAttendanceButton = findViewById(R.id.mark_attendance_button);
        markAttendanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (enrollmentEditText.getText().toString().isEmpty()) {
                    enrollmentEditText.setError("Type in Enrollment Number");
                } else {
                    getCollegeID();
                    Toast.makeText(AttendanceActivity.this, "Added", Toast.LENGTH_SHORT).show();
                    markAttendance();
                }

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
                //is this really here??
                databaseReference.child("participants").child(dbEventKey).child(collegeID).setValue(enrollmentNumber);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("my_log", "onCancelled: " + databaseError);
            }
        });

    }

    private void markAttendance() {
        databaseReference.child("profile").child(getUser()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (category.equals("Orientation")) {
                    databaseReference.child("profile").child(getUser()).child("orientationHour").setValue((Long) dataSnapshot.child("orientationHour").getValue() + hours);
                } else if (category.equals("Campus")) {
                    databaseReference.child("profile").child(getUser()).child("campusHour").setValue((Long) dataSnapshot.child("campusHour").getValue() + hours);
                } else if (category.equals("Community")) {
                    databaseReference.child("profile").child(getUser()).child("communityHour").setValue((Long) dataSnapshot.child("communityHour").getValue() + hours);
                } else if (category.equals("Camp")) {
                    databaseReference.child("profile").child(getUser()).child("campHour").setValue((Long) dataSnapshot.child("campHour").getValue() + hours);
                } else {
                    Toast.makeText(AttendanceActivity.this, "Error in Marking", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private String getUser() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("SharedPref", MODE_PRIVATE);
        return sharedPreferences.getString("userName", null);
    }


}
