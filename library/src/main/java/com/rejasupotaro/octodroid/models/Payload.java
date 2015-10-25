package com.rejasupotaro.octodroid.models;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONObject;

public class Payload extends Resource {
    @SerializedName("action")
    private String action;
    @SerializedName("issue")
    private Issue issue;
    @SerializedName("comment")
    private Comment comment;
    @SerializedName("repository")
    private Repository repository;
    @SerializedName("sender")
    private User sender;
    @SerializedName("ref")
    private String ref;
    @SerializedName("ref_type")
    private String refType;
    @SerializedName("master_branch")
    private String masterBranch;
    @SerializedName("description")
    private String description;
    @SerializedName("pusher_type")
    private String pusherType;
    @SerializedName("deployment")
    private JSONObject deployment;
    @SerializedName("deployment_status")
    private JSONObject deploymentStatus;
    @SerializedName("forkee")
    private User forkee;
    @SerializedName("pages")
    private JSONArray pages;
    @SerializedName("member")
    private User member;
    @SerializedName("scope")
    private String scope;
    @SerializedName("team")
    private JSONObject team;
    @SerializedName("organization")
    private JSONObject organization;
    @SerializedName("build")
    private JSONObject build;
    @SerializedName("number")
    private int number;
    @SerializedName("pull_request")
    private PullRequest pullRequest;
    @SerializedName("before")
    private String before;
    @SerializedName("after")
    private String after;
    @SerializedName("pusher")
    private JSONObject pusher;

    public String getAction() {
        return action;
    }

    public Issue getIssue() {
        return issue;
    }

    public Comment getComment() {
        return comment;
    }

    public Repository getRepository() {
        return repository;
    }

    public User getSender() {
        return sender;
    }

    public String getRef() {
        return ref;
    }

    public String getRefType() {
        return refType;
    }

    public String getMasterBranch() {
        return masterBranch;
    }

    public String getDescription() {
        return description;
    }

    public String getPusherType() {
        return pusherType;
    }

    public JSONObject getDeployment() {
        return deployment;
    }

    public JSONObject getDeploymentStatus() {
        return deploymentStatus;
    }

    public User getForkee() {
        return forkee;
    }

    public JSONArray getPages() {
        return pages;
    }

    public User getMember() {
        return member;
    }

    public String getScope() {
        return scope;
    }

    public JSONObject getTeam() {
        return team;
    }

    public JSONObject getOrganization() {
        return organization;
    }

    public JSONObject getBuild() {
        return build;
    }

    public int getNumber() {
        return number;
    }

    public PullRequest getPullRequest() {
        return pullRequest;
    }

    public String getBefore() {
        return before;
    }

    public String getAfter() {
        return after;
    }

    public JSONObject getPusher() {
        return pusher;
    }
}
