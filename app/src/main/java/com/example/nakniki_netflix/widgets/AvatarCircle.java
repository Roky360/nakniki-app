package com.example.nakniki_netflix.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import com.example.nakniki_netflix.R;

public class AvatarCircle extends FrameLayout {

    private ImageView avatarImage;

    private static final int DEFAULT_RADIUS_DP = 70;

    public AvatarCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    private void initialize(Context context) {
        // Inflate the layout for the AvatarCircle
        LayoutInflater.from(context).inflate(R.layout.avatar_circle, this, true);

        // Bind the ImageView
        avatarImage = findViewById(R.id.avatarImage);

        // Set default radius
        setAvatarRadius(dpToPx(DEFAULT_RADIUS_DP));
    }

    public void setAvatarImage(@DrawableRes int drawableRes) {
        avatarImage.setImageResource(drawableRes);
    }

    public void setOnAvatarClickListener(OnClickListener listener) {
        avatarImage.setOnClickListener(listener);
    }

    public void setAvatarRadius(int radiusPx) {
        // Update the size of the FrameLayout to maintain circular shape
        FrameLayout avatarFrame = findViewById(R.id.avatarFrame); // Get the FrameLayout
        avatarFrame.getLayoutParams().width = radiusPx * 2; // Set width to diameter
        avatarFrame.getLayoutParams().height = radiusPx * 2; // Set height to diameter
        avatarFrame.requestLayout(); // Request layout update
    }

    public int dpToPx(int dp) {
        return (int) (dp * getContext().getResources().getDisplayMetrics().density);
    }
}