package com.example.octodroid;

import android.content.Context;
import android.content.SharedPreferences;

import com.github.gfx.util.encrypt.EncryptedSharedPreferences;
import com.rejasupotaro.android.kvs.PrefSchema;
import com.rejaupotaro.android.kvs.annotations.Key;
import com.rejaupotaro.android.kvs.annotations.Table;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

@Table("octodroid_session")
public class SessionPrefsSchema extends PrefSchema {
    private static final String DEFAULT_ALGORITHM_MODE = "AES/CBC/PKCS5Padding";
    private static final String DEFAULT_PROVIDER = "BC";

    private static Cipher getCipher() {
        try {
            return Cipher.getInstance(DEFAULT_ALGORITHM_MODE, DEFAULT_PROVIDER);
        } catch (NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException e) {
            throw new AssertionError(e);
        }
    }

    private static SessionPrefs prefs;

    @Key("email")
    protected String email;
    @Key("password")
    protected String password;

    public static synchronized SessionPrefs get(Context context) {
        if (prefs == null) {
            SharedPreferences base = context.getSharedPreferences("octodroid_session", Context.MODE_PRIVATE);
            prefs = new SessionPrefs(new EncryptedSharedPreferences(getCipher(), base, context));
        }
        return prefs;
    }
}

