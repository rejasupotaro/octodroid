package com.rejasupotaro.octodroid.models;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.Date;

public class Event extends Resource {
    @SerializedName("id")
    private long id;
    @SerializedName("type")
    private String type;
    @SerializedName("public")
    private boolean isPublic;
    @SerializedName("payload")
    private JSONObject payload;
    @SerializedName("repo")
    private Repository repository;
    @SerializedName("actor")
    private User user;
    @SerializedName("org")
    private JSONObject organization;
    @SerializedName("created_at")
    private Date createdAt;

    public long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public JSONObject getPayload() {
        return payload;
    }

    public Repository getRepository() {
        return repository;
    }

    public User getUser() {
        return user;
    }

    public JSONObject getOrganization() {
        return organization;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
