package com.rejasupotaro.octodroid.http;

public enum Sort {
    STARS;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
