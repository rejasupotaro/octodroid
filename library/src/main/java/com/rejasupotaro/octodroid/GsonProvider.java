package com.rejasupotaro.octodroid;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import com.rejasupotaro.octodroid.models.Event;

import java.lang.reflect.Type;

public class GsonProvider {
    private static Gson gson = new GsonBuilder()
            .registerTypeAdapter(EventTypeConverter.TYPE, new EventTypeConverter())
            .setPrettyPrinting()
            .create();

    public static void set(Gson gson) {
        GsonProvider.gson = gson;
    }

    public static Gson get() {
        return gson;
    }

    private static class EventTypeConverter
            implements JsonSerializer<Event.Type>, JsonDeserializer<Event.Type> {

        public static final Type TYPE = new TypeToken<Event.Type>() {
        }.getType();

        @Override
        public JsonElement serialize(Event.Type src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.toString());
        }

        @Override
        public Event.Type deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            return Event.Type.of(json.getAsString());
        }
    }
}
