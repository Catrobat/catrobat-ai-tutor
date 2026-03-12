package org.catrobat.aitutor.data

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class AndroidApiKeyStore(context: Context) : ApiKeyStore {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        "secure_api_keys",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override fun saveApiKey(key: String) {
        sharedPreferences.edit().putString("gemini_api_key", key).apply()
    }

    override fun getApiKey(): String? {
        return sharedPreferences.getString("gemini_api_key", null)
    }

    override fun clearApiKey() {
        sharedPreferences.edit().remove("gemini_api_key").apply()
    }
}
