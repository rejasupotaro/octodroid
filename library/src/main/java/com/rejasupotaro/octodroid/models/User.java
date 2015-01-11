package com.rejasupotaro.octodroid.models;

import com.google.gson.annotations.SerializedName;

public class User extends Resource {
    @SerializedName("login")
    private String login;
    @SerializedName("id")
    private int id;
    @SerializedName("avatar_url")
    private String avatarUrl;
    @SerializedName("gravatar_id")
    private String gravatarId;
    @SerializedName("url")
    private String url;
    @SerializedName("html_url")
    private String htmlUrl;
    @SerializedName("followers_url")
    private String followersUrl;
    @SerializedName("following_url")
    private String followingUrl;
    @SerializedName("gists_url")
    private String gistsUrl;
    @SerializedName("starred_url")
    private String starredUrl;
    @SerializedName("subscriptions_url")
    private String subscriptionsUrl;
    @SerializedName("organizations_url")
    private String organizationsUrl;
    @SerializedName("repos_url")
    private String reposUrl;
    @SerializedName("events_url")
    private String eventsUrl;
    @SerializedName("received_events_url")
    private String receivedEventsUrl;
    @SerializedName("type")
    private String type;
    @SerializedName("site_admin")
    private String siteAdmin;
    @SerializedName("name")
    private String name;
    @SerializedName("company")
    private String company;
    @SerializedName("blog")
    private String blog;
    @SerializedName("location")
    private String location;
    @SerializedName("email")
    private String email;
    @SerializedName("hireable")
    private String hireable;
    @SerializedName("bio")
    private String bio;
    @SerializedName("public_repos")
    private int publicRepos;
    @SerializedName("public_gists")
    private int publicGists;
    @SerializedName("followers")
    private int followers;
    @SerializedName("following")
    private int following;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("total_private_repos")
    private int totalPrivateRepos;
    @SerializedName("owned_private_repos")
    private int ownedPrivateRepos;
    @SerializedName("private_gists")
    private int privateGists;
    @SerializedName("disk_usage")
    private int diskUsage;
    @SerializedName("collaborators")
    private int collaborators;
    @SerializedName("plan")
    private Plan plan;

    public String getLogin() {
        return login;
    }
}
