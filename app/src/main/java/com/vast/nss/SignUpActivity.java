package com.vast.nss;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity{

    EditText editTextUserName, editTextPassword, editTextConfirmPassword;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextUserName = findViewById(R.id.editTextUserName);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        TextView login = findViewById(R.id.textViewLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        Button buttonSignUp = findViewById(R.id.buttonSignUp);
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser(){
        final String userName = editTextUserName.getText().toString();
        final String password = editTextPassword.getText().toString();
        String confirmPassword = editTextConfirmPassword.getText().toString();


        if (userName.isEmpty()) {
            editTextUserName.setError("NSS ID is Required!");
            editTextUserName.requestFocus();
        }

        else if(password.isEmpty()){
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
        }

        else if(confirmPassword.isEmpty()){
            editTextConfirmPassword.setError("Password Confirmation is required!");
            editTextConfirmPassword.requestFocus();
        }

        else if(password.length()<6){
            editTextPassword.setError("Minimum length should be 6");
            editTextPassword.requestFocus();
        }

        else if (!(password.equals(confirmPassword))){
            editTextConfirmPassword.setError("Passwords Doesn't matches!");
            editTextConfirmPassword.requestFocus();
        }

        else databaseReference.child("users").child(userName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    editTextUserName.setError("User Already Exists!");
                    editTextUserName.requestFocus();
                }
                else{
                    databaseReference.child("users").child(userName).setValue(password);
                    Intent intent = new Intent(SignUpActivity.this, ProfileCreationActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
