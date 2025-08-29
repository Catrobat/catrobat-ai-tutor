package org.catrobat.aitutor.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.catrobat.aitutor.domain.repository.SettingsRepository

class DataStoreSettingsRepository(private val dataStore: DataStore<Preferences>) :
    SettingsRepository {
    private object Keys {
        val HAS_SEEN_TUTORIAL = booleanPreferencesKey("has_seen_tutorial")
    }

    override val hasSeenTutorial: Flow<Boolean>
        get() =
            dataStore.data.map { preferences ->
                preferences[Keys.HAS_SEEN_TUTORIAL] ?: false
            }

    override suspend fun setTutorialSeen(seen: Boolean) {
        dataStore.edit { preferences ->
            preferences[Keys.HAS_SEEN_TUTORIAL] = seen
        }
    }
}
