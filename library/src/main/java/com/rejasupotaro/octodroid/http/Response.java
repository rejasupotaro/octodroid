package com.rejasupotaro.octodroid.http;

import android.text.TextUtils;

import com.google.gson.JsonSyntaxException;
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
        return parse(r.headers(), r.code(), r.body().string(), type);
    }

    public static <T> Response<T> parse(Headers headers, int statusCode, String body, TypeToken<T> type) throws IOException {
        Response<T> response = new Response<>();
        try {
            response.entity = GsonProvider.get().fromJson(
                    body,
                    type.getType());
        } catch (JsonSyntaxException e) {
            throw new JsonSyntaxException("Can't instantiate " + type.getRawType().getName() + " from " + body);
        }
        response.headers = headers;
        response.code = statusCode;
        response.body = body;
        response.pagination = PaginationHeaderParser.parse(response);

        return response;
    }
}
