package com.example.octodroid.data.prefs;

import com.rejasupotaro.android.kvs.annotations.Key;
import com.rejasupotaro.android.kvs.annotations.Table;

import java.util.Set;

@Table(name = "octodroid")
public class OctodroidPrefsSchema {
    @Key(name = "selected_serialized_serialized_repositories")
    Set<String> selectedSerializedRepositories;
}

