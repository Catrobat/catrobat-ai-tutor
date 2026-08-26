package org.catrobat.aitutor.ui.components.input

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.catrobat.aitutor.ui.theme.AiTutorColors
import org.catrobat.shared.generated.resources.Res
import org.catrobat.shared.generated.resources.cancel
import org.catrobat.shared.generated.resources.send
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun InputBottomBar(
    sendEnabled: Boolean,
    onCancel: () -> Unit,
    onSend: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextButton(onClick = onCancel) {
            Text(
                text = stringResource(Res.string.cancel),
                color = AiTutorColors.onSurfaceVariant,
            )
        }
        Spacer(Modifier.width(8.dp))
        Button(
            onClick = onSend,
            enabled = sendEnabled,
            colors =
                ButtonDefaults.buttonColors(
                    containerColor = AiTutorColors.primary,
                    disabledContainerColor = AiTutorColors.primary.copy(alpha = 0.12f),
                    contentColor = AiTutorColors.onPrimary,
                    disabledContentColor = AiTutorColors.onPrimary.copy(alpha = 0.38f),
                ),
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send,
                contentDescription = stringResource(Res.string.send),
            )
        }
    }
}

@Preview
@Composable
private fun InputBottomBarPreview() {
    Surface(color = AiTutorColors.surface) {
        Box(Modifier.padding(16.dp)) {
            InputBottomBar(
                sendEnabled = true,
                onCancel = {},
                onSend = {},
            )
        }
    }
}
