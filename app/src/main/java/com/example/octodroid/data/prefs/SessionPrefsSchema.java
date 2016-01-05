package com.example.octodroid.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.github.gfx.util.encrypt.EncryptedSharedPreferences;
import com.rejasupotaro.android.kvs.annotations.Key;
import com.rejasupotaro.android.kvs.annotations.Table;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

@Table("octodroid_session")
public abstract class SessionPrefsSchema {
    @Key("username")
    String username;
    @Key("password")
    String password;

    private static String DEFAULT_ALGORITHM_MODE = "AES/CBC/PKCS5Padding";
    private static String DEFAULT_PROVIDER = "BC";

    private static SessionPrefs prefs;

    public static synchronized SessionPrefs get(Context context) {
        if (prefs != null) {
            return prefs;
        }

        try {
            SharedPreferences base = context.getSharedPreferences("octodroid_session", Context.MODE_PRIVATE);
            Cipher cipher = Cipher.getInstance(DEFAULT_ALGORITHM_MODE, DEFAULT_PROVIDER);
            prefs = new SessionPrefs(new EncryptedSharedPreferences(cipher, base, context));
            return prefs;
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException(e);
        }
    }
}

