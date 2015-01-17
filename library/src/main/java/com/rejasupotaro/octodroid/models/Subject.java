package com.rejasupotaro.octodroid.models;

import com.google.gson.annotations.SerializedName;

public class Subject extends Resource {
    @SerializedName("title")
    private String title;
    @SerializedName("url")
    private String url;
    @SerializedName("latest_comment_url")
    private String latestCommentUrl;
    @SerializedName("type")
    private String type;

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getLatestCommentUrl() {
        return latestCommentUrl;
    }

    public String getType() {
        return type;
    }
}
