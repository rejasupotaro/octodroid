package com.rejasupotaro.octodroid.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResult<T> extends Resource {
    @SerializedName("total_count")
    private int totalCount;
    @SerializedName("incomplete_results")
    private boolean incompleteResults;
    @SerializedName("items")
    private List<T> items;

    public int getTotalCount() {
        return totalCount;
    }

    public boolean isIncompleteResults() {
        return incompleteResults;
    }

    public List<T> getItems() {
        return items;
    }
}
