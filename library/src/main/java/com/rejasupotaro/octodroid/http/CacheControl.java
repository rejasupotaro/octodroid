package com.rejasupotaro.octodroid.http;

import java.io.File;

public class CacheControl {
    private File file;
    private int maxCacheSize;
    private int maxStale;

    private CacheControl(Builder builder) {
        this.file = builder.file;
        this.maxCacheSize = builder.maxCacheSize;
        this.maxStale = builder.maxStale;
    }

    public static class Builder {
        private static final String DEFAULT_CACHE_FILE_NAME = "response_cache";
        private static final int DEFAULT_MAX_CACHE_SIZE = 3 * 1024 * 1024; // 3MB

        private File file;
        private int maxCacheSize = DEFAULT_MAX_CACHE_SIZE;
        private int maxStale;

        public Builder file() {
            return this;
        }

        public Builder maxCacheSize() {
            return this;
        }

        public Builder maxStale() {
            return this;
        }

        public CacheControl build() {
            return new CacheControl(this);
        }
    }
}
