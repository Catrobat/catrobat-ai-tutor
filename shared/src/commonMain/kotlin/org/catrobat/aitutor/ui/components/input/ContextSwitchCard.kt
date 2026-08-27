package org.catrobat.aitutor.ui.components.input

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.catrobat.aitutor.ui.theme.AiTutorTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun ContextSwitchCard(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = AiTutorTheme.colors.surface.copy(alpha = 0.95f),
        shape = RoundedCornerShape(24.dp),
        shadowElevation = 8.dp,
    ) {
        Box(modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = text,
                    color = AiTutorTheme.colors.onSurface,
                    modifier = Modifier.weight(1f),
                )
                Switch(
                    checked = checked,
                    onCheckedChange = onCheckedChange,
                    colors =
                        SwitchDefaults.colors(
                            checkedThumbColor = AiTutorTheme.colors.primary,
                            checkedTrackColor = AiTutorTheme.colors.secondaryContainer,
                        ),
                )
            }
        }
    }
}

@Preview
@Composable
private fun ContextSwitchCardPreview() {
    Surface(color = AiTutorTheme.colors.onSurface) {
        Box(Modifier.padding(16.dp)) {
            ContextSwitchCard(
                text = "Include code context",
                checked = true,
                onCheckedChange = {},
            )
        }
    }
}
