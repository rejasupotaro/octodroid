package com.example.octodroid;

import android.app.Application;

import com.rejasupotaro.octodroid.GitHub;
import com.rejasupotaro.octodroid.http.CacheControl;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        GitHub.client().cache(new CacheControl.Builder()
                .file(this)
                .build());
    }
}
