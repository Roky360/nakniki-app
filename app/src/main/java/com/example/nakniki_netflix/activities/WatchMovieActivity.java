package com.example.nakniki_netflix.activities;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nakniki_netflix.R;

public class WatchMovieActivity extends AppCompatActivity {

    private VideoView videoView;
    private static final String TAG = "MOVIE_PLAYER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Force full-screen landscape mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        setContentView(R.layout.activity_watch_movie);

        // Get the Movie ID from Intent
        String movieId = getIntent().getStringExtra("movie_id");
        if (movieId == null) {
            Log.e(TAG, "No movie ID received.");
            finish();
            return;
        }

        videoView = findViewById(R.id.video_player);
        ImageView exitButton = findViewById(R.id.exit_button);
        // Set exit button to go to the previous screen once clicked
        exitButton.setOnClickListener(v -> finish());

        // Construct the movie URL
        String videoUrl = getResources().getString(R.string.api_movies_base_url) + movieId + ".mp4";
        Log.d(TAG, "Playing Movie URL: " + videoUrl);

        videoView.setVideoPath(videoUrl);

        // Setting up video player settings
        videoView.setOnPreparedListener(mp -> {
            mp.setVolume(1.0f, 1.0f); // Set volume (change if needed)
            videoView.start();    // Autoplay the video
        });

        // Handle errors
        videoView.setOnErrorListener((mp, what, extra) -> {
            Log.e(TAG, "Error playing video: " + what + ", " + extra);
            return true;
        });
    }
}