package com.vast.nss;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileCreationActivity extends AppCompatActivity {


    DatabaseReference databaseReference;

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
        collegeId = reg_collegeId.getText().toString();
        collegeId = collegeId.toUpperCase();
        department = reg_department.getText().toString();
        nssId = reg_nssId.getText().toString();
        contact = reg_contact.getText().toString();

//        name = findViewById(R.id.register_name).toString();
//        collegeId = findViewById(R.id.register_collegeID).toString();
//        department = findViewById(R.id.register_department).toString();
//        nssId = findViewById(R.id.register_nssID).toString();
//        contact = findViewById(R.id.register_contact).toString();




    }
}
