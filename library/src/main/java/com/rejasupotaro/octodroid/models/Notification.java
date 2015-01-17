package com.rejasupotaro.octodroid.models;

import com.google.gson.annotations.SerializedName;

public class Notification extends Resource {
    @SerializedName("id")
    private String id;
    @SerializedName("repository")
    private Repository repository;
    @SerializedName("subject")
    private Subject subject;
    @SerializedName("reason")
    private String reason;
    @SerializedName("unread")
    private boolean unread;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("last_read_at")
    private String lastReadAt;
    @SerializedName("url")
    private String url;
}
