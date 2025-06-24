package org.catrobat.aitutor.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.catrobat.aitutor.domain.usecase.CreateShareablePromptUseCase
import org.catrobat.aitutor.domain.usecase.GetInstalledAiAppsUseCase
import org.catrobat.aitutor.ui.AiAppLauncher
import org.catrobat.aitutor.ui.TutorUiStep

class AiTutorViewModel(
    private val getInstalledAiAppsUseCase: GetInstalledAiAppsUseCase,
    private val createShareablePromptUseCase: CreateShareablePromptUseCase,
    private val aiAppLauncher: AiAppLauncher,
) : ViewModel() {
    private val _uiState = MutableStateFlow(AiTutorUiState())
    val uiState: StateFlow<AiTutorUiState> = _uiState.asStateFlow()

    private val _errorMessageFlow = MutableSharedFlow<String>()
    val errorMessageFlow: SharedFlow<String> = _errorMessageFlow.asSharedFlow()

    init {
        loadInstalledApps()
    }

    fun onUserQuestionChanged(newQuestion: String) {
        _uiState.update { it.copy(userQuestion = newQuestion) }
    }

    fun onToggleCodeContext(isIncluded: Boolean) {
        _uiState.update { it.copy(isCodeContextIncluded = isIncluded) }
    }

    fun onCurrentStepChanged(newStep: TutorUiStep) {
        _uiState.update { it.copy(currentStep = newStep) }
    }

    fun launchAiApp(
        packageName: String? = null,
        codeContext: String?,
    ) {
        val currentState = _uiState.value
        val finalPrompt =
            createShareablePromptUseCase(
                userQuestion = currentState.userQuestion,
                isCodeContextIncluded = currentState.isCodeContextIncluded,
                codeContext = codeContext,
            )
        aiAppLauncher.launchApp(finalPrompt, packageName)
    }

    private fun loadInstalledApps() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val apps = getInstalledAiAppsUseCase()
                _uiState.update { it.copy(installedApps = apps, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false) }
                _errorMessageFlow.emit("Failed to load installed AI apps: ${e.message}")
            }
        }
    }
}
