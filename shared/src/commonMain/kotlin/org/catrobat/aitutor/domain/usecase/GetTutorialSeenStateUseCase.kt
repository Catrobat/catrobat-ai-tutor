package org.catrobat.aitutor.domain.usecase

import org.catrobat.aitutor.domain.repository.SettingsRepository

class GetTutorialSeenStateUseCase(private val settingsRepository: SettingsRepository) {
    operator fun invoke() = settingsRepository.hasSeenTutorial
}
