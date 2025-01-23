package com.example.nakniki_netflix.db;

import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import com.example.nakniki_netflix.MainActivity;

public class TokenStorage {
    private static final String PREF_NAME = "auth_prefs";
    private static final String KEY_TOKEN = "jwt_token";
    private SharedPreferences sharedPreferences;

    public TokenStorage() {
        try {
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            sharedPreferences = EncryptedSharedPreferences.create(
                    PREF_NAME,
                    masterKeyAlias,
                    MainActivity.getAppContext(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (Exception e) {
            e.printStackTrace(); // TODO: remove print
        }
    }

    public void saveToken(String token) {
        sharedPreferences.edit().putString(KEY_TOKEN, token).apply();
    }

    public String getToken() {
        return "Bearer " + sharedPreferences.getString(KEY_TOKEN, null);
    }

    public String getTokenRaw() {
        return sharedPreferences.getString(KEY_TOKEN, null);
    }

    public void clearToken() {
        sharedPreferences.edit().remove(KEY_TOKEN).apply();
    }
}
