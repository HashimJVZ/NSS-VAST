package com.vast.nss;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ProfileCreationActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_creation);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        Button button = findViewById(R.id.button_sign_up);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText reg_name = findViewById(R.id.register_name);
                EditText reg_collegeId = findViewById(R.id.register_collegeID);
                EditText reg_department = findViewById(R.id.register_department);
                EditText reg_nssId = findViewById(R.id.register_nssID);
                EditText reg_contact = findViewById(R.id.register_contact);

                String name  = reg_name.getText().toString();
                String collegeId = reg_collegeId.getText().toString().toUpperCase();
                String department = reg_department.getText().toString().toUpperCase();
                String nssId = reg_nssId.getText().toString();
                String contact = reg_contact.getText().toString();

                long init = 0;

                HashMap<String, Object> map = new HashMap<>();
                map.put("name",name);
                map.put("collegeId",collegeId);
                map.put("department",department);
                map.put("nssID",nssId);
                map.put("contact",contact);
                map.put("communityHour", init);
                map.put("campHour", init);
                map.put("orientationHour", init);
                map.put("campusHour", init);



                user = FirebaseAuth.getInstance().getUid();
                databaseReference.child("profile").child(user).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent intent = new Intent(ProfileCreationActivity.this, MainActivity.class);
                        startActivity(intent);

                        finish();
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
