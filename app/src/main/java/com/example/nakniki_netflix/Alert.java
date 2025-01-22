package com.example.nakniki_netflix;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

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

        // Set the background color dynamically based on the type using color from colors.xml
        int colorRes = getColorForType(type);  // Get the appropriate color resource
        ColorDrawable colorDrawable = new ColorDrawable(ContextCompat.getColor(activity, colorRes));
        alertView.setBackground(colorDrawable);

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

    private int getColorForType(String type) {
        // Simplify the logic by directly getting the color resource
        switch (type.toLowerCase()) {
            case "success":
                return R.color.success_color;  // Success color defined in colors.xml
            case "error":
                return R.color.error_color;    // Error color defined in colors.xml
            case "warning":
                return R.color.warning_color;  // Warning color defined in colors.xml
            default:
                return R.color.info_color;    // Default (Info) color
        }
    }
}
