package com.rejasupotaro.octodroid.http.params;

public enum All {
    TRUE,
    FALSE;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
