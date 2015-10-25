package com.rejasupotaro.octodroid.models;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;

public class Issue extends Resource {
    @SerializedName("id")
    private long id;
    @SerializedName("url")
    private String url;
    @SerializedName("labels_url")
    private String labelsUrl;
    @SerializedName("comments_url")
    private String commentsUrl;
    @SerializedName("events_url")
    private String eventsUrl;
    @SerializedName("html_url")
    private String htmlUrl;
    @SerializedName("number")
    private int number;
    @SerializedName("state")
    private String state;
    @SerializedName("title")
    private String title;
    @SerializedName("body")
    private String body;
    @SerializedName("locked")
    private boolean locked;
    @SerializedName("comments")
    private int comments;

    public long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getLabelsUrl() {
        return labelsUrl;
    }

    public String getCommentsUrl() {
        return commentsUrl;
    }

    public String getEventsUrl() {
        return eventsUrl;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public int getNumber() {
        return number;
    }

    public String getState() {
        return state;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public boolean isLocked() {
        return locked;
    }

    public int getComments() {
        return comments;
    }
}
