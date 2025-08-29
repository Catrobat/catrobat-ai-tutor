package org.catrobat.aitutor.domain.usecase

import org.catrobat.aitutor.domain.repository.AiAppRepository

class GetInstalledAiAppsUseCase(private val repository: AiAppRepository) {
    suspend operator fun invoke() = repository.getInstalledAiApps()
}
