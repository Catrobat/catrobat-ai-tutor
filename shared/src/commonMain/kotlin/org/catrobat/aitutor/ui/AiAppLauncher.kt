package org.catrobat.aitutor.ui

expect class AiAppLauncher {
    fun launchApp(
        prompt: String,
        packageName: String? = null,
    )
}
