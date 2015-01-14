package com.rejasupotaro.octodroid;

import com.google.gson.Gson;
import com.rejasupotaro.octodroid.http.ApiClient;

public class GitHub {
    private static GitHubClient client;

    public static synchronized GitHubClient client() {
        if (client == null) {
            client = new GitHubClient(new ApiClient());
        }
        return client;
    }

    public static void gson(Gson gson) {
        GsonProvider.set(gson);
    }
}
