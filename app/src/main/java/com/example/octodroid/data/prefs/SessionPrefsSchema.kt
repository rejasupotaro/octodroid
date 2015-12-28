package com.example.octodroid.data.prefs

import android.content.Context
import android.content.SharedPreferences

import com.github.gfx.util.encrypt.EncryptedSharedPreferences
import com.rejasupotaro.android.kvs.annotations.Key
import com.rejasupotaro.android.kvs.annotations.Table

import java.security.NoSuchAlgorithmException
import java.security.NoSuchProviderException

import javax.crypto.Cipher
import javax.crypto.NoSuchPaddingException

@Table("octodroid_session")
abstract class SessionPrefsSchema {

    @Key("username")
    internal var username: String
    @Key("password")
    internal var password: String

    companion object {
        private val DEFAULT_ALGORITHM_MODE = "AES/CBC/PKCS5Padding"
        private val DEFAULT_PROVIDER = "BC"

        private val cipher: Cipher
            get() {
                try {
                    return Cipher.getInstance(DEFAULT_ALGORITHM_MODE, DEFAULT_PROVIDER)
                } catch (e: NoSuchAlgorithmException) {
                    throw AssertionError(e)
                } catch (e: NoSuchProviderException) {
                    throw AssertionError(e)
                } catch (e: NoSuchPaddingException) {
                    throw AssertionError(e)
                }

            }

        private var prefs: SessionPrefs? = null

        @Synchronized operator fun get(context: Context): SessionPrefs {
            if (prefs == null) {
                val base = context.getSharedPreferences("octodroid_session", Context.MODE_PRIVATE)
                prefs = SessionPrefs(EncryptedSharedPreferences(cipher, base, context))
            }
            return prefs
        }
    }
}

