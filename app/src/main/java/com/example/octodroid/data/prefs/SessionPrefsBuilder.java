package com.example.octodroid.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.github.gfx.util.encrypt.EncryptedSharedPreferences;
import com.rejasupotaro.android.kvs.PrefsBuilder;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

public class SessionPrefsBuilder implements PrefsBuilder<SessionPrefs> {
    private static String DEFAULT_ALGORITHM_MODE = "AES/CBC/PKCS5Padding";
    private static String DEFAULT_PROVIDER = "BC";

    @Override
    public SessionPrefs build(Context context) {
        try {
            SharedPreferences base = context.getSharedPreferences("octodroid_session", Context.MODE_PRIVATE);
            Cipher cipher = Cipher.getInstance(DEFAULT_ALGORITHM_MODE, DEFAULT_PROVIDER);
            return new SessionPrefs(new EncryptedSharedPreferences(cipher, base, context));
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException(e);
        }
    }
}
