package com.example.octodroid;

import android.app.Application;

import com.example.octodroid.data.GitHub;
import com.rejasupotaro.octodroid.http.ApiClient;
import com.rejasupotaro.octodroid.http.CacheControl;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initializeApiClient();
    }

    private void initializeApiClient() {
        ApiClient apiClient = new ApiClient();
        apiClient.userAgent("octodroid");
        apiClient.cache(new CacheControl.Builder()
                .file(this)
                .build());
        GitHub.initialize(apiClient);
    }
}
