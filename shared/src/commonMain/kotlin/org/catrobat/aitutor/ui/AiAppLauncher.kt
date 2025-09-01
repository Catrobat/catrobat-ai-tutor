package org.catrobat.aitutor.ui

expect class AiAppLauncher {
    suspend fun launchApp(
        prompt: String,
        packageName: String? = null,
    )
}
