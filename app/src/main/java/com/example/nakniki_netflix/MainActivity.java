package com.example.nakniki_netflix;

import android.content.Context;
import android.os.Bundle;

import com.example.nakniki_netflix.entities.User;
import com.example.nakniki_netflix.view_models.UserViewModel;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.nakniki_netflix.databinding.ActivityMainBinding;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static MainActivity instance;

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private UserViewModel userViewModel;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

//        binding.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAnchorView(R.id.fab)
//                        .setAction("Action", null).show();
//            }
//        });

        // view model test
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getUserData().observe(this, user -> {
            if (user != null) {
                Objects.requireNonNull(getSupportActionBar()).setTitle(user.getUsername());
            }
        });

        binding.fab.setOnClickListener(view -> userViewModel.getUserData().setValue(new User("0", "Hemihemi " + i++, "", "", "")));
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public static Context getAppContext() {
        return instance.getApplicationContext();
    }
}