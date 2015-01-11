package com.rejasupotaro.octodroid.http;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.RequestBody;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public abstract class AbstractClient {
    private static final String TAG = AbstractClient.class.getSimpleName();
    private static final String DEFAULT_CACHE_FILE_NAME = "response_cache";
    private static final int DEFAULT_MAX_CACHE_SIZE = 3 * 1024 * 1024; // 3MB

    private ApiClient apiClient;

    public void authorization(String username, String password) {
        apiClient.authorization(username, password);
    }

    public void cache(Context context) {
        try {
            cache(new File(context.getCacheDir(), DEFAULT_CACHE_FILE_NAME));
        } catch (IOException e) {
            Log.e(TAG, "Could not create http cache", e);
        }
    }

    public void cache(File httpCacheDirectory) throws IOException {
        apiClient.cache(new Cache(httpCacheDirectory, DEFAULT_MAX_CACHE_SIZE));
    }

    public AbstractClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public <T> Observable<Response<T>> request(final Method method, final String path, final Map<String, String> headers, final RequestBody body, final TypeToken<T> type) {
        return Observable.create(new RequestSubscriber(method, path, headers, body))
                .map(new Func1<com.squareup.okhttp.Response, Response<T>>() {
                    @Override
                    public Response<T> call(com.squareup.okhttp.Response response) {
                        return parseResponse(response, type);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private <T> Response<T> parseResponse(com.squareup.okhttp.Response response, TypeToken<T> type) {
        try {
            return Response.parse(response, type);
        } catch (IOException e) {
            throw new JsonParseException(e);
        }
    }

    public class RequestSubscriber implements Observable.OnSubscribe<com.squareup.okhttp.Response> {
        private Method method;
        private String path;
        private Map<String, String> headers;
        private RequestBody body;

        public RequestSubscriber(Method method, String path, Map<String, String> headers, RequestBody body) {
            this.method = method;
            this.path = path;
            this.headers = headers;
            this.body = body;
        }

        @Override
        public void call(Subscriber<? super com.squareup.okhttp.Response> subscriber) {
            try {
                com.squareup.okhttp.Response response = apiClient.request(method, path, headers, body);
                subscriber.onNext(response);
            } catch (IOException e) {
                subscriber.onError(e);
            }
        }
    }
}

