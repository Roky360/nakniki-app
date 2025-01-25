package com.example.nakniki_netflix.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nakniki_netflix.R;
import com.example.nakniki_netflix.widgets.Alert;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize UI elements
        EditText usernameEditText = findViewById(R.id.username);
        EditText passwordEditText = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.login_button);
        TextView signupText = findViewById(R.id.signup_text);
        ImageView backButton = findViewById(R.id.back_button);
        Alert alert = new Alert(this);

        // Handle Login Button Click
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the username and password
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Simple validation check (you can add more advanced validation)
                if (username.isEmpty() || password.isEmpty()) {
                    alert.show("Please fill in all fields.", "error");
                } else {
                    // TODO handle the login logic
                    alert.show("name:" + username + " password:" + password + " login...", "success");
                }
            }
        });

        // Handle Sign-Up Text Click (Navigates to SignUp Activity)
        signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the Sign-Up activity TODO remove this when the signup activity is implemented
                // Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                //startActivity(intent);
                alert.show("move to signup", "success");
            }
        });

        // Handle Back Button Click
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the current activity and go back to the previous screen
                onBackPressed();
            }
        });
    }
}