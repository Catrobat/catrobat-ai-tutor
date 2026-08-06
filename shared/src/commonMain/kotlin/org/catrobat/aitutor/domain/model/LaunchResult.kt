package org.catrobat.aitutor.domain.model

sealed interface LaunchResult {
    data object Success : LaunchResult

    data class Error(val error: AiTutorError) : LaunchResult
}
