package org.catrobat.aitutor.ui

import org.catrobat.aitutor.domain.model.LaunchResult

expect class AiAppLauncher {
    suspend fun launchApp(
        prompt: String,
        packageName: String? = null,
    ): LaunchResult
}
