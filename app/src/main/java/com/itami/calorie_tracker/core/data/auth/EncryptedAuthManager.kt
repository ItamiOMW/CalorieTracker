package com.itami.calorie_tracker.core.data.auth

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class EncryptedAuthManager @Inject constructor(
    @ApplicationContext context: Context,
) : AuthManager {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        TOKEN_STORAGE_FILE_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
    )

    override val token: String?
        get() = sharedPreferences.getString(ACCESS_TOKEN_KEY, null)

    override fun setToken(token: String?) {
        sharedPreferences.edit()
            .putString(ACCESS_TOKEN_KEY, token)
            .apply()
    }

    companion object {

        private const val TOKEN_STORAGE_FILE_NAME = "token_storage"

        private const val ACCESS_TOKEN_KEY = "access_token_key"
    }
}