package com.example.nakniki_netflix;

import android.content.Context;
import android.util.AttributeSet;
import android.graphics.Typeface;

import androidx.annotation.Nullable;

public class CategoryBadge extends androidx.appcompat.widget.AppCompatTextView {

    // constructors
    public CategoryBadge(Context context) {
        super(context);
        init();
    }
    public CategoryBadge(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public CategoryBadge(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // set default padding, text size and background
        setPadding(24, 16, 24, 16);
        setTextSize(20);
        setTextColor(getResources().getColor(R.color.colorText));
        setBackgroundResource(R.drawable.category_badge_background);
        setTypeface(getTypeface(), Typeface.BOLD);
    }

    /**
     * set the category name
     * @param categoryName the category name
     */
    public void setCategoryName(String categoryName) {
        setText(categoryName);
    }
}
