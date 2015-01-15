package com.rejasupotaro.octodroid.http.params;

public enum Type {
    ALL,
    OWNER,
    PUBLIC,
    PRIVATE,
    MEMBER;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
