package com.rejasupotaro.octodroid.http;

import android.text.TextUtils;

import com.rejasupotaro.octodroid.utils.UrlUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Params {

    private Map<String, String> queryMap;

    public Params() {
        queryMap = new HashMap<>();
        queryMap.put("page", "1");
        queryMap.put("per_page", "20");
    }

    public Params add(String key, String value) {
        queryMap.put(key, value);
        return this;
    }

    public void incrementPage() {
        int page = Integer.parseInt(queryMap.get("page"));
        queryMap.put("page", String.valueOf(++page));
    }

    @Override
    public String toString() {
        List<String> queryList = new ArrayList<>();
        for (String key : queryMap.keySet()) {
            String value = queryMap.get(key);
            String query = String.format("%s=%s", key, UrlUtils.encode(value));
            queryList.add(query);
        }

        return TextUtils.join("&", queryList);
    }
}
