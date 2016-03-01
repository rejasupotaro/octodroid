package com.example.octodroid.data

import android.content.Context
import com.example.octodroid.data.prefs.SessionPrefs
import com.example.octodroid.data.prefs.SessionPrefsSchema

object SessionManager {
    fun isLoggedIn(context: Context): Boolean {
        val prefs = SessionPrefs.get(context)
        return prefs.hasUsername() && prefs.hasPassword()
    }

    fun login(context: Context) {
        val prefs = SessionPrefs.get(context)
        GitHub.client().authorization(prefs.username, prefs.password)
    }

    fun logout(context: Context) {
        SessionPrefs.get(context).clear()
    }
}
