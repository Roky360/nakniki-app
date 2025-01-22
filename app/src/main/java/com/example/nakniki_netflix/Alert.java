package com.example.nakniki_netflix;

import android.app.Activity;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Alert {

    private final Activity activity;
    private View alertView;

    public Alert(Activity activity) {
        this.activity = activity;
    }

    public void show(String message, String type) {
        if (alertView == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            alertView = inflater.inflate(R.layout.custom_alert, null);

            // Add the alert view to the root layout
            FrameLayout rootView = activity.findViewById(android.R.id.content);

            // Set up layout parameters for positioning
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
            layoutParams.bottomMargin = 50; // Distance from the bottom of the screen

            rootView.addView(alertView, layoutParams);
        }

        TextView alertMessage = alertView.findViewById(R.id.alertMessage);
        alertMessage.setText(message);

        // Set the background color dynamically based on the type
        switch (type.toLowerCase()) {
            case "success":
                alertView.setBackground(activity.getDrawable(R.drawable.alert_background));
                break;
            case "error":
                alertView.setBackground(activity.getDrawable(R.drawable.alert_background));
                break;
            case "warning":
                alertView.setBackground(activity.getDrawable(R.drawable.alert_background));
                break;
            default:
                alertView.setBackground(activity.getDrawable(R.drawable.alert_background));
                break;
        }

        // Show the alert view
        alertView.setVisibility(View.VISIBLE);
        alertView.setAlpha(1.0f);

        // Automatically fade out and hide the alert after 3 seconds
        new Handler().postDelayed(() -> {
            alertView.animate()
                    .alpha(0.0f)
                    .setDuration(1000)
                    .withEndAction(() -> alertView.setVisibility(View.GONE));
        }, 3000);
    }
}
