package org.catrobat.aitutor.ui

import org.catrobat.aitutor.domain.model.LaunchResult

actual class AiAppLauncher {
    actual suspend fun launchApp(
        prompt: String,
        packageName: String?,
    ): LaunchResult {
        TODO("Not yet implemented")
    }
}
