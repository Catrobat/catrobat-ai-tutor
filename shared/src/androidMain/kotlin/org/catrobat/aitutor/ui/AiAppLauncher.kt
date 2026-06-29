package org.catrobat.aitutor.ui

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.runBlocking
import org.catrobat.aitutor.domain.model.AiTutorError
import org.catrobat.aitutor.domain.model.AiTutorErrorType
import org.catrobat.aitutor.domain.model.LaunchResult
import org.catrobat.shared.generated.resources.Res
import org.catrobat.shared.generated.resources.could_not_launch_this_app
import org.catrobat.shared.generated.resources.could_not_send_prompt_directly
import org.catrobat.shared.generated.resources.no_app_found_to_handle_this_action
import org.catrobat.shared.generated.resources.send_prompt_with
import org.jetbrains.compose.resources.getString

actual class AiAppLauncher(private val context: Context) {
    actual suspend fun launchApp(
        prompt: String,
        packageName: String?,
    ): LaunchResult {
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
                    val chooserTitle = getString(Res.string.send_prompt_with)
                    Intent.createChooser(sendIntent, chooserTitle)
                } else {
                    sendIntent
                }
            chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(chooserIntent)
            return LaunchResult.Success
        } catch (e: ActivityNotFoundException) {
            // Fallback for apps that don't handle ACTION_SEND
            if (packageName != null) {
                val launchIntent = context.packageManager.getLaunchIntentForPackage(packageName)
                return if (launchIntent != null) {
                    context.startActivity(launchIntent)
                    // You should probably copy the prompt to the clipboard here as well.
                    LaunchResult.Error(
                        AiTutorError(
                            type = AiTutorErrorType.PROMPT_NOT_SENT_DIRECTLY,
                            message = getString(Res.string.could_not_send_prompt_directly),
                        ),
                    )
                } else {
                    LaunchResult.Error(
                        AiTutorError(
                            type = AiTutorErrorType.COULD_NOT_LAUNCH_APP,
                            message = getString(Res.string.could_not_launch_this_app),
                        ),
                    )
                }
            } else {
                return LaunchResult.Error(
                    AiTutorError(
                        type = AiTutorErrorType.NO_APP_FOUND,
                        message = getString(Res.string.no_app_found_to_handle_this_action),
                    ),
                )
            }
        }
    }
}
