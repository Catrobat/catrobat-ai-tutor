package org.catrobat.aitutor.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val hasSeenTutorial: Flow<Boolean>

    suspend fun setTutorialSeen(seen: Boolean)
}
