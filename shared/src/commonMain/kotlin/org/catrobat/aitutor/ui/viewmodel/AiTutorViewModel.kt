package org.catrobat.aitutor.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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

class AiTutorViewModel(
    private val getInstalledAiAppsUseCase: GetInstalledAiAppsUseCase,
    private val createShareablePromptUseCase: CreateShareablePromptUseCase,
    private val aiAppLauncher: AiAppLauncher,
    private val getTutorialSeenStateUseCase: GetTutorialSeenStateUseCase,
    private val setTutorialSeenUseCase: SetTutorialSeenUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(AiTutorUiState())
    val uiState: StateFlow<AiTutorUiState> = _uiState.asStateFlow()

    private val _errorMessageFlow = MutableSharedFlow<String>()
    val errorMessageFlow: SharedFlow<String> = _errorMessageFlow.asSharedFlow()

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

    fun initializeContexts(initialIsOutputContextIncluded: Boolean?) {
        _uiState.update {
            it.copy(
                isOutputContextIncluded = initialIsOutputContextIncluded,
            )
        }
    }

    fun onToggleCodeContext(isIncluded: Boolean) {
        _uiState.update { it.copy(isCodeContextIncluded = isIncluded) }
    }

    fun onPromptVersionChanged(version: PromptVersion) {
        _uiState.update { it.copy(selectedPromptVersion = version) }
    }

    fun onCurrentStepChanged(newStep: TutorUiStep) {
        _uiState.update { it.copy(currentStep = newStep) }
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
        viewModelScope.launch {
            aiAppLauncher.launchApp(finalPrompt, packageName)
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
                _errorMessageFlow.emit(
                    getString(
                        Res.string.error_loading_installed_ai_apps,
                        e.message ?: "",
                    ),
                )
            }
        }
    }
}
