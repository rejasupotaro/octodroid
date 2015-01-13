package com.rejasupotaro.octodroid.http;

import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.rejasupotaro.octodroid.GsonProvider;
import com.squareup.okhttp.Headers;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import rx.Observable;

public class Response<T> {
    private T entity;
    private Headers headers;
    private int code;
    private String body;
    private Observable<Response<T>> next;

    private void entity(T entity) {
        this.entity = entity;
    }

    public T entity() {
        return entity;
    }

    private void headers(Headers headers) {
        this.headers = headers;
    }

    public Headers headers() {
        return headers;
    }

    private void code(int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }

    public boolean isSuccessful() {
        return code >= 200 && code < 300;
    }

    private void body(String body) {
        this.body = body;
    }

    public String body() {
        return body;
    }

//    public int nextPage() {
//        return extra.getLinks().getNext().getPage();
//    }

    public Observable<Response<T>> next() {
        return next;
    }

    public boolean hasNext() {
        return false; // TODO
//        return (extra != null) && (extra.getLinks() != null) && (extra.getLinks().hasNext());
    }

    public void next(Observable<Response<T>> next) {
        this.next = next;
    }

    public JSONObject bodyAsJson() {
        if (TextUtils.isEmpty(body)) {
            return new JSONObject();
        }

        try {
            return new JSONObject(body);
        } catch (JSONException e) {
            return new JSONObject();
        }
    }

    public static <T> Response<T> parse(com.squareup.okhttp.Response r, TypeToken<T> type) throws IOException {
        String bodyString = r.body().string();

        Response<T> response = new Response<>();
        T entity = GsonProvider.get().fromJson(
                bodyString,
                type.getType());
        response.entity(entity);
        response.headers(r.headers());
        response.code(r.code());
        response.body(bodyString);

        return response;
    }
}
