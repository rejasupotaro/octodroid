package com.rejasupotaro.octodroid;

import com.rejasupotaro.octodroid.http.ApiClient;

public class GitHub {
    private static GitHubClient client;

    public static synchronized GitHubClient client() {
        if (client == null) {
            client = new GitHubClient(new ApiClient());
        }
        return client;
    }
}
