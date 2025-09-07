package org.catrobat.aitutor.domain.usecase

import dev.mokkery.MockMode
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlinx.coroutines.test.runTest
import org.catrobat.aitutor.domain.repository.SettingsRepository
import kotlin.test.Test

class SetTutorialSeenUseCaseTest {
    private val settingsRepository = mock<SettingsRepository>(MockMode.autoUnit)
    private val useCase = SetTutorialSeenUseCase(settingsRepository)

    @Test
    fun `invoke with true should call repository setTutorialSeen with true`() =
        runTest {
            useCase(true)

            verifySuspend { settingsRepository.setTutorialSeen(true) }
        }

    @Test
    fun `invoke with false should call repository setTutorialSeen with false`() =
        runTest {
            useCase(false)

            verifySuspend { settingsRepository.setTutorialSeen(false) }
        }
}
