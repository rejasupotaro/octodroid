package com.rejasupotaro.octodroid.http;

import android.content.Context;

import java.io.File;

public class CacheControl {
    private File file;
    private int maxCacheSize;
    private int maxStale;

    public File getFile() {
        return file;
    }

    public int getMaxCacheSize() {
        return maxCacheSize;
    }

    public int getMaxStale() {
        return maxStale;
    }

    private CacheControl(Builder builder) {
        this.file = builder.file;
        this.maxCacheSize = builder.maxCacheSize;
        this.maxStale = builder.maxStale;
    }

    public static class Builder {
        private static final String DEFAULT_CACHE_FILE_NAME = "octodroid_response_cache";
        private static final int DEFAULT_MAX_CACHE_SIZE = 3 * 1024 * 1024; // 3MB
        private static final int DEFAULT_MAX_STALE = 28 * 24 * 60 * 60; // tolerate 4-weeks stale

        private File file;
        private int maxCacheSize = DEFAULT_MAX_CACHE_SIZE;
        private int maxStale = DEFAULT_MAX_STALE;

        public Builder file(Context context) {
            return file(context, DEFAULT_CACHE_FILE_NAME);
        }

        public Builder file(Context context, String fileName) {
            return file(new File(context.getCacheDir(), fileName));
        }

        public Builder file(File file) {
            this.file = file;
            return this;
        }

        public Builder maxCacheSize(int maxCacheSize) {
            this.maxCacheSize = maxCacheSize;
            return this;
        }

        public Builder maxStale(int maxStale) {
            this.maxStale = maxStale;
            return this;
        }

        public CacheControl build() {
            if (file == null) {
                throw new IllegalStateException("Cache file should not be null");
            }
            return new CacheControl(this);
        }
    }
}
