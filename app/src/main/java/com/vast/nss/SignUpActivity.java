package com.vast.nss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editTextUserName, editTextPassword, editTextConfirmPassword;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextUserName = findViewById(R.id.editTextUserName);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void registerUser(){
        String userName = editTextUserName.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();

        if (userName.isEmpty()) {
            editTextUserName.setError("NSS ID is Required!");
            editTextUserName.requestFocus();
        }

        if(password.isEmpty()){
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
        }

        if(confirmPassword.isEmpty()){
            editTextConfirmPassword.setError("Password is required!");
            editTextConfirmPassword.requestFocus();
        }

        if(password.length()<6){
            editTextPassword.setError("Minimum length should be 6");
            editTextPassword.requestFocus();
        }

        if (password.equals(confirmPassword)){
            editTextConfirmPassword.setError("Passwords Doesn't matches!");
            editTextConfirmPassword.requestFocus();
        }



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonSignUp:
                registerUser();
                break;

            case R.id.textViewLogin:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}
