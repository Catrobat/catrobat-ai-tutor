package org.catrobat.aitutor.ui.components.input

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.catrobat.aitutor.ui.theme.AiTutorColors
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun ActionPillButton(
    icon: @Composable (Modifier) -> Unit,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        onClick = onClick,
        modifier = modifier.height(40.dp),
        shape = RoundedCornerShape(16.dp),
        color = AiTutorColors.secondaryContainer,
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            icon(Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                color = AiTutorColors.onSecondaryContainer,
                maxLines = 1,
            )
        }
    }
}

@Preview
@Composable
private fun ActionPillButtonPreview() {
    Surface(color = AiTutorColors.surface) {
        Box(Modifier.padding(16.dp)) {
            ActionPillButton(
                icon = { modifier ->
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.HelpOutline,
                        contentDescription = null,
                        tint = AiTutorColors.onSecondaryContainer,
                        modifier = modifier,
                    )
                },
                label = "Help",
                onClick = {},
            )
        }
    }
}
