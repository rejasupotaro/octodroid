package com.rejasupotaro.octodroid.http;

import java.io.IOException;

import rx.Observable;
import rx.Subscriber;

public abstract class AbstractClient {
    private ApiClient apiClient;

    public void userAgent(String userAgent) {
        apiClient.userAgent(userAgent);
    }

    public void authorization(String username, String password) {
        apiClient.authorization(username, password);
    }

    public void cache(CacheControl cacheControl) {
        apiClient.cache(cacheControl);
    }

    public AbstractClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public RequestCreator request(final Method method, final String path) {
        return new RequestCreator(apiClient, method, path);
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
                com.squareup.okhttp.Response response = apiClient.request(requestCreator);
                subscriber.onNext(response);
            } catch (IOException e) {
                subscriber.onError(e);
            }
        }
    }
}

