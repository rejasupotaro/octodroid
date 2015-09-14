package com.rejasupotaro.octodroid.http;

import android.text.TextUtils;
import android.util.Log;

import com.rejasupotaro.octodroid.ConnectivityObserver;
import com.rejasupotaro.octodroid.http.interceptors.LoggingInterceptor;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Credentials;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;

public class ApiClient {
    private static final String TAG = ApiClient.class.getSimpleName();
    private static final String DEFAULT_ENDPOINT = "https://api.github.com";

    protected static OkHttpClient okHttpClient;

    private String endpoint = DEFAULT_ENDPOINT;
    private String userAgent;
    private String authorization;
    private CacheControl cacheControl;

    public void endpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public void userAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public void authorization(String username, String password) {
        this.authorization = Credentials.basic(username, password);
    }

    public void authorization(String accessToken) {
        this.authorization = accessToken;
    }

    public void cache(CacheControl cacheControl) {
        try {
            Cache cache = new Cache(cacheControl.getFile(), cacheControl.getMaxCacheSize());
            okHttpClient.setCache(cache);
            this.cacheControl = cacheControl;
        } catch (IOException e) {
            Log.e(TAG, "Can't create cache: " + e.toString());
        }
    }

    public ApiClient() {
        okHttpClient = new OkHttpClient();
        okHttpClient.interceptors().add(new LoggingInterceptor());
    }

    private void setUrl(Request.Builder builder, RequestCreator requestCreator) {
        builder.url(endpoint + requestCreator.getPath());
    }

    private void setBody(Request.Builder builder, RequestCreator requestCreator) {
        Method method = requestCreator.getMethod();

        switch (method) {
            case GET:
                builder.get();
                break;
            case POST:
                builder.post(requestCreator.getBody());
                break;
            case PUT:
                builder.put(requestCreator.getBody());
                break;
            case PATCH:
                builder.patch(requestCreator.getBody());
                break;
            case DELETE:
                builder.delete();
                break;
        }
    }

    private void setHeaders(Request.Builder builder, RequestCreator requestCreator) {
        Map<String, String> headers = requestCreator.getHeaders();

        if (headers == null) {
            headers = new HashMap<>();
        }
        Map<String, String> newHeaders = baseHeader(requestCreator);
        newHeaders.putAll(headers);
        if (!TextUtils.isEmpty(authorization)) {
            newHeaders.put(Header.AUTHORIZATION, authorization);
        }

        for (String name : newHeaders.keySet()) {
            builder.addHeader(name, newHeaders.get(name));
        }
    }

    private Map<String, String> baseHeader(RequestCreator requestCreator) {
        final boolean noCache = requestCreator.isNoCache();
        final boolean noStore = requestCreator.isNoStore();

        return new HashMap<String, String>() {{
            put(Header.ACCEPT, "application/json");
            if (!TextUtils.isEmpty(userAgent)) {
                put(Header.USER_AGENT, userAgent);
            }
            if (!ConnectivityObserver.isConnect() && cacheControl != null) {
                put(Header.CACHE_CONTROL, "only-if-cached, max-stale=" + cacheControl.getMaxStale());
            } else if (noCache && noStore) {
                put(Header.CACHE_CONTROL, "no-cache, no-store");
            } else if (noCache) {
                put(Header.CACHE_CONTROL, "no-cache");
            } else if (noStore) {
                put(Header.CACHE_CONTROL, "no-store");
            }
        }};
    }

    public RequestCreator request(final Method method, final String path) {
        return new RequestCreator(this, method, path);
    }

    public RequestCreator request(final Method method, final String path, final Params params) {
        return new RequestCreator(this, method, String.format("%s?%s", path, params.toString()));
    }

    public com.squareup.okhttp.Response requestInternal(RequestCreator requestCreator) throws IOException {
        Request.Builder builder = new Request.Builder();
        setUrl(builder, requestCreator);
        setBody(builder, requestCreator);
        setHeaders(builder, requestCreator);

        Request request = builder.build();
        return okHttpClient.newCall(request).execute();
    }

    public static class RequestSubscriber implements Observable.OnSubscribe<com.squareup.okhttp.Response> {
        private ApiClient apiClient;
        private RequestCreator requestCreator;

        public RequestSubscriber(ApiClient apiClient, RequestCreator requestCreator) {
            this.apiClient = apiClient;
            this.requestCreator = requestCreator;
        }

        @Override
        public void call(Subscriber<? super com.squareup.okhttp.Response> subscriber) {
            try {
                com.squareup.okhttp.Response response = apiClient.requestInternal(requestCreator);
                subscriber.onNext(response);
            } catch (IOException e) {
                subscriber.onError(e);
            }
        }
    }
}

