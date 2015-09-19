package com.rejasupotaro.octodroid.http;

import android.support.test.runner.AndroidJUnit4;

import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Headers;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(AndroidJUnit4.class)
public class ResponseTest {

    @Test
    public void parseSuccessResponse() throws IOException, JSONException {
        Response<JSONObject> response = Response.parse(
                Headers.of("Name", "Value"),
                200,
                "{\"message\":\"This is a message\"}",
                new TypeToken<JSONObject>() {
                });
        assertThat(response.isSuccessful()).isTrue();
        assertThat(response.headers().get("Name")).isEqualTo("Value");
        assertThat(response.code()).isEqualTo(200);
        assertThat(response.body()).isEqualTo("{\"message\":\"This is a message\"}");
        assertThat(response.bodyAsJson().get("message")).isEqualTo("This is a message");
    }

    @Test
    public void parseErrorResponse() throws IOException, JSONException {
        Response<JSONObject> response = Response.parse(
                Headers.of("Name", "Value"),
                422,
                "{\"errors\": {\"token\": [{\"error\": \"expired\"}]}}",
                new TypeToken<JSONObject>() {
                });
        assertThat(response.isSuccessful()).isFalse();
        assertThat(response.headers().get("Name")).isEqualTo("Value");
        assertThat(response.code()).isEqualTo(422);
        assertThat(response.body()).isEqualTo("{\"errors\": {\"token\": [{\"error\": \"expired\"}]}}");
    }
}

