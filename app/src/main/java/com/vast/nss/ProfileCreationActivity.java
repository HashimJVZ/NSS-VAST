package com.vast.nss;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

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

        EditText reg_name, reg_collegeId, reg_department, reg_nssId, reg_contact;
        String name, collegeId, department, nssId, contact;

        reg_name = findViewById(R.id.register_name);
        reg_collegeId = findViewById(R.id.register_collegeID);
        reg_department = findViewById(R.id.register_department);
        reg_nssId = findViewById(R.id.register_nssID);
        reg_contact = findViewById(R.id.register_contact);

        name  = reg_name.getText().toString();
        collegeId = reg_collegeId.getText().toString().toUpperCase();
        department = reg_department.getText().toString();
        nssId = reg_nssId.getText().toString();
        contact = reg_contact.getText().toString();

        HashMap<String, Object> map = new HashMap<>();
        map.put("name",name);
        map.put("collegeId",collegeId);
        map.put("department",department);
        map.put("nssID",nssId);
        map.put("contact",contact);

        user = FirebaseAuth.getInstance().getUid();

        databaseReference.child("profile").child(user).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                finish();
            }
        });

    }
}
