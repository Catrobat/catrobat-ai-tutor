package org.catrobat.aitutor.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.catrobat.aitutor.ui.theme.AiTutorColors

@Composable
fun ApiKeySetupView(
    onSave: (String) -> Unit,
    onDismiss: () -> Unit,
    onClearKey: () -> Unit = {},
    isKeyAlreadySaved: Boolean = false,
) {
    var apiKey by remember { mutableStateOf("") }
    val uriHandler = LocalUriHandler.current

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = AiTutorColors.surface,
            contentColor = AiTutorColors.onSurface,
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = if (isKeyAlreadySaved) "Update API Key" else "Gemini API Key",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(Modifier.height(16.dp))

                OutlinedTextField(
                    value = apiKey,
                    onValueChange = { apiKey = it },
                    label = { Text(if (isKeyAlreadySaved) "Enter New API Key" else "Enter API Key") },
                    placeholder = {
                        if (isKeyAlreadySaved) {
                            Text("••••••••••••••••")
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors =
                        OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AiTutorColors.primary,
                            unfocusedBorderColor = AiTutorColors.onSurfaceVariant,
                            cursorColor = AiTutorColors.primary,
                        ),
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "Your key is stored securely on device and never shared",
                    style = MaterialTheme.typography.bodySmall,
                    color = AiTutorColors.onSurfaceVariant,
                )

                Spacer(Modifier.height(8.dp))

                TextButton(onClick = { uriHandler.openUri("https://aistudio.google.com") }) {
                    Text(
                        text = "Get a free key at aistudio.google.com",
                        style = MaterialTheme.typography.bodySmall,
                        color = AiTutorColors.primary,
                    )
                }

                if (isKeyAlreadySaved) {
                    Spacer(Modifier.height(8.dp))
                    TextButton(onClick = {
                        onClearKey()
                        onDismiss()
                    }) {
                        Text(
                            text = "Clear Key",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Red.copy(alpha = 0.7f),
                        )
                    }
                }

                Spacer(Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel", color = AiTutorColors.primary)
                    }
                    Spacer(Modifier.width(8.dp))
                    Button(
                        onClick = { if (apiKey.isNotBlank()) onSave(apiKey) },
                        colors = ButtonDefaults.buttonColors(containerColor = AiTutorColors.primary),
                        enabled = apiKey.isNotBlank(),
                    ) {
                        Text("Save", color = AiTutorColors.onPrimary)
                    }
                }
            }
        }
    }
}
