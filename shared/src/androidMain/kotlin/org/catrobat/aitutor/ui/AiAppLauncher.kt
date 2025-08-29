package org.catrobat.aitutor.ui

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast

actual class AiAppLauncher(private val context: Context) {
    actual fun launchApp(
        prompt: String,
        packageName: String?,
    ) {
        try {
            val sendIntent =
                Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, prompt)
                    type = "text/plain"

                    // If a specific package is provided, target it.
                    // If not, the system will show a chooser.
                    `package` = packageName
                }
            // Use a chooser if no specific package is set (for the "More" option)
            val chooserIntent =
                if (packageName == null) {
                    Intent.createChooser(sendIntent, "Send prompt with...")
                } else {
                    sendIntent
                }
            chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(chooserIntent)
        } catch (e: ActivityNotFoundException) {
            // Fallback for apps that don't handle ACTION_SEND
            if (packageName != null) {
                val launchIntent = context.packageManager.getLaunchIntentForPackage(packageName)
                if (launchIntent != null) {
                    context.startActivity(launchIntent)
                    // You should probably copy the prompt to the clipboard here as well.
                    Toast.makeText(
                        context,
                        "Couldn't send prompt directly. Please paste it manually.",
                        Toast.LENGTH_LONG,
                    ).show()
                } else {
                    Toast.makeText(context, "Could not launch this app.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "No app found to handle this action.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}
