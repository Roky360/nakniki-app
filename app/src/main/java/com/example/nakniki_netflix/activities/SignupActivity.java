package com.example.nakniki_netflix.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nakniki_netflix.R;
import com.example.nakniki_netflix.widgets.Alert;
import com.example.nakniki_netflix.widgets.AvatarCircle;

public class SignupActivity extends AppCompatActivity {

    private ImageView backButton;
    private TextView title;
    private EditText username, email, password, verifyPassword;
    private Button signupButton;
    private TextView loginText;
    private LinearLayout avatarContainer;
    private String selectedAvatar = "";
    private Alert alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize views
        backButton = findViewById(R.id.back_button);
        title = findViewById(R.id.title);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        verifyPassword = findViewById(R.id.verify_password);
        signupButton = findViewById(R.id.signup_button);
        loginText = findViewById(R.id.login_text);
        avatarContainer = findViewById(R.id.avatarContainer);
        alert = new Alert(this);

        // Set listeners
        backButton.setOnClickListener(v -> onBackPressed());  // Go back when back button is clicked
        loginText.setOnClickListener(v -> navigateToLogin());  // Navigate to login when login text is clicked

        signupButton.setOnClickListener(v -> {
            // Validate the fields and create an account
            if (validateFields()) {
                createAccount();
            }
        });

        // Populate avatar selection
        populateAvatars();
    }

    private void populateAvatars() {
        // insert the avatars
        int[] avatarResIds = {R.drawable.avatar1,
                R.drawable.avatar2,
                R.drawable.avatar3,
                R.drawable.avatar4,
                R.drawable.avatar5,
                R.drawable.avatar6,
                R.drawable.avatar7,
                R.drawable.avatar8,
                R.drawable.avatar9,
                R.drawable.avatar10,
                R.drawable.avatar11};

        for (int resId : avatarResIds) {
            AvatarCircle avatarCircle = new AvatarCircle(this); // Create AvatarCircle widget
            avatarCircle.setAvatarImage(resId);  // Set the avatar image
            avatarCircle.setAvatarRadius(avatarCircle.dpToPx(40));

            // Set the click listener for the avatar
            avatarCircle.setOnAvatarClickListener(v -> onAvatarSelected(avatarCircle, resId));

            // Add the avatar circle to the container
            avatarContainer.addView(avatarCircle);
        }
    }

    private void onAvatarSelected(AvatarCircle avatarCircle, int resId) {
        // Highlight the selected avatar
        for (int i = 0; i < avatarContainer.getChildCount(); i++) {
            AvatarCircle avatar = (AvatarCircle) avatarContainer.getChildAt(i);
            avatar.setBackgroundColor(Color.TRANSPARENT); // Remove previous selection
        }

        avatarCircle.setBackgroundColor(Color.RED); // Highlight selected avatar
        selectedAvatar = String.valueOf(resId); // Save the selected avatar resource ID
    }

    private boolean validateFields() {
        String usernameText = username.getText().toString().trim();
        String emailText = email.getText().toString().trim();
        String passwordText = password.getText().toString().trim();
        String verifyPasswordText = verifyPassword.getText().toString().trim();

        // Check if the fields are empty
        if (usernameText.isEmpty() || emailText.isEmpty() || passwordText.isEmpty() || verifyPasswordText.isEmpty()) {
            showAlert("All fields are required.", "error");
            return false;
        }

        // Check if passwords match
        if (!passwordText.equals(verifyPasswordText)) {
            showAlert("Passwords do not match.", "error");
            return false;
        }

        // Ensure an avatar is selected
        if (selectedAvatar.isEmpty()) {
            showAlert("Please select an avatar.", "error");
            return false;
        }

        return true;
    }

    private void createAccount() {
        // You can add logic here to handle user registration (e.g., saving to a database)
        String usernameText = username.getText().toString().trim();
        String emailText = email.getText().toString().trim();
        String passwordText = password.getText().toString().trim();

        // For now, show a success message and navigate to the next screen
        showAlert("Account created successfully for " + usernameText, "success");

        // TODO add logic of create account

        // Redirect to the login screen or main screen TODO redirect to home
//        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
//        startActivity(intent);
//        finish(); // Close the signup activity
    }

    private void navigateToLogin() {
        // Navigate to login activity TODO redirect to login activity
//        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
//        startActivity(intent);
        showAlert("Navigate to Login", "success");
    }

    private void showAlert(String message, String type) {
        alert.show(message, type);
    }
}
