package org.catrobat.aitutor.data

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build

class InstalledAiAppDataSource(private val context: Context) {
    private val pm: PackageManager = context.packageManager

    // A predefined list of known AI application package names.
    private val aiChatAppPackages =
        listOf(
            "com.openai.chatgpt",
            "com.google.android.apps.bard",
            "com.anthropic.claude",
            "ai.perplexity.app.android",
        )

    fun getInstalledAiAppsInfo(): List<ApplicationInfo> {
        val apps = mutableListOf<ApplicationInfo>()
        for (packageName in aiChatAppPackages) {
            try {
                val appInfo =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        pm.getApplicationInfo(
                            packageName,
                            PackageManager.ApplicationInfoFlags.of(0),
                        )
                    } else {
                        @Suppress("DEPRECATION")
                        pm.getApplicationInfo(packageName, 0)
                    }
                apps.add(appInfo)
            } catch (e: PackageManager.NameNotFoundException) {
                // App is not installed, ignore.
            }
        }
        return apps
    }
}
