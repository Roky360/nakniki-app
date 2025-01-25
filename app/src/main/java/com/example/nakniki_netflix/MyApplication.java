package com.example.nakniki_netflix;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this; // Initialize the instance
    }

    public static Context getAppContext() {
        return instance.getApplicationContext();
    }
}
