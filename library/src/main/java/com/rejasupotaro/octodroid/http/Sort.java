package com.rejasupotaro.octodroid.http;

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
