package com.example.octodroid.data.prefs;

import android.content.Context;

import com.rejasupotaro.android.kvs.PrefSchema;
import com.rejasupotaro.android.kvs.annotations.Key;
import com.rejasupotaro.android.kvs.annotations.Table;

import java.util.HashSet;
import java.util.Set;

@Table("octodroid")
public class OctodroidPrefsSchema extends PrefSchema {
    @Key("selected_repository_ids")
    Set<String> selectedRepositoryIds = new HashSet<>();

    private static OctodroidPrefs prefs;

    public static synchronized OctodroidPrefs get(Context context) {
        if (prefs == null) {
            prefs = new OctodroidPrefs(context);
        }
        return prefs;
    }
}
