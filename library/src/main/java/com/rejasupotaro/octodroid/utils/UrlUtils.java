package com.rejasupotaro.octodroid.utils;

import com.rejasupotaro.octodroid.http.Http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UrlUtils {
    public static String encode(String str) {
        try {
            return URLEncoder.encode(str, Http.UTF_8);
        } catch (UnsupportedEncodingException e) {
            // Should not occur
            return "";
        }
    }
}
