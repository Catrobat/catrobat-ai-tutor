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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.launch
import org.catrobat.aitutor.ui.theme.AiTutorTheme
import org.catrobat.aitutor.util.clipEntryOf
import org.catrobat.shared.generated.resources.Res
import org.catrobat.shared.generated.resources.cancel
import org.catrobat.shared.generated.resources.copied_to_clipboard
import org.catrobat.shared.generated.resources.copy_code_context_description
import org.catrobat.shared.generated.resources.copy_code_context_title
import org.catrobat.shared.generated.resources.copy_to_clipboard
import org.catrobat.shared.generated.resources.switch_to_paste_answer
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun ClipboardCopyView(
    codeContext: String,
    onSwitchToPaste: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    val clipboard = LocalClipboard.current
    val scope = rememberCoroutineScope()
    var isCopied by remember { mutableStateOf(false) }

    ClipboardCopyViewContent(
        title = stringResource(Res.string.copy_code_context_title),
        description = stringResource(Res.string.copy_code_context_description),
        cancelText = stringResource(Res.string.cancel),
        copyText = stringResource(Res.string.copy_to_clipboard),
        copiedText = stringResource(Res.string.copied_to_clipboard),
        switchToPasteText = stringResource(Res.string.switch_to_paste_answer),
        isCopyEnabled = codeContext.isNotBlank(),
        isCopied = isCopied,
        onCopy = {
            scope.launch {
                clipboard.setClipEntry(clipEntryOf(codeContext))
                isCopied = true
            }
        },
        onSwitchToPaste = onSwitchToPaste,
        onDismissRequest = onDismissRequest,
    )
}

@Composable
private fun ClipboardCopyViewContent(
    title: String,
    description: String,
    cancelText: String,
    copyText: String,
    copiedText: String,
    switchToPasteText: String,
    isCopyEnabled: Boolean,
    isCopied: Boolean,
    onCopy: () -> Unit,
    onSwitchToPaste: () -> Unit,
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
            color = AiTutorTheme.colors.surface.copy(alpha = 0.95f),
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
                    color = AiTutorTheme.colors.onSurface,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = AiTutorTheme.colors.onSurfaceVariant,
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
                            color = AiTutorTheme.colors.primary,
                        )
                    }
                    Button(
                        onClick = onCopy,
                        enabled = isCopyEnabled,
                        colors = ButtonDefaults.buttonColors(containerColor = AiTutorTheme.colors.primary),
                    ) {
                        Text(
                            text = if (isCopied) copiedText else copyText,
                            color = AiTutorTheme.colors.onPrimary,
                        )
                    }
                }
                Spacer(Modifier.height(8.dp))
                SwitchFlowLink(text = switchToPasteText, onClick = onSwitchToPaste)
            }
        }
    }
}

@Preview
@Composable
private fun ClipboardCopyViewPreview() {
    Surface {
        Box(Modifier.fillMaxSize()) {
            ClipboardCopyViewContent(
                title = "Copy Code Context",
                description = "Copy your code below, then paste it into your AI app manually.",
                cancelText = "Cancel",
                copyText = "Copy to Clipboard",
                copiedText = "Copied!",
                switchToPasteText = "Want to paste the AI's answer instead?",
                isCopyEnabled = true,
                isCopied = false,
                onCopy = {},
                onSwitchToPaste = {},
                onDismissRequest = {},
            )
        }
    }
}
