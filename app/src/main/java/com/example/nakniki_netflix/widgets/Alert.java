package com.example.nakniki_netflix.widgets;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.nakniki_netflix.R;

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

        // Create a GradientDrawable with rounded corners and set the background color dynamically
        int colorRes = getColorForType(type);  // Get the appropriate color resource
        Drawable drawable = createRoundedBackground(colorRes);
        alertView.setBackground(drawable);

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

    private Drawable createRoundedBackground(int colorRes) {
        // Define the radius for the corners
        float radius = 50f;  // Adjust to the desired corner radius in dp

        // Create a GradientDrawable
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(ContextCompat.getColor(activity, colorRes));  // Set the background color
        drawable.setCornerRadius(radius);  // Set rounded corners
        return drawable;
    }

    private int getColorForType(String type) {
        // return the color according the alert type
        switch (type.toLowerCase()) {
            case "success":
                return R.color.success_color;
            case "error":
                return R.color.error_color;
            case "warning":
                return R.color.warning_color;
            default:
                return R.color.info_color;
        }
    }
}
