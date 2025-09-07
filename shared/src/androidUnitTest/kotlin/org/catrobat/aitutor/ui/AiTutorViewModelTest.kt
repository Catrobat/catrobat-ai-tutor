package org.catrobat.aitutor.ui

import android.app.Application
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import dev.mokkery.MockMode
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.catrobat.aitutor.domain.model.AiAppInfo
import org.catrobat.aitutor.domain.model.PlatformImage
import org.catrobat.aitutor.domain.prompt.PromptVersion
import org.catrobat.aitutor.domain.repository.AiAppRepository
import org.catrobat.aitutor.domain.repository.SettingsRepository
import org.catrobat.aitutor.domain.usecase.CreateShareablePromptUseCase
import org.catrobat.aitutor.domain.usecase.GetInstalledAiAppsUseCase
import org.catrobat.aitutor.domain.usecase.GetTutorialSeenStateUseCase
import org.catrobat.aitutor.domain.usecase.SetTutorialSeenUseCase
import org.catrobat.aitutor.ui.viewmodel.AiTutorViewModel
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class AiTutorViewModelTest {
    // Mock Repositories
    private val aiAppRepository = mock<AiAppRepository>()
    private val settingsRepository = mock<SettingsRepository>(MockMode.autoUnit)

    // Instances of the Use Cases and Launcher
    private val createShareablePromptUseCase = CreateShareablePromptUseCase()
    private val getInstalledAiAppsUseCase = GetInstalledAiAppsUseCase(aiAppRepository)
    private val getTutorialSeenStateUseCase = GetTutorialSeenStateUseCase(settingsRepository)
    private val setTutorialSeenUseCase = SetTutorialSeenUseCase(settingsRepository)
    private lateinit var aiAppLauncher: AiAppLauncher

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var viewModel: AiTutorViewModel

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        val context = ApplicationProvider.getApplicationContext<Application>()
        aiAppLauncher = AiAppLauncher(context)
        everySuspend { aiAppRepository.getInstalledAiApps() } returns emptyList()
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel() {
        viewModel = AiTutorViewModel(
            getInstalledAiAppsUseCase,
            createShareablePromptUseCase,
            aiAppLauncher,
            getTutorialSeenStateUseCase,
            setTutorialSeenUseCase
        )
    }

    @Test
    fun `init should load installed apps and update state on success`() = runTest {
        val mockIcon = mock<PlatformImage>()
        val fakeApps = listOf(AiAppInfo("Test App", "com.test.app", mockIcon))
        everySuspend { aiAppRepository.getInstalledAiApps() } returns fakeApps

        createViewModel()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals(fakeApps, state.installedApps)
    }


    @Test
    fun `handleTutorVisibility should show tutorial if not seen before`() = runTest {
        every { settingsRepository.hasSeenTutorial } returns flowOf(false)
        createViewModel()

        viewModel.handleTutorVisibility(true)

        assertEquals(TutorUiStep.ShowingTutorial, viewModel.uiState.value.currentStep)
    }

    @Test
    fun `handleTutorVisibility should show input if tutorial has been seen`() = runTest {
        every { settingsRepository.hasSeenTutorial } returns flowOf(true)
        createViewModel()

        viewModel.handleTutorVisibility(true)

        assertEquals(TutorUiStep.AwaitingInput, viewModel.uiState.value.currentStep)
    }

    @Test
    fun `dismissTutorial should set tutorial as seen and move to input state`() = runTest {
        createViewModel()

        viewModel.dismissTutorial()

        verifySuspend { settingsRepository.setTutorialSeen(true) }
        assertEquals(TutorUiStep.AwaitingInput, viewModel.uiState.value.currentStep)
    }

    @Test
    fun `launchAiApp should create prompt and prepare correct intent`() = runTest {
        val userQuestion = "My question"
        val codeContext = "val code = 1"
        val packageName = "com.test.app"
        createViewModel()
        viewModel.onUserQuestionChanged(userQuestion)

        val expectedPrompt = createShareablePromptUseCase(
            userQuestion = userQuestion,
            isCodeContextIncluded = true,
            codeContext = codeContext,
            isOutputContextIncluded = null,
            outputContext = null,
            promptVersion = PromptVersion.V1,
            systemContext = null
        )

        viewModel.launchAiApp(packageName = packageName, codeContext = codeContext)

        // Use Robolectric's shadow application to check the intent
        val shadowApp = shadowOf(ApplicationProvider.getApplicationContext<Application>())
        val startedIntent = shadowApp.nextStartedActivity

        assertNotNull(startedIntent, "An intent should have been prepared to start an activity.")
        assertEquals(Intent.ACTION_SEND, startedIntent.action)
        assertEquals("text/plain", startedIntent.type)
        assertEquals(packageName, startedIntent.`package`)
        assertEquals(expectedPrompt, startedIntent.getStringExtra(Intent.EXTRA_TEXT))
    }
}
