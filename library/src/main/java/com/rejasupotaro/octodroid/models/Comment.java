package com.rejasupotaro.octodroid.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Comment extends Resource {
    @SerializedName("url")
    private String url;
    @SerializedName("html_url")
    private String htmlUrl;
    @SerializedName("issue_url")
    private String issueUrl;
    @SerializedName("id")
    private int id;
    @SerializedName("user")
    private User user;
    @SerializedName("position")
    private int position;
    @SerializedName("line")
    private int line;
    @SerializedName("path")
    private String path;
    @SerializedName("commit_id")
    private String commitId;
    @SerializedName("created_at")
    private Date createdAt;
    @SerializedName("updated_at")
    private Date updatedAt;
    @SerializedName("body")
    private String body;

    public String getUrl() {
        return url;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public String getIssueUrl() {
        return issueUrl;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public int getPosition() {
        return position;
    }

    public int getLine() {
        return line;
    }

    public String getPath() {
        return path;
    }

    public String getCommitId() {
        return commitId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public String getBody() {
        return body;
    }
}
