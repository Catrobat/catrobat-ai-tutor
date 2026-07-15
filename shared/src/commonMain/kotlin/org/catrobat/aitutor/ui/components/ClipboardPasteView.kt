package org.catrobat.aitutor.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.launch
import org.catrobat.aitutor.ui.theme.AiTutorColors
import org.catrobat.aitutor.util.getText
import org.catrobat.shared.generated.resources.Res
import org.catrobat.shared.generated.resources.cancel
import org.catrobat.shared.generated.resources.paste_answer_description
import org.catrobat.shared.generated.resources.paste_answer_error_no_text_found
import org.catrobat.shared.generated.resources.paste_answer_title
import org.catrobat.shared.generated.resources.paste_from_clipboard
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun ClipboardPasteView(
    onPasteResult: (String) -> Unit,
    onDismissRequest: () -> Unit,
    onClipboardPasteError: (String) -> Unit,
) {
    val clipboard = LocalClipboard.current
    val scope = rememberCoroutineScope()
    val noTextFoundError = stringResource(Res.string.paste_answer_error_no_text_found)
) {
    val clipboard = LocalClipboard.current
    val scope = rememberCoroutineScope()
    ClipboardPasteViewContent(
        title = stringResource(Res.string.paste_answer_title),
        description = stringResource(Res.string.paste_answer_description),
        cancelText = stringResource(Res.string.cancel),
        pasteText = stringResource(Res.string.paste_from_clipboard),
        onPaste = {
            scope.launch {
                val text = clipboard.getClipEntry()?.getText()
                if (!text.isNullOrBlank()) {
                    onPasteResult(text)
                } else {
                    onClipboardPasteError(noTextFoundError)
                    onDismissRequest()
                }
                if (!text.isNullOrBlank()) onPasteResult(text)
            }
        },
        onDismissRequest = onDismissRequest,
    )
}

@Composable
private fun ClipboardPasteViewContent(
    title: String,
    description: String,
    cancelText: String,
    pasteText: String,
    onPaste: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            modifier =
                Modifier
                    .padding(horizontal = 24.dp, vertical = 32.dp)
                    .widthIn(max = 580.dp),
            color = AiTutorColors.surface.copy(alpha = 0.95f),
            shape = RoundedCornerShape(24.dp),
            shadowElevation = 8.dp,
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = AiTutorColors.onSurface,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = AiTutorColors.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 8.dp),
                )
                Spacer(Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    TextButton(onClick = onDismissRequest) {
                        Text(
                            text = cancelText,
                            color = AiTutorColors.primary,
                        )
                    }
                    Button(
                        onClick = onPaste,
                        colors = ButtonDefaults.buttonColors(containerColor = AiTutorColors.primary),
                    ) {
                        Text(
                            text = pasteText,
                            color = AiTutorColors.onPrimary,
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ClipboardPasteViewPreview() {
    Surface {
        Box(Modifier.fillMaxSize()) {
            ClipboardPasteViewContent(
                title = "Paste the AI's answer",
                description = "Copy the response from your AI app, then paste it back here to continue.",
                cancelText = "Cancel",
                pasteText = "Paste from clipboard",
                onPaste = {},
                onDismissRequest = {},
            )
        }
    }
}
