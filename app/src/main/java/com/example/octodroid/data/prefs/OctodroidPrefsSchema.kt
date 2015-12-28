package com.example.octodroid.data.prefs

import android.content.Context

import com.rejasupotaro.android.kvs.annotations.Key
import com.rejasupotaro.android.kvs.annotations.Table

import java.util.HashSet

@Table("octodroid")
abstract class OctodroidPrefsSchema {
    @Key("selected_serialized_serialized_repositories")
    internal var seletedSerializedRepositories: Set<String> = HashSet<String>()

    companion object {

        private var prefs: OctodroidPrefs? = null

        @Synchronized operator fun get(context: Context): OctodroidPrefs {
            if (prefs == null) {
                prefs = OctodroidPrefs(context)
            }
            return prefs
        }
    }
}
