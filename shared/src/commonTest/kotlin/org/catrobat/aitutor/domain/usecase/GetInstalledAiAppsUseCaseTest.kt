package org.catrobat.aitutor.domain.usecase

import dev.mokkery.answering.returns
import dev.mokkery.answering.throws
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.test.runTest
import org.catrobat.aitutor.domain.model.AiAppInfo
import org.catrobat.aitutor.domain.model.PlatformImage
import org.catrobat.aitutor.domain.repository.AiAppRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class GetInstalledAiAppsUseCaseTest {
    private val repository = mock<AiAppRepository>()
    private val useCase = GetInstalledAiAppsUseCase(repository)

    @Test
    fun `invoke should return list of apps from repository on success`() =
        runTest {
            val mockIcon = mock<PlatformImage>()
            val fakeApps =
                listOf(
                    AiAppInfo("App One", "com.app.one", mockIcon),
                    AiAppInfo("App Two", "com.app.two", mockIcon),
                )
            everySuspend { repository.getInstalledAiApps() } returns fakeApps

            val result = useCase()

            assertEquals(fakeApps, result)
            assertEquals(2, result.size)
        }

    @Test
    fun `invoke should return an empty list if repository returns one`() =
        runTest {
            everySuspend { repository.getInstalledAiApps() } returns emptyList()

            val result = useCase()

            assertEquals(emptyList(), result)
        }

    @Test
    fun `invoke should propagate exception when repository throws an error`() =
        runTest {
            val expectedException = RuntimeException("Failed to query packages")
            everySuspend { repository.getInstalledAiApps() } throws expectedException

            val thrownException =
                assertFailsWith<RuntimeException> {
                    useCase()
                }
            assertEquals(expectedException.message, thrownException.message)
        }
}
