package com.rejasupotaro.octodroid.http;

import android.net.Uri;
import android.text.TextUtils;

public class Link {
    public static enum Rel {
        FIRST,
        PREV,
        NEXT,
        LAST
    }

    private int page;
    private int perPage;
    private Rel rel;

    public int getPage() {
        return page;
    }

    public int getPerPage() {
        return perPage;
    }

    public Rel getRel() {
        return rel;
    }

    public Link(int page, int perPage, Rel rel) {
        this.page = page;
        this.perPage = perPage;
        this.rel = rel;
    }

    public static Link fromSource(String source) {
        if (TextUtils.isEmpty(source)) return null;

        Uri resourceUri = getResourceUri(source);
        int page = getPage(resourceUri);
        int perPage = getPerPage(resourceUri);
        Rel rel = getRel(source);

        return new Link(page, perPage, rel);
    }

    private static Uri getResourceUri(String source) {
        int startIndex = source.indexOf("<");
        int endIndex = source.indexOf(">");
        if (startIndex < 0 || endIndex < 0) {
            return null;
        } else {
            String resourceString = source.substring(startIndex, endIndex + 1);
            return Uri.parse(resourceString.substring(1, resourceString.length() - 1));
        }
    }

    private static int getPage(Uri resourceUri) {
        return getQueryParameter(resourceUri, "page");
    }

    private static int getPerPage(Uri resourceUri) {
        return getQueryParameter(resourceUri, "per_page");
    }

    private static int getQueryParameter(Uri resourceUri, String key) {
        if (resourceUri == null) return -1;

        String param = resourceUri.getQueryParameter(key);
        if (TextUtils.isEmpty(param)) {
            return -1;
        } else {
            return Integer.valueOf(param);
        }
    }

    private static Rel getRel(String source) {
        if (source != null) {
            for (Rel rel : Rel.values()) {
                String pattern = "rel=\"" + rel.name().toLowerCase() + "\"";
                if (source.indexOf(pattern) != -1) {
                    return rel;
                }
            }
        }

        return null;
    }
}

