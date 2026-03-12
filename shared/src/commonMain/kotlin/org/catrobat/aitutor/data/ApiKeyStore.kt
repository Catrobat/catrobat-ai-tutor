package org.catrobat.aitutor.data

interface ApiKeyStore {
    fun saveApiKey(key: String)
    fun getApiKey(): String?
    fun clearApiKey()
}
