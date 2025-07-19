package org.catrobat.aitutor.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import org.catrobat.aitutor.ui.theme.AiTutorColors

@Composable
internal fun InputView(
    modifier: Modifier = Modifier,
    inputText: String,
    isCodeContextIncluded: Boolean,
    onInputTextChange: (String) -> Unit,
    onDismissRequest: () -> Unit,
    onisCodeContextIncludedChange: (Boolean) -> Unit,
    onSend: (String) -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Box(
            modifier = Modifier.fillMaxSize().imePadding().then(modifier),
            contentAlignment = Alignment.BottomCenter,
        ) {
            Surface(
                modifier =
                    Modifier
                        .padding(horizontal = 16.dp, vertical = 24.dp)
                        .widthIn(max = 580.dp),
                color = AiTutorColors.surface.copy(alpha = 0.95f),
                shape = RoundedCornerShape(24.dp),
                shadowElevation = 8.dp,
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            text = "Include code context",
                            color = AiTutorColors.onSurface,
                            modifier = Modifier.weight(1f),
                            style = MaterialTheme.typography.titleSmall,
                        )
                        Switch(
                            checked = isCodeContextIncluded,
                            onCheckedChange = onisCodeContextIncludedChange,
                            colors =
                                SwitchDefaults.colors(
                                    checkedThumbColor = AiTutorColors.primary,
                                    checkedTrackColor = AiTutorColors.secondaryContainer,
                                ),
                        )
                    }

                    Spacer(Modifier.height(16.dp))

                    TextField(
                        value = inputText,
                        onValueChange = onInputTextChange,
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text(
                                "Type your question here...",
                                color = AiTutorColors.onSurfaceVariant,
                            )
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = AiTutorColors.onSurfaceVariant,
                            )
                        },
                        colors =
                            TextFieldDefaults.colors().copy(
                                focusedContainerColor = AiTutorColors.secondaryContainer,
                                unfocusedContainerColor = AiTutorColors.secondaryContainer,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                cursorColor = AiTutorColors.primary,
                                focusedTextColor = AiTutorColors.onSurface,
                                unfocusedTextColor = AiTutorColors.onSurfaceVariant,
                            ),
                        shape = RoundedCornerShape(16.dp),
                    )

                    Spacer(Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        TextButton(onClick = onDismissRequest) {
                            Text("Cancel", color = AiTutorColors.onSurfaceVariant)
                        }
                        Spacer(Modifier.width(8.dp))
                        Button(
                            onClick = { onSend(inputText) },
                            enabled = inputText.isNotBlank(),
                            colors =
                                ButtonDefaults.buttonColors(
                                    containerColor = AiTutorColors.primary,
                                    disabledContainerColor =
                                        AiTutorColors.primary.copy(
                                            alpha = 0.12f,
                                        ),
                                    contentColor = AiTutorColors.onPrimary,
                                    disabledContentColor =
                                        AiTutorColors.onPrimary.copy(
                                            alpha = 0.38f,
                                        ),
                                ),
                        ) {
                            Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send")
                        }
                    }
                }
            }
        }
    }
}
