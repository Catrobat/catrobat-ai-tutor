package org.catrobat.aitutor.ui.viewmodel

import org.catrobat.aitutor.domain.model.AiAppInfo
import org.catrobat.aitutor.domain.prompt.PromptVersion
import org.catrobat.aitutor.ui.TutorUiStep

data class AiTutorUiState(
    val installedApps: List<AiAppInfo> = emptyList(),
    val isLoading: Boolean = false,
    val userQuestion: String = "",
    val currentStep: TutorUiStep = TutorUiStep.Hidden,
    // --- Context States ---
    // The main code context is always available to be toggled
    val isCodeContextIncluded: Boolean = true,
    // Output context is also optional
    val isOutputContextIncluded: Boolean? = null,
    // --- Debug/Prompt Selection ---
    val availablePromptVersions: List<PromptVersion> = PromptVersion.entries,
    val selectedPromptVersion: PromptVersion = PromptVersion.V1,
)
