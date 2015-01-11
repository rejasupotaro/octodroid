package com.rejasupotaro.octodroid.models;

import com.rejasupotaro.octodroid.GsonProvider;

public class Resource {
    public String toJson() {
        return GsonProvider.get().toJson(this);
    }
}
