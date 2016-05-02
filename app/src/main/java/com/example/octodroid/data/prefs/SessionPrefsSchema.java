package com.example.octodroid.data.prefs;

import com.rejasupotaro.android.kvs.annotations.Key;
import com.rejasupotaro.android.kvs.annotations.Table;

@Table(name = "octodroid_session", builder = SessionPrefsBuilder.class)
public class SessionPrefsSchema {
    @Key(name = "username")
    String username;
    @Key(name = "password")
    String password;
}

