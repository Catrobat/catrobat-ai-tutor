package org.catrobat.aitutor.domain.model

sealed interface ClipboardReadResult {
    data class Success(val text: String) : ClipboardReadResult

    data object Empty : ClipboardReadResult

    data class Error(val cause: Throwable) : ClipboardReadResult
}
