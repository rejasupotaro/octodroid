package com.rejasupotaro.octodroid;

import com.google.gson.Gson;

public class GsonProvider {
    private static final Gson GSON = new Gson();

    public static Gson get() {
        return GSON;
    }
}
