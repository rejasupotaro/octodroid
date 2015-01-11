package com.rejasupotaro.octodroid.models;

import com.google.gson.annotations.SerializedName;

public class Repository extends Resource {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("full_name")
    private String fullName;
    @SerializedName("private")
    private boolean published;
    @SerializedName("html_url")
    private String htmlUrl;
    @SerializedName("description")
    private String description;
    @SerializedName("fork")
    private boolean fork;
    @SerializedName("url")
    private String url;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("pushed_at")
    private String pushedAt;
    @SerializedName("homepage")
    private String homepage;
    @SerializedName("size")
    private int size;
    @SerializedName("stargazers_count")
    private int stargazersCount;
    @SerializedName("watchers_count")
    private int watchersCount;
    @SerializedName("language")
    private String language;
    @SerializedName("forks_count")
    private int forksCount;
    @SerializedName("open_issues_count")
    private int openIssuesCount;
    @SerializedName("master_branch")
    private String masterBranch;
    @SerializedName("default_branch")
    private String defaultBranch;
    @SerializedName("score")
    private float score;
    @SerializedName("owner")
    private User owner;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public boolean isPublished() {
        return published;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public String getDescription() {
        return description;
    }

    public boolean isFork() {
        return fork;
    }

    public String getUrl() {
        return url;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getPushedAt() {
        return pushedAt;
    }

    public String getHomepage() {
        return homepage;
    }

    public int getSize() {
        return size;
    }

    public int getStargazersCount() {
        return stargazersCount;
    }

    public int getWatchersCount() {
        return watchersCount;
    }

    public String getLanguage() {
        return language;
    }

    public int getForksCount() {
        return forksCount;
    }

    public int getOpenIssuesCount() {
        return openIssuesCount;
    }

    public String getMasterBranch() {
        return masterBranch;
    }

    public String getDefaultBranch() {
        return defaultBranch;
    }

    public float getScore() {
        return score;
    }

    public User getOwner() {
        return owner;
    }
}
