package org.catrobat.aitutor.domain.usecase

import org.catrobat.aitutor.domain.repository.SettingsRepository

class SetTutorialSeenUseCase(private val settingsRepository: SettingsRepository) {
    suspend operator fun invoke(seen: Boolean) = settingsRepository.setTutorialSeen(seen)
}
