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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.nakniki_netflix.R;
import com.example.nakniki_netflix.api.Resource;
import com.example.nakniki_netflix.entities.User;
import com.example.nakniki_netflix.repositories.UserRepository;
import com.example.nakniki_netflix.view_models.UserViewModel;
import com.example.nakniki_netflix.view_models.UserViewModelFactory;
import com.example.nakniki_netflix.view_models.ViewModelUtils;
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

            // Add margin (spacing) to each avatar
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 20, 0); // Add 20px margin to the right
            avatarCircle.setLayoutParams(params);

            // Add the avatar circle to the container
            avatarContainer.addView(avatarCircle);
        }
    }

    private void onAvatarSelected(AvatarCircle avatarCircle, int resId) {
        // Base size for avatars
        int baseSize = avatarCircle.dpToPx(40);  // 40dp (adjust as needed)
        int selectedSize = avatarCircle.dpToPx(50); // Increased size for selected avatar

        // Reset size of all avatars to base size
        for (int i = 0; i < avatarContainer.getChildCount(); i++) {
            AvatarCircle avatar = (AvatarCircle) avatarContainer.getChildAt(i);
            avatar.setAvatarRadius(baseSize);  // Reset to base size
        }

        // Increase size of the selected avatar
        avatarCircle.setAvatarRadius(selectedSize);

        // Save the selected avatar resource ID
        selectedAvatar = getResources().getResourceEntryName(resId);
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

        // create the user
        UserViewModel userViewModel = new ViewModelProvider(this, new UserViewModelFactory(new UserRepository())).get(UserViewModel.class);
        LiveData<Resource<Void>> live = userViewModel.register(usernameText, passwordText, emailText, "/avatars/" + selectedAvatar + ".png");

        ViewModelUtils.observeUntil(live, resource -> {
            if (resource.getStatus() == Resource.Status.SUCCESS) {
                // todo navigate to login
                showAlert("created", "success");
            } else if (resource.getStatus() == Resource.Status.ERROR) {
                showAlert(resource.getMessage(), "error");
            }
        }, resource -> resource.getStatus() == Resource.Status.SUCCESS);
    }

    private void navigateToLogin() {
        // Navigate to login activity
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void showAlert(String message, String type) {
        alert.show(message, type);
    }
}
