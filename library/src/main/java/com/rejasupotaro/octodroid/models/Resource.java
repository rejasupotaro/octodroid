package com.rejasupotaro.octodroid.models;

import com.rejasupotaro.octodroid.GsonProvider;

public class Resource {
    public String toJson() {
        return GsonProvider.get().toJson(this);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return GsonProvider.get().fromJson(json, clazz);
    }
}
