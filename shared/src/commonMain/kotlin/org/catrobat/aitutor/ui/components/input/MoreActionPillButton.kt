package org.catrobat.aitutor.ui.components.input

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import org.catrobat.aitutor.ui.theme.AiTutorTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun MoreActionPillButton(
    moreLabel: String,
    helpLabel: String,
    aboutLabel: String,
    onHelpClick: () -> Unit,
    onAboutClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    val density = LocalDensity.current

    Box(modifier = modifier) {
        ActionPillButton(
            icon = { iconModifier ->
                Icon(
                    imageVector = Icons.Default.MoreHoriz,
                    contentDescription = null,
                    tint = AiTutorTheme.colors.onSecondaryContainer,
                    modifier = iconModifier,
                )
            },
            label = moreLabel,
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth(),
        )
        if (expanded) {
            val gapPx = with(density) { 8.dp.roundToPx() }
            Popup(
                popupPositionProvider = remember(gapPx) { UpwardPopupPositionProvider(gapPx) },
                onDismissRequest = { expanded = false },
            ) {
                Surface(
                    modifier = Modifier.width(180.dp),
                    color = AiTutorTheme.colors.secondaryContainer,
                    shape = RoundedCornerShape(16.dp),
                    shadowElevation = 8.dp,
                ) {
                    Column {
                        DropdownMenuItem(
                            text = { Text(text = helpLabel, color = AiTutorTheme.colors.onSurface) },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.HelpOutline,
                                    contentDescription = null,
                                    tint = AiTutorTheme.colors.onSurfaceVariant,
                                )
                            },
                            onClick = {
                                expanded = false
                                onHelpClick()
                            },
                        )
                        DropdownMenuItem(
                            text = { Text(text = aboutLabel, color = AiTutorTheme.colors.onSurface) },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.Info,
                                    contentDescription = null,
                                    tint = AiTutorTheme.colors.onSurfaceVariant,
                                )
                            },
                            onClick = {
                                expanded = false
                                onAboutClick()
                            },
                        )
                    }
                }
            }
        }
    }
}

/** Positions a popup's bottom edge [gapPx] pixels above the anchor's top edge, left-aligned to it. */
private class UpwardPopupPositionProvider(private val gapPx: Int) : PopupPositionProvider {
    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize,
    ): IntOffset {
        val x = anchorBounds.left
        val y = anchorBounds.top - popupContentSize.height - gapPx
        return IntOffset(x, y.coerceAtLeast(0))
    }
}

@Preview
@Composable
private fun MoreActionPillButtonPreview() {
    Surface(color = AiTutorTheme.colors.surface) {
        Box(
            Modifier.padding(
                top = 102.dp,
                bottom = 16.dp,
                start = 16.dp,
                end = 16.dp,
            ),
        ) {
            MoreActionPillButton(
                moreLabel = "More",
                helpLabel = "Help",
                aboutLabel = "About",
                onHelpClick = {},
                onAboutClick = {},
            )
        }
    }
}
