package com.example.octodroid;

import android.app.Application;

import com.rejasupotaro.octodroid.GitHub;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        GitHub.client().cache(this);
    }
}
