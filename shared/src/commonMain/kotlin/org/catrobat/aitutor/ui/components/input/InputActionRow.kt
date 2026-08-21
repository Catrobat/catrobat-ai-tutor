package org.catrobat.aitutor.ui.components.input

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.catrobat.aitutor.ui.theme.AiTutorTheme
import org.catrobat.shared.generated.resources.Res
import org.catrobat.shared.generated.resources.ic_content_paste
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * The row of quick actions shown above the input text field. Holds at most 3 evenly stretched
 * pill buttons: when a host app enables the clipboard flow, Help/About collapse into a single
 * "More" menu to make room for the "Paste"/"Copy" shortcuts; otherwise Help/About are shown
 * directly.
 */
@Composable
internal fun InputActionRow(
    hasClipboardFlow: Boolean,
    moreLabel: String,
    pasteLabel: String,
    copyLabel: String,
    helpLabel: String,
    aboutLabel: String,
    onPasteClick: () -> Unit,
    onCopyClick: () -> Unit,
    onHelpClick: () -> Unit,
    onAboutClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (hasClipboardFlow) {
            MoreActionPillButton(
                moreLabel = moreLabel,
                helpLabel = helpLabel,
                aboutLabel = aboutLabel,
                onHelpClick = onHelpClick,
                onAboutClick = onAboutClick,
                modifier = Modifier.weight(1f),
            )
            ActionPillButton(
                icon = { modifier ->
                    Icon(
                        painter = painterResource(Res.drawable.ic_content_paste),
                        contentDescription = null,
                        tint = AiTutorTheme.colors.onSecondaryContainer,
                        modifier = modifier,
                    )
                },
                label = pasteLabel,
                onClick = onPasteClick,
                modifier = Modifier.weight(1f),
            )
            ActionPillButton(
                icon = { modifier ->
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = null,
                        tint = AiTutorTheme.colors.onSecondaryContainer,
                        modifier = modifier,
                    )
                },
                label = copyLabel,
                onClick = onCopyClick,
                modifier = Modifier.weight(1f),
            )
        } else {
            ActionPillButton(
                icon = { modifier ->
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.HelpOutline,
                        contentDescription = null,
                        tint = AiTutorTheme.colors.onSecondaryContainer,
                        modifier = modifier,
                    )
                },
                label = helpLabel,
                onClick = onHelpClick,
                modifier = Modifier.weight(1f),
            )
            ActionPillButton(
                icon = { modifier ->
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = null,
                        tint = AiTutorTheme.colors.onSecondaryContainer,
                        modifier = modifier,
                    )
                },
                label = aboutLabel,
                onClick = onAboutClick,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Preview
@Composable
private fun InputActionRowWithClipboardFlowPreview() {
    Surface(color = AiTutorTheme.colors.surface) {
        Box(Modifier.padding(16.dp)) {
            InputActionRow(
                hasClipboardFlow = true,
                moreLabel = "More",
                pasteLabel = "Paste",
                copyLabel = "Copy",
                helpLabel = "Help",
                aboutLabel = "About",
                onPasteClick = {},
                onCopyClick = {},
                onHelpClick = {},
                onAboutClick = {},
            )
        }
    }
}

@Preview
@Composable
private fun InputActionRowWithoutClipboardFlowPreview() {
    Surface(color = AiTutorTheme.colors.surface) {
        Box(Modifier.padding(16.dp)) {
            InputActionRow(
                hasClipboardFlow = false,
                moreLabel = "More",
                pasteLabel = "Paste",
                copyLabel = "Copy",
                helpLabel = "Help",
                aboutLabel = "About",
                onPasteClick = {},
                onCopyClick = {},
                onHelpClick = {},
                onAboutClick = {},
            )
        }
    }
}
