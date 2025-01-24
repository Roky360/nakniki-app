package com.example.nakniki_netflix.view_models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.function.Predicate;

public class ViewModelUtils {
    /**
     * Utility function to observe a LiveData once.
     */
    public static <T> void observeUntil(LiveData<T> liveData, Observer<T> observer, Predicate<T> stopCondition) {
        liveData.observeForever(new Observer<T>() {
            @Override
            public void onChanged(T t) {
                observer.onChanged(t); // trigger the observer
                // remove observer only when stop condition is met
                if (stopCondition.test(t)) {
                    liveData.removeObserver(this);
                }
            }
        });
    }
}
