package com.rejasupotaro.octodroid;

import com.google.gson.Gson;

public class GsonProvider {
    private static Gson gson = new Gson();

    public static void set(Gson gson) {
        GsonProvider.gson = gson;
    }

    public static Gson get() {
        return gson;
    }
}
