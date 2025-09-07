package org.catrobat.aitutor.domain.usecase

import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.catrobat.aitutor.domain.repository.SettingsRepository
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class GetTutorialSeenStateUseCaseTest {

    private val settingsRepository = mock<SettingsRepository>()
    private val useCase = GetTutorialSeenStateUseCase(settingsRepository)

    @Test
    fun `invoke should return a flow with true when tutorial has been seen`() = runTest {
        every { settingsRepository.hasSeenTutorial } returns flowOf(true)

        val resultFlow = useCase()

        assertTrue(resultFlow.first())
    }

    @Test
    fun `invoke should return a flow with false when tutorial has not been seen`() = runTest {
        every { settingsRepository.hasSeenTutorial } returns flowOf(false)

        val resultFlow = useCase()

        assertFalse(resultFlow.first())
    }
}
