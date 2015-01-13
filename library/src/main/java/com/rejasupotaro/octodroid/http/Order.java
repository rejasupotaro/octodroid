package com.rejasupotaro.octodroid.http;

public enum Order {
    ASC,
    DESC;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
