package com.example.nakniki_netflix;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.nakniki_netflix.activities.SearchScreenActivity;
import com.example.nakniki_netflix.api.Resource;
import com.example.nakniki_netflix.entities.Movie;
import com.example.nakniki_netflix.fragments.ProfileFragment;
import com.example.nakniki_netflix.fragments.RegisteredHomeFragment;
import com.example.nakniki_netflix.fragments.MoviesScreenFragment;
import com.example.nakniki_netflix.repositories.MovieRepository;
import com.example.nakniki_netflix.view_models.CategoryViewModel;
import com.example.nakniki_netflix.view_models.MovieViewModel;
import com.example.nakniki_netflix.view_models.MovieViewModelFactory;
import com.example.nakniki_netflix.view_models.UserViewModel;
import com.example.nakniki_netflix.view_models.ViewModelUtils;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.nakniki_netflix.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static MainActivity instance;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new RegisteredHomeFragment())
                .commit();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);

        bottomNav.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home_page) {
                selectedFragment = new RegisteredHomeFragment();
            } else if (itemId == R.id.nav_movies_page) {
                selectedFragment = new MoviesScreenFragment();
            } else if (itemId == R.id.nav_profile_page) {
                selectedFragment = new ProfileFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }
            return true;
        });

        binding.fab.setOnClickListener(v -> {
            Intent intent = new Intent(this, SearchScreenActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

}