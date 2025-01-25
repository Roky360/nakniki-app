package com.example.nakniki_netflix.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.nakniki_netflix.R;
import com.example.nakniki_netflix.api.Resource;
import com.example.nakniki_netflix.repositories.UserRepository;
import com.example.nakniki_netflix.view_models.UserViewModel;
import com.example.nakniki_netflix.view_models.UserViewModelFactory;
import com.example.nakniki_netflix.view_models.ViewModelUtils;
import com.example.nakniki_netflix.widgets.Alert;

public class LoginActivity extends AppCompatActivity {

    private Alert alert = new Alert(this);


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

        // add listeners to the buttons
        backButton.setOnClickListener(v -> onBackPressed());
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the username and password
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Simple validation check
                if (username.isEmpty() || password.isEmpty()) {
                    alert.show("Please fill in all fields.", "error");
                } else {
                    // TODO handle the login logic
                    UserViewModel userViewModel = new ViewModelProvider(LoginActivity.this, new UserViewModelFactory(new UserRepository())).get(UserViewModel.class);
                    LiveData<Resource<Void>> live = userViewModel.login(username, password);

                    ViewModelUtils.observeUntil(live, resource -> {
                        if (resource.getStatus() == Resource.Status.SUCCESS) {
                            // TODO redirect to home page
                            showAlert("login in...", "success");
                        } else if (resource.getStatus() == Resource.Status.ERROR) {
                            showAlert(resource.getMessage(), "error");
                        }
                    }, resource -> resource.getStatus() == Resource.Status.SUCCESS);
                }
            }
        });

        // if the user clicks on the signup text, open the signup activity
        signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the Sign-Up activity
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void showAlert(String message, String type) {
        alert.show(message, type);
    }

}