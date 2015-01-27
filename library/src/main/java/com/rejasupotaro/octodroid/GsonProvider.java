package com.rejasupotaro.octodroid;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonProvider {
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void set(Gson gson) {
        GsonProvider.gson = gson;
    }

    public static Gson get() {
        return gson;
    }
}
