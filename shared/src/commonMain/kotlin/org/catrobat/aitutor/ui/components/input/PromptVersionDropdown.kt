package org.catrobat.aitutor.ui.components.input

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.catrobat.aitutor.domain.prompt.PromptVersion
import org.catrobat.aitutor.ui.theme.AiTutorTheme
import org.catrobat.shared.generated.resources.Res
import org.catrobat.shared.generated.resources.prompt_version_debug
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Debug-only dropdown letting the developer override the prompt template used to build the
 * shareable prompt. Callers are expected to gate this behind [org.catrobat.aitutor.util.isDebug].
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PromptVersionDropdown(
    availablePromptVersions: List<PromptVersion>,
    selectedPromptVersion: PromptVersion,
    onPromptVersionChange: (PromptVersion) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier.fillMaxWidth(),
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
                    focusedContainerColor = AiTutorTheme.colors.secondaryContainer,
                    unfocusedContainerColor = AiTutorTheme.colors.secondaryContainer,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = AiTutorTheme.colors.onSurface,
                    focusedLabelColor = AiTutorTheme.colors.onSurfaceVariant,
                    unfocusedLabelColor = AiTutorTheme.colors.onSurfaceVariant,
                    focusedTextColor = AiTutorTheme.colors.onSurface,
                    unfocusedTextColor = AiTutorTheme.colors.onSurfaceVariant,
                    focusedTrailingIconColor = AiTutorTheme.colors.onSurfaceVariant,
                    unfocusedTrailingIconColor = AiTutorTheme.colors.onSurfaceVariant,
                ),
            shape = RoundedCornerShape(16.dp),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            containerColor = AiTutorTheme.colors.secondaryContainer,
            onDismissRequest = { expanded = false },
        ) {
            availablePromptVersions.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(text = selectionOption.displayName) },
                    onClick = {
                        onPromptVersionChange(selectionOption)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    colors =
                        MenuDefaults.itemColors().copy(
                            textColor = AiTutorTheme.colors.onSurface,
                            disabledTextColor = AiTutorTheme.colors.onSurfaceVariant,
                        ),
                )
            }
        }
    }
}

@Preview
@Composable
private fun PromptVersionDropdownPreview() {
    Surface(color = AiTutorTheme.colors.surface) {
        Box(Modifier.padding(16.dp)) {
            PromptVersionDropdown(
                availablePromptVersions = PromptVersion.entries,
                selectedPromptVersion = PromptVersion.V1,
                onPromptVersionChange = {},
            )
        }
    }
}
