package com.example.octodroid

import android.app.Application

import com.example.octodroid.data.GitHub
import com.rejasupotaro.octodroid.http.ApiClient
import com.rejasupotaro.octodroid.http.CacheControl

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initializeApiClient()
    }

    private fun initializeApiClient() {
        val apiClient = ApiClient()
        apiClient.userAgent("octodroid")
        apiClient.cache(CacheControl.Builder().file(this).build())
        GitHub.initialize(apiClient)
    }
}
