package com.rejasupotaro.octodroid.http;

import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RequestCreator {
    private ApiClient apiClient;
    private Method method;
    private String path;
    private Map<String, String> headers;
    private RequestBody body;
    private boolean noCache;
    private boolean noStore;

    public Method getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public RequestBody getBody() {
        return body;
    }

    public boolean isNoCache() {
        return noCache;
    }

    public boolean isNoStore() {
        return noStore;
    }

    public RequestCreator(ApiClient apiClient, Method method, String path) {
        this.apiClient = apiClient;
        this.method = method;
        this.path = path;
    }

    public RequestCreator headers(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public RequestCreator body(JSONObject json) {
        this.body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString());
        return this;
    }

    public RequestCreator body(RequestBody body) {
        this.body = body;
        return this;
    }

    public RequestCreator noCache() {
        this.noCache = true;
        return this;
    }

    public RequestCreator noStore() {
        this.noStore = true;
        return this;
    }

    public <T> Observable<Response<T>> to(final TypeToken<T> type) {
        return Observable.create(new AbstractClient.RequestSubscriber(apiClient, this))
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
}

