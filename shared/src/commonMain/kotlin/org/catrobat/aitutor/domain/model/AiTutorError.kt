package org.catrobat.aitutor.domain.model

enum class AiTutorErrorType {
    LOADING_INSTALLED_APPS,
    CLIPBOARD_PASTE,
    NO_APP_FOUND,
    COULD_NOT_LAUNCH_APP,
    PROMPT_NOT_SENT_DIRECTLY,
}

data class AiTutorError(
    val type: AiTutorErrorType,
    val message: String,
)
