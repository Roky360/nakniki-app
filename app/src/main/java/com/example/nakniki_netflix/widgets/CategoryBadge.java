package com.example.nakniki_netflix.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.graphics.Typeface;

import androidx.annotation.Nullable;

import com.example.nakniki_netflix.R;

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
        setPadding(16, 10, 16, 10);
        setTextSize(14);
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
