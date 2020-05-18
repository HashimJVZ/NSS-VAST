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

        enrollmentNumber = "9645";
        addToList();

        markAttendanceButton = findViewById(R.id.mark_attendance_button);
        markAttendanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enrollmentNumber = enrollmentEditText.getText().toString();
                if (enrollmentNumber.isEmpty()) {
                    enrollmentEditText.setError("Type in Enrollment Number");
                } else {
                    getCollegeID();
                }

            }
        });

        FloatingActionButton floatingActionButton = findViewById(R.id.scan_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AttendanceActivity.this, ScanningActivity.class);
                intent.putExtra("dbEventKey", dbEventKey);
                intent.putExtra("category", category);
                intent.putExtra("hours", hours);
                startActivity(intent);
            }
        });

    }

    private void getCollegeID() {
        Log.d("hashim", "getCollegeId: ");
        databaseReference.child("profile").child(enrollmentNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("hashim", "onDataChange: collegeID ");
                collegeID = (String) dataSnapshot.child("collegeId").getValue();
                Log.d("hashim", "collegeID=" + collegeID);
                addToList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("hashim", "onCancelled: " + databaseError);
            }
        });
    }

    private void addToList() {

        databaseReference.child("participants").child(dbEventKey).child(collegeID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("hashim", "onDataChange: exist");
                if (dataSnapshot.exists()) {
                    Toast.makeText(AttendanceActivity.this, "Already Marked", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AttendanceActivity.this, "Added", Toast.LENGTH_SHORT).show();
                    markAttendance();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void markAttendance() {
        //changed..
        //is this really here??
        databaseReference.child("participants").child(dbEventKey).child(collegeID).setValue(enrollmentNumber);

        databaseReference.child("profile").child(getUser()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                switch (category) {
                    case "Orientation":
                        databaseReference.child("profile").child(getUser()).child("orientationHour").setValue((Long) dataSnapshot.child("orientationHour").getValue() + hours);
                        break;
                    case "Campus":
                        databaseReference.child("profile").child(getUser()).child("campusHour").setValue((Long) dataSnapshot.child("campusHour").getValue() + hours);
                        break;
                    case "Community":
                        databaseReference.child("profile").child(getUser()).child("communityHour").setValue((Long) dataSnapshot.child("communityHour").getValue() + hours);
                        break;
                    case "Camp":
                        databaseReference.child("profile").child(getUser()).child("campHour").setValue((Long) dataSnapshot.child("campHour").getValue() + hours);
                        break;
                    default:
                        Toast.makeText(AttendanceActivity.this, "Error in Marking", Toast.LENGTH_SHORT).show();
                        break;
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
