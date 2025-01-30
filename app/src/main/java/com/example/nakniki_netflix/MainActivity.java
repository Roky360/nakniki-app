package com.example.nakniki_netflix;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.nakniki_netflix.activities.SearchScreenActivity;
import com.example.nakniki_netflix.api.Resource;
import com.example.nakniki_netflix.entities.Movie;
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
                .replace(R.id.fragment_container, new HomeTestFragment())
                .commit();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);

        bottomNav.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home_page) {
                selectedFragment = new HomeTestFragment();
            } else if (itemId == R.id.nav_movies_page) {
                selectedFragment = new MoviesScreenFragment();
            } else if (itemId == R.id.nav_profile_page) {
                selectedFragment = new ProfileTestFragment();
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

//        binding.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAnchorView(R.id.fab)
//                        .setAction("Action", null).show();
//            }
//        });

        // view model test
//        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
//        userViewModel.getUserData().observe(this, user -> {
//            if (user != null) {
//                Objects.requireNonNull(getSupportActionBar()).setTitle(user.getUsername());
//            }
//        });
//
//        binding.fab.setOnClickListener(view -> userViewModel.getUserData().setValue(new User("0", "Hemihemi " + i++, "", "", "")));

//        CategoryDao categoryDao = AppDB.getInstance(this).categoryDao();
//        CategoryAPI categoryAPI = RetrofitClient.getInstance().create(CategoryAPI.class);
//        CategoryRepository repository = new CategoryRepository(categoryDao, categoryAPI);

        // category view model test
//        categoryViewModel = new ViewModelProvider(this, new CategoryViewModelFactory(new CategoryRepository())).get(CategoryViewModel.class);
//
//        categoryViewModel.getAllCategories().observe(this, categories -> {
//            if (categories.getStatus() == Resource.Status.SUCCESS) {
//                StringBuilder cats = new StringBuilder();
//                for (Category category : categories.getData()) {
//                    cats.append(category.getName()).append("\n");
//                }
//                binding.catList.setText(cats.toString());
//            } else if (categories.getStatus() == Resource.Status.ERROR) {
//                binding.catList.setText(categories.getMessage());
//            } else {
//                binding.catList.setText("Loading...");
//            }
//        });
//
//        binding.fab.setOnClickListener(view -> {
//            categoryViewModel.addCategory(
//                    new Category(String.valueOf(i), "New category! " + i++, true)
//            );
//        });

        // movies view model test
//        movieViewModel = new ViewModelProvider(this, new MovieViewModelFactory(new MovieRepository())).get(MovieViewModel.class);


//        movieViewModel.getMoviesByCategories().observe(this, catWithMovies -> {
//            if (catWithMovies.getStatus() == Resource.Status.SUCCESS) {
//                StringBuilder cats = new StringBuilder();
//                List<CategoryWithMovies> data = catWithMovies.getData();
//                for (CategoryWithMovies cat : data) {
//                    StringBuilder movies = new StringBuilder();
//                    movies.append(cat.getCategory()).append(":\n");
//                    for (Movie m : cat.getMovies()) {
//                        movies.append("\t").append(m.getName()).append("\n");
//                    }
//                    cats.append(movies);
//                }
//                binding.catList.setText(cats.toString());
//            } else if (catWithMovies.getStatus() == Resource.Status.ERROR) {
//                binding.catList.setText(catWithMovies.getMessage());
//            } else {
//                binding.catList.setText("Loading...");
//            }
//        });

//        movieViewModel.searchMovies("a").observe(this, resource -> {
//            if (resource.getStatus() == Resource.Status.SUCCESS) {
//                List<Movie> res = resource.getData();
//                StringBuilder movies = new StringBuilder();
//                for (Movie m : res) {
//                    movies.append(m.getName()).append("\n");
//                }
//
//                binding.catList.setText(res.toString());
//            } else {
//                binding.catList.setText(resource.getMessage());
//            }
//        });

//        binding.fab.setOnClickListener(view -> {
//            ViewModelUtils.observeUntil(movieViewModel.searchMovies(binding.editText.getText().toString()),
//                    resource -> {
//                        if (resource.getStatus() == Resource.Status.SUCCESS) {
//                            List<Movie> res = resource.getData();
//                            StringBuilder movies = new StringBuilder();
//                            for (Movie m : res) {
//                                movies.append(m.getName()).append("\n");
//                            }
//
//                            binding.catList.setText(movies.toString());
//                        } else {
//                            binding.catList.setText(resource.getMessage());
//                        }
//
//                    },
//                    resource -> resource.getStatus() == Resource.Status.SUCCESS
//            );
//        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

//    public static Context getAppContext() {
//        return instance.getApplicationContext();
//    }
}