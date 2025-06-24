package org.catrobat.aitutor.ui.viewmodel

import org.catrobat.aitutor.domain.model.AiAppInfo
import org.catrobat.aitutor.ui.TutorUiStep

data class AiTutorUiState(
    val installedApps: List<AiAppInfo> = emptyList(),
    val isLoading: Boolean = false,
    val userQuestion: String = "",
    val isCodeContextIncluded: Boolean = true,
    val currentStep: TutorUiStep = TutorUiStep.Hidden,
)
