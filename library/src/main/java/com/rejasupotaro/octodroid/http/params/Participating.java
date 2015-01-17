package com.rejasupotaro.octodroid.http.params;

public enum Participating {
    TRUE,
    FALSE;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
