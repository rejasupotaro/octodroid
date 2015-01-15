package com.rejasupotaro.octodroid.http;

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
