package com.rejasupotaro.octodroid.models;

import com.google.gson.annotations.SerializedName;

public class Plan {
    @SerializedName("name")
    private String name;
    @SerializedName("space")
    private int space;
    @SerializedName("private_repos")
    private int privateRepos;
    @SerializedName("collaborators")
    private int collaborators;
}
