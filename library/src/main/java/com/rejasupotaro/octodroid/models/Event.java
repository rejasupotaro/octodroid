package com.rejasupotaro.octodroid.models;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;
import org.json.JSONObject;

public class Event extends Resource {
    @SerializedName("id")
    private int id;
    @SerializedName("type")
    private String type;
    @SerializedName("public")
    private boolean isPublic;
    @SerializedName("payload")
    private String payload;
    @SerializedName("repo")
    private Repository repository;
    @SerializedName("actor")
    private User user;
    @SerializedName("org")
    private JSONObject organization;
    @SerializedName("created_at")
    private DateTime createdAt;

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public String getPayload() {
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

    public DateTime getCreatedAt() {
        return createdAt;
    }
}
