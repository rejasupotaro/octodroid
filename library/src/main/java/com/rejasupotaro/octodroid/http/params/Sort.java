package com.rejasupotaro.octodroid.http.params;

public enum Sort {
    STARS,
    CREATED,
    UPDATED,
    PUSHED;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
