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
    private Pagination pagination;

    public T entity() {
        return entity;
    }

    public Headers headers() {
        return headers;
    }

    public int code() {
        return code;
    }

    public boolean isSuccessful() {
        return code >= 200 && code < 300;
    }

    public String body() {
        return body;
    }

    public int nextPage() {
        return pagination.getNextLink().getPage();
    }

    public Observable<Response<T>> next() {
        return next;
    }

    public boolean hasNext() {
        return pagination.hasNext();
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
        Headers headers = r.headers();

        Response<T> response = new Response<>();
        response.entity = GsonProvider.get().fromJson(
                bodyString,
                type.getType());
        response.headers = headers;
        response.code = r.code();
        response.body = bodyString;
        response.pagination = PaginationHeaderParser.parse(response);

        return response;
    }
}
