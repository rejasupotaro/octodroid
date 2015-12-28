package com.example.octodroid.data

import com.google.gson.Gson
import com.rejasupotaro.octodroid.GitHubClient
import com.rejasupotaro.octodroid.GsonProvider
import com.rejasupotaro.octodroid.http.ApiClient

object GitHub {
    private var client: GitHubClient? = null

    fun initialize(apiClient: ApiClient) {
        if (client == null) {
            synchronized (GitHub::class.java) {
                if (client == null) {
                    client = GitHubClient(apiClient)
                }
            }
        }
    }

    fun client(): GitHubClient {
        if (client == null) {
            synchronized (GitHub::class.java) {
                if (client == null) {
                    client = GitHubClient(ApiClient())
                }
            }
        }
        return client
    }

    fun gson(gson: Gson) {
        GsonProvider.set(gson)
    }
}
