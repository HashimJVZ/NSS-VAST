package com.vast.nss;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity{

    EditText editTextUserName, editTextPassword;
    Button loginButton;
    TextView signUp;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUserName = findViewById(R.id.loginUserName);
        editTextPassword = findViewById(R.id.loginPassword);
        loginButton = findViewById(R.id.loginButton);
        signUp = findViewById(R.id.textSignUp);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userName = editTextUserName.getText().toString().trim();
                final String password = editTextPassword.getText().toString();

                if (userName.isEmpty()) {
                    editTextUserName.setError("Enrollment Number is Required!");
                    editTextUserName.requestFocus();
                }
                else if(password.isEmpty()){
                    editTextPassword.setError("Password is required!");
                    editTextPassword.requestFocus();
                }
                else {
                    databaseReference.child("users").child(userName).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(!dataSnapshot.exists()){
                                editTextUserName.setError("User Doesn't Exists!");
                                editTextUserName.requestFocus();

                            }
                            else if (Objects.equals(dataSnapshot.getValue(), password)){
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                            else{
                                editTextPassword.setError("Wrong Password");
                                editTextPassword.requestFocus();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);

            }
        });

    }
}
