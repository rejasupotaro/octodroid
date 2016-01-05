package com.example.octodroid.data.prefs;

import android.content.Context;

import com.rejasupotaro.android.kvs.annotations.Key;
import com.rejasupotaro.android.kvs.annotations.Table;

import java.util.Set;

@Table("octodroid")
public abstract class OctodroidPrefsSchema {
    @Key("selected_serialized_serialized_repositories")
    Set<String> selectedSerializedRepositories = null;

    private static OctodroidPrefs prefs;

    public static synchronized OctodroidPrefs get(Context context) {
        if (prefs != null) {
            return prefs;
        }

        prefs = new OctodroidPrefs(context);
        return prefs;
    }
}

