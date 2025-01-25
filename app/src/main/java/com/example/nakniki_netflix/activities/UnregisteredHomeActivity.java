package com.example.nakniki_netflix.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.nakniki_netflix.R;

public class UnregisteredHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_unregistered);

        Button btnLogin = findViewById(R.id.login_button);
        Button btnSignup = findViewById(R.id.signup_button);

        // Sets the buttons to navigate to their screens
        btnLogin.setOnClickListener(v -> navigateToLogin());
        btnSignup.setOnClickListener(v -> navigateToSignup());
    }
    // Navigates to login screen
    private void navigateToLogin() {
        // Navigate to login activity
        Intent intent = new Intent(UnregisteredHomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    // Navigates to signup screen
    private void navigateToSignup() {
        // Navigate to login activity
        Intent intent = new Intent(UnregisteredHomeActivity.this, SignupActivity.class);
        startActivity(intent);
        finish();
    }
}
