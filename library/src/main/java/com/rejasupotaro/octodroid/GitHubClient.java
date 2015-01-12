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
import rx.functions.Func1;

public class GitHubClient extends AbstractClient {
    private static final int PER_PAGE = 20;

    public GitHubClient(ApiClient apiClient) {
        super(apiClient);
    }

    public Observable<Response<User>> user() {
        return request(Method.GET, "/user", null, null, new TypeToken<User>() {
        });
    }

    public Observable<Response<User>> user(String username) {
        String path = String.format("/users/%s", username);
        return request(Method.GET, path, null, null, new TypeToken<User>() {
        });
    }

    public Observable<Response<SearchResult>> searchRepositories(final String q, final String sort, final String order) {
        return searchRepositories(q, sort, order, 1);
    }

    public Observable<Response<SearchResult>> searchRepositories(final String q, final String sort, final String order, final int page) {
        String path = String.format("/search/repositories?q=%s&sort=%s&order=%s&page=%d&per_page=%d", encode(q), sort, order, page, PER_PAGE);
        return request(Method.GET, path, null, null, new TypeToken<SearchResult>() {
        }).map(new Func1<Response<SearchResult>, Response<SearchResult>>() {
            @Override
            public Response<SearchResult> call(Response<SearchResult> r) {
                r.next(searchRepositories(q, sort, order, page + 1));
                return r;
            }
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

