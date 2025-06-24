package org.catrobat.aitutor.domain.repository

import org.catrobat.aitutor.domain.model.AiAppInfo

interface AiAppRepository {
    suspend fun getInstalledAiApps(): List<AiAppInfo>
}
