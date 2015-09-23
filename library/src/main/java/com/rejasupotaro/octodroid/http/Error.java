package com.rejasupotaro.octodroid.http;

import com.google.gson.annotations.SerializedName;
import com.rejasupotaro.octodroid.models.Resource;

import org.json.JSONObject;

import java.util.List;

public class Error extends Resource {
    @SerializedName("message")
    private String message;
    @SerializedName("errors")
    private List<JSONObject> details;

    public String getMessage() {
        return message;
    }

    public List<JSONObject> getDetails() {
        return details;
    }
}
