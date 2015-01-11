package com.rejasupotaro.octodroid;

import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.rejasupotaro.octodroid.http.AbstractClient;
import com.rejasupotaro.octodroid.http.ApiClient;
import com.rejasupotaro.octodroid.http.Method;
import com.rejasupotaro.octodroid.http.Response;
import com.rejasupotaro.octodroid.models.SearchResult;
import com.rejasupotaro.octodroid.models.User;

import org.apache.http.protocol.HTTP;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import rx.Observable;

public class GitHubClient extends AbstractClient {
    public GitHubClient(ApiClient apiClient) {
        super(apiClient);
    }

    public Observable<Response<User>> myself() {
        return request(Method.GET, "/user", null, null, new TypeToken<User>() {
        });
    }

    public Observable<Response<SearchResult>> searchRepositories(String q, String sort, String order, int page, int perPage) {
        String path = String.format("/search/repositories?q=%s&sort=%s&order=%s&page=%d&per_page=%d", encode(q), sort, order, page, perPage);
        return request(Method.GET, path, null, null, new TypeToken<SearchResult>() {
        });
    }

    private static String encode(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            return URLEncoder.encode(str, HTTP.UTF_8);
        } catch (UnsupportedEncodingException e) {
            // Should not occur
            return "";
        }
    }
}

