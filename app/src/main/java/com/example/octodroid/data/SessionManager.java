package com.example.octodroid.data;

import android.content.Context;

import com.example.octodroid.data.prefs.SessionPrefs;
import com.example.octodroid.data.prefs.SessionPrefsSchema;

public class SessionManager {
    public static boolean isLoggedIn(Context context) {
        SessionPrefs prefs = SessionPrefsSchema.get(context);
        return prefs.hasUsername() && prefs.hasPassword();
    }

    public static void login(Context context) {
        SessionPrefs prefs = SessionPrefsSchema.get(context);
        GitHub.client().authorization(prefs.getUsername(), prefs.getPassword());
    }

    public static void logout(Context context) {
        SessionPrefs.get(context).clear();
    }
}
