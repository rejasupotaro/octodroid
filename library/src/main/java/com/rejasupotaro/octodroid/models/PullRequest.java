package com.rejasupotaro.octodroid.models;

import com.google.gson.annotations.SerializedName;

public class PullRequest extends Resource {
    @SerializedName("id")
    private long id;
    @SerializedName("number")
    private int number;
    @SerializedName("state")
    private String state;
    @SerializedName("title")
    private String title;
    @SerializedName("body")
    private String body;
    @SerializedName("user")
    private User user;
    @SerializedName("merged")
    private boolean merged;
    @SerializedName("mergeable")
    private boolean mergeable;
    @SerializedName("comments")
    private int comments;
    @SerializedName("commits")
    private int commits;
    @SerializedName("additions")
    private int additions;
    @SerializedName("deletions")
    private int deletions;
    @SerializedName("changed_files")
    private int changedFiles;

    public long getId() {
        return id;
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

    public User getUser() {
        return user;
    }

    public boolean isMerged() {
        return merged;
    }

    public boolean isMergeable() {
        return mergeable;
    }

    public int getComments() {
        return comments;
    }

    public int getCommits() {
        return commits;
    }

    public int getAdditions() {
        return additions;
    }

    public int getDeletions() {
        return deletions;
    }

    public int getChangedFiles() {
        return changedFiles;
    }
}
