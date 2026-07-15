package org.catrobat.aitutor.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.catrobat.aitutor.domain.model.AiTutorError
import org.catrobat.aitutor.domain.model.AiTutorErrorType
import org.catrobat.aitutor.domain.model.LaunchResult
import org.catrobat.aitutor.domain.prompt.PromptVersion
import org.catrobat.aitutor.domain.usecase.CreateShareablePromptUseCase
import org.catrobat.aitutor.domain.usecase.GetInstalledAiAppsUseCase
import org.catrobat.aitutor.domain.usecase.GetTutorialSeenStateUseCase
import org.catrobat.aitutor.domain.usecase.SetTutorialSeenUseCase
import org.catrobat.aitutor.ui.AiAppLauncher
import org.catrobat.aitutor.ui.TutorUiStep
import org.catrobat.shared.generated.resources.Res
import org.catrobat.shared.generated.resources.error_loading_installed_ai_apps
import org.jetbrains.compose.resources.getString
import kotlin.time.Duration.Companion.milliseconds

class AiTutorViewModel(
    private val getInstalledAiAppsUseCase: GetInstalledAiAppsUseCase,
    private val createShareablePromptUseCase: CreateShareablePromptUseCase,
    private val aiAppLauncher: AiAppLauncher,
    private val getTutorialSeenStateUseCase: GetTutorialSeenStateUseCase,
    private val setTutorialSeenUseCase: SetTutorialSeenUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(AiTutorUiState())
    val uiState: StateFlow<AiTutorUiState> = _uiState.asStateFlow()

    private val _errors = Channel<AiTutorError>(Channel.BUFFERED)
    val errors: Flow<AiTutorError> = _errors.receiveAsFlow()

    init {
        loadInstalledApps()
    }

    fun handleTutorVisibility(isShown: Boolean) {
        if (isShown) {
            viewModelScope.launch {
                val hasSeenTutorial = getTutorialSeenStateUseCase().first()
                val nextStep =
                    if (hasSeenTutorial) TutorUiStep.AwaitingInput else TutorUiStep.ShowingTutorial
                _uiState.update { it.copy(currentStep = nextStep) }
            }
        } else {
            _uiState.update {
                it.copy(
                    currentStep = TutorUiStep.Hidden,
                    userQuestion = "",
                )
            }
        }
    }

    fun showTutorial() {
        _uiState.update { it.copy(currentStep = TutorUiStep.ShowingTutorial) }
    }

    fun dismissTutorial() {
        viewModelScope.launch {
            setTutorialSeenUseCase(true)
            _uiState.update { it.copy(currentStep = TutorUiStep.AwaitingInput) }
        }
    }

    fun onUserQuestionChanged(newQuestion: String) {
        _uiState.update { it.copy(userQuestion = newQuestion) }
    }

    fun initializeContexts(
        initialIsOutputContextIncluded: Boolean?,
        initialPromptVersion: PromptVersion?,
    ) {
        _uiState.update { state ->
            state.copy(
                isOutputContextIncluded = initialIsOutputContextIncluded,
                selectedPromptVersion = initialPromptVersion ?: state.selectedPromptVersion,
            )
        }
    }

    fun onToggleCodeContext(isIncluded: Boolean) {
        _uiState.update { it.copy(isCodeContextIncluded = isIncluded) }
    }

    fun onPromptVersionChanged(version: PromptVersion) {
        _uiState.update { it.copy(selectedPromptVersion = version) }
    }

    fun resetPasteStep() {
        _uiState.update { it.copy(currentStep = TutorUiStep.Hidden) }
    }

    fun emitError(error: AiTutorError) {
        viewModelScope.launch {
            _errors.send(error)
        }
    }

    fun onCurrentStepChanged(newStep: TutorUiStep) {
        viewModelScope.launch {
            // Delay so the paste dialog doesn't appear immediately over the launching AI app.
            if (newStep == TutorUiStep.AwaitingPaste) {
                delay(500.milliseconds)
            }

            _uiState.update {
                it.copy(currentStep = newStep)
            }
        }
    }

    // Handler for the optional output context switch
    fun onToggleOutputContext(isIncluded: Boolean) {
        if (_uiState.value.isOutputContextIncluded != null) {
            _uiState.update { it.copy(isOutputContextIncluded = isIncluded) }
        }
    }

    fun showAboutScreen() {
        _uiState.update { it.copy(currentStep = TutorUiStep.ShowingAbout) }
    }

    fun dismissAboutScreen() {
        _uiState.update { it.copy(currentStep = TutorUiStep.AwaitingInput) }
    }

    fun launchAiApp(
        packageName: String? = null,
        codeContext: String?,
        outputContext: String? = null,
        systemContext: String? = null,
    ) {
        val currentState = _uiState.value
        viewModelScope.launch {
            val finalPrompt =
                createShareablePromptUseCase(
                    userQuestion = currentState.userQuestion,
                    isCodeContextIncluded = currentState.isCodeContextIncluded,
                    codeContext = codeContext,
                    isOutputContextIncluded = currentState.isOutputContextIncluded,
                    outputContext = outputContext,
                    promptVersion = currentState.selectedPromptVersion,
                    systemContext = systemContext,
                )
            when (val result = aiAppLauncher.launchApp(finalPrompt, packageName)) {
                is LaunchResult.Error -> _errors.send(result.error)
                LaunchResult.Success -> Unit
            }
        }
    }

    private fun loadInstalledApps() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val apps = getInstalledAiAppsUseCase()
                _uiState.update { it.copy(installedApps = apps, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false) }
                _errors.send(
                    AiTutorError(
                        type = AiTutorErrorType.LOADING_INSTALLED_APPS,
                        message =
                            getString(
                                Res.string.error_loading_installed_ai_apps,
                                e.message ?: "",
                            ),
                    ),
                )
            }
        }
    }
}
