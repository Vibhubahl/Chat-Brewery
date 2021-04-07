package com.example.baatcheet.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.baatcheet.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText name = findViewById(R.id.editTextName);
        EditText phone = findViewById(R.id.editTextPhone);
        EditText email = findViewById(R.id.editTextTextEmailAddress1);
        EditText pass = findViewById(R.id.editTextTextPassword1);
        AppCompatButton signIn1 = findViewById(R.id.sign_up1);

        signIn1.setOnClickListener(v -> {

            String nameText = name.getText().toString();
            String phoneText = phone.getText().toString();
            String emailText = email.getText().toString();
            String passText = pass.getText().toString();

            if (!nameText.isEmpty() || !phoneText.isEmpty() || !emailText.isEmpty() || !passText.isEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailText,passText).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Map<String, String> userData = new HashMap<>();
                        userData.put("name", nameText);
                        userData.put("phone", phoneText);
                        userData.put("email", emailText);
                        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getUid()).setValue(userData).addOnSuccessListener(aVoid -> {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        });
                    }else{
                        Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}