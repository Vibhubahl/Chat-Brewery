package com.example.baatcheet.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;

import com.example.baatcheet.R;
import com.example.baatcheet.activities.LoginActivity;
import com.example.baatcheet.activities.RegisterActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        AppCompatButton signIn = findViewById(R.id.sign_in);
        AppCompatButton signUp = findViewById(R.id.sign_up);

        signIn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), LoginActivity.class)));
        signUp.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), RegisterActivity.class)));
    }
}