package com.example.nakniki_netflix.activities;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nakniki_netflix.R;

public class WatchMovieActivity extends AppCompatActivity {

    private VideoView videoView;
    private ImageView playPauseButton;
    private boolean isPaused = false;
    private static final String TAG = "MOVIE_PLAYER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Force full-screen landscape mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        setContentView(R.layout.activity_watch_movie);

        // Get the Movie ID from Intent
        String movieId = getIntent().getStringExtra("movieId");
        if (movieId == null) {
            Log.e(TAG, "No movie ID received.");
            finish();
            return;
        }

        videoView = findViewById(R.id.video_player);

        // Construct the movie URL
        String videoUrl = getResources().getString(R.string.api_base_url) + getResources().getString(R.string.api_movies_base_url) + movieId + ".mp4";
        Log.d(TAG, "Playing Movie URL: " + videoUrl);

        videoView.setVideoPath(videoUrl);

        // Setting up video player settings
        videoView.setOnPreparedListener(mp -> {
            mp.setVolume(1.0f, 1.0f); // Set volume (change if needed)
            videoView.start();    // Autoplay the video

            // Auto-close when the video is over
            mp.setOnCompletionListener(mediaPlayer -> finish());
        });

        ImageView exitButton = findViewById(R.id.exit_button);
        exitButton.setOnClickListener(v -> finish());
        playPauseButton = findViewById(R.id.play_pause_button);
        playPauseButton.setOnClickListener(v -> togglePlayPause());

        // Handle errors
        videoView.setOnErrorListener((mp, what, extra) -> {
            Log.e(TAG, "Error playing video: " + what + ", " + extra);
            return true;
        });

    }

    /**
     * Toggles between play and pause
     */
    private void togglePlayPause() {
        if (videoView.isPlaying()) {
            videoView.pause();
            playPauseButton.setImageResource(R.drawable.ic_play);
            isPaused = true;
        } else {
            videoView.start();
            playPauseButton.setImageResource(R.drawable.ic_pause);
            isPaused = false;
        }
    }
}