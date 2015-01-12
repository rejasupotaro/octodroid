package com.rejasupotaro.octodroid.http;

import android.text.TextUtils;
import android.util.Log;

import com.rejasupotaro.octodroid.BuildConfig;
import com.rejasupotaro.octodroid.ConnectivityObserver;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Credentials;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ApiClient {
    private static final String TAG = ApiClient.class.getSimpleName();
    private static final String DEFAULT_ENDPOINT = "https://api.github.com";
    private static final int MAX_AGE = 3 * 60 * 60; // 3 hours
    private static final int MAX_STALE = 28 * 24 * 60 * 60; // tolerate 4-weeks stale

    protected static OkHttpClient okHttpClient;

    private String endpoint = DEFAULT_ENDPOINT;
    private String authorization;

    public void endpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public void authorization(String username, String password) {
        this.authorization = Credentials.basic(username, password);
    }

    public void authorization(String accessToken) {
        this.authorization = accessToken;
    }

    public void cache(Cache cache) throws IOException {
        if (cache == null) {
            throw new IOException("Cache should not be null");
        }
        okHttpClient.setCache(cache);
    }

    public ApiClient() {
        okHttpClient = new OkHttpClient();
    }

    public com.squareup.okhttp.Response request(Method method, String path, Map<String, String> headers, RequestBody body) throws IOException {
        Request.Builder builder = new Request.Builder();
        setUrl(builder, path);
        setBody(builder, method, body);
        setHeaders(builder, headers);

        Request request = builder.build();
        com.squareup.okhttp.Response response = okHttpClient.newCall(request).execute();
        dumpIfDebug(request, response);
        return response;
    }

    private void setUrl(Request.Builder builder, String path) {
        builder.url(endpoint + path);
    }

    private void setBody(Request.Builder builder, Method method, RequestBody body) {
        switch (method) {
            case GET:
                builder.get();
                break;
            case POST:
                builder.post(body);
                break;
            case PUT:
                builder.put(body);
                break;
            case DELETE:
                builder.delete();
                break;
        }
    }

    private void setHeaders(Request.Builder builder, Map<String, String> headers) {
        if (headers == null) {
            headers = new HashMap<>();
        }
        Map<String, String> newHeaders = baseHeader();
        newHeaders.putAll(headers);
        if (!TextUtils.isEmpty(authorization)) {
            newHeaders.put(Header.AUTHORIZATION, authorization);
        }

        for (String name : newHeaders.keySet()) {
            builder.addHeader(name, newHeaders.get(name));
        }
    }

    private Map<String, String> baseHeader() {
        return new HashMap<String, String>() {{
            put(Header.ACCEPT, "application/json");
//            put(Header.USER_AGENT, UserAgent.get());
            if (!ConnectivityObserver.isConnect()) {
                put(Header.CACHE_CONTROL, "only-if-cached, max-stale=" + MAX_STALE);
            }
        }};
    }

    public void dumpIfDebug(Request request, com.squareup.okhttp.Response response) {
        if (!BuildConfig.DEBUG) {
            return;
        }
        Log.i(TAG, "===> " + request.toString());
        Log.i(TAG, "<=== " + response.toString());
    }
}

