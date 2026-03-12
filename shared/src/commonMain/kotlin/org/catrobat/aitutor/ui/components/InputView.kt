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
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import org.catrobat.aitutor.domain.prompt.PromptVersion
import org.catrobat.aitutor.ui.theme.AiTutorColors
import org.catrobat.aitutor.util.isDebug
import org.catrobat.shared.generated.resources.Res
import org.catrobat.shared.generated.resources.about
import org.catrobat.shared.generated.resources.cancel
import org.catrobat.shared.generated.resources.include_code_context
import org.catrobat.shared.generated.resources.include_code_output
import org.catrobat.shared.generated.resources.prompt_version_debug
import org.catrobat.shared.generated.resources.send
import org.catrobat.shared.generated.resources.show_tutorial
import org.catrobat.shared.generated.resources.type_your_question_here
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun InputView(
    modifier: Modifier = Modifier,
    inputText: String,
    isCodeContextIncluded: Boolean,
    onToggleCodeContext: (Boolean) -> Unit,
    isOutputContextIncluded: Boolean?,
    onToggleOutputContext: (Boolean) -> Unit,
    availablePromptVersions: List<PromptVersion>,
    selectedPromptVersion: PromptVersion,
    onPromptVersionChange: (PromptVersion) -> Unit,
    onInputTextChange: (String) -> Unit,
    onDismissRequest: () -> Unit,
    onSend: (String) -> Unit,
    onAskInApp: (String) -> Unit,
    onHelpRequest: () -> Unit,
    onAboutRequest: () -> Unit,
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
                    ContextSwitchRow(
                        text = stringResource(Res.string.include_code_context),
                        checked = isCodeContextIncluded,
                        onCheckedChange = onToggleCodeContext,
                    )

                    // Output Context (Conditionally present)
                    if (isOutputContextIncluded != null) {
                        ContextSwitchRow(
                            text = stringResource(Res.string.include_code_output),
                            checked = isOutputContextIncluded,
                            onCheckedChange = onToggleOutputContext,
                        )
                    }

                    if (isDebug) {
                        Spacer(Modifier.height(8.dp))
                        var expanded by remember { mutableStateOf(false) }

                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded },
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            OutlinedTextField(
                                modifier =
                                    Modifier.menuAnchor(MenuAnchorType.PrimaryEditable, true)
                                        .fillMaxWidth(),
                                readOnly = true,
                                value = selectedPromptVersion.displayName,
                                onValueChange = {},
                                label = { Text(text = stringResource(Res.string.prompt_version_debug)) },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                                colors =
                                    TextFieldDefaults.colors().copy(
                                        focusedContainerColor = AiTutorColors.secondaryContainer,
                                        unfocusedContainerColor = AiTutorColors.secondaryContainer,
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent,
                                        cursorColor = AiTutorColors.onSurface,
                                        focusedLabelColor = AiTutorColors.onSurfaceVariant,
                                        unfocusedLabelColor = AiTutorColors.onSurfaceVariant,
                                        focusedTextColor = AiTutorColors.onSurface,
                                        unfocusedTextColor = AiTutorColors.onSurfaceVariant,
                                        focusedTrailingIconColor = AiTutorColors.onSurfaceVariant,
                                        unfocusedTrailingIconColor = AiTutorColors.onSurfaceVariant,
                                    ),
                                shape = RoundedCornerShape(16.dp),
                            )
                            ExposedDropdownMenu(
                                expanded = expanded,
                                containerColor = AiTutorColors.secondaryContainer,
                                onDismissRequest = { expanded = false },
                            ) {
                                availablePromptVersions.forEach { selectionOption ->
                                    DropdownMenuItem(
                                        text = { Text(selectionOption.displayName) },
                                        onClick = {
                                            onPromptVersionChange(selectionOption)
                                            expanded = false
                                        },
                                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                        colors =
                                            MenuDefaults.itemColors().copy(
                                                textColor = AiTutorColors.onSurface,
                                                disabledTextColor = AiTutorColors.onSurfaceVariant,
                                            ),
                                    )
                                }
                            }
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    TextField(
                        value = inputText,
                        onValueChange = onInputTextChange,
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text(
                                text = stringResource(Res.string.type_your_question_here),
                                color = AiTutorColors.onSurfaceVariant,
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
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
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        // About and Help Button
                        Row(
                            modifier =
                                Modifier
                                    .weight(1f)
                                    .height(24.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            IconButton(onClick = onAboutRequest) {
                                Icon(
                                    imageVector = Icons.Outlined.Info,
                                    contentDescription = stringResource(Res.string.about),
                                    tint = AiTutorColors.onSurfaceVariant,
                                )
                            }
                            IconButton(onClick = onHelpRequest) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.HelpOutline,
                                    contentDescription = stringResource(Res.string.show_tutorial),
                                    tint = AiTutorColors.onSurfaceVariant,
                                )
                            }
                        }

                        TextButton(onClick = onDismissRequest) {
                            Text(
                                text = stringResource(Res.string.cancel),
                                color = AiTutorColors.onSurfaceVariant,
                            )
                        }
                        Spacer(Modifier.width(8.dp))
                        TextButton(
                            onClick = { onAskInApp(inputText) },
                            enabled = inputText.isNotBlank()
                        ) {
                            Text(
                                text = "Ask In-App",
                                color = AiTutorColors.primary,
                            )
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
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = stringResource(Res.string.send),
                            )
                        }
                    }
                }
            }
        }
    }
}
