package com.rejasupotaro.octodroid.http;

import android.text.TextUtils;

import com.squareup.okhttp.Headers;

public class PaginationHeaderParser {
    public static final String HEADER_KEY_LINK = "Link";

    public static Pagination parse(Response<?> response) {
        if (response == null) {
            return new Pagination();
        }

        return parse(response.headers());
    }

    public static Pagination parse(Headers headers) {
        if (headers == null) {
            return new Pagination();
        }

        String linksString = headers.get(HEADER_KEY_LINK);
        if (TextUtils.isEmpty(linksString)) {
            return new Pagination(null);
        }

        String[] linkStrings = linksString.split(",");
        if (linkStrings.length == 0) {
            return new Pagination(null);
        }

        Link[] links = new Link[linkStrings.length];
        for (int i = 0; i < links.length; i++) {
            Link link = Link.fromSource(linkStrings[i]);
            links[i] = link;
        }

        return new Pagination(links);
    }
}

