package com.rejasupotaro.octodroid.http.params;

public enum Order {
    ASC,
    DESC;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
