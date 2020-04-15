package com.vast.nss;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vast.nss.databinding.ActivityLoginBinding;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userName = binding.loginUserName.getText().toString().trim();
                final String password = binding.loginPassword.getText().toString();

                if (userName.isEmpty()) {
                    binding.loginUserName.setError("Enrollment Number is Required!");
                    binding.loginUserName.requestFocus();
                } else if (password.isEmpty()) {
                    binding.loginPassword.setError("Password is required!");
                    binding.loginPassword.requestFocus();
                } else {
                    databaseReference.child("users").child(userName).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (!dataSnapshot.exists()) {
                                binding.loginUserName.setError("User Doesn't Exists!");
                                binding.loginUserName.requestFocus();

                            } else if (Objects.equals(dataSnapshot.getValue(), password)) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                saveUser(userName);
                                finish();
                            } else {
                                binding.loginPassword.setError("Wrong Password");
                                binding.loginPassword.requestFocus();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

        });

        binding.textSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }

    private void saveUser(String userName) {
        SharedPreferences sharedPreferences = getSharedPreferences("SharedPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userName", userName);
        editor.apply();

    }
}
