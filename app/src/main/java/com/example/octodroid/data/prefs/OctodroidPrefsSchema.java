package com.example.octodroid.data.prefs;

import android.content.Context;

import com.rejasupotaro.android.kvs.annotations.Key;
import com.rejasupotaro.android.kvs.annotations.Table;

import java.util.HashSet;
import java.util.Set;

@Table("octodroid")
public abstract class OctodroidPrefsSchema {
    @Key("selected_serialized_serialized_repositories")
    Set<String> seletedSerializedRepositories = new HashSet<>();

    private static OctodroidPrefs prefs;

    public static synchronized OctodroidPrefs get(Context context) {
        if (prefs == null) {
            prefs = new OctodroidPrefs(context);
        }
        return prefs;
    }
}
