package org.catrobat.aitutor.ui.components.input

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.catrobat.aitutor.ui.theme.AiTutorColors
import org.catrobat.shared.generated.resources.Res
import org.catrobat.shared.generated.resources.type_your_question_here
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * The question input area. When the selected prompt requires a user question, this is an
 * editable text field that grows with its content. Otherwise (e.g. prompts that only need code
 * context) there is nothing to type, so a centered icon is shown instead.
 *
 * The container is transparent in both states so it blends into the dialog's own background
 * instead of standing out as a separate pill.
 */
@Composable
internal fun QuestionInputField(
    requiresUserQuestion: Boolean,
    inputText: String,
    onInputTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (requiresUserQuestion) {
        TextField(
            value = inputText,
            onValueChange = onInputTextChange,
            modifier = modifier.fillMaxWidth(),
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
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = AiTutorColors.primary,
                    focusedTextColor = AiTutorColors.onSurface,
                    unfocusedTextColor = AiTutorColors.onSurfaceVariant,
                ),
            shape = RoundedCornerShape(16.dp),
        )
    } else {
        Box(
            modifier = modifier.fillMaxWidth().height(96.dp),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Default.AutoAwesome,
                contentDescription = null,
                tint = AiTutorColors.primary,
                modifier = Modifier.size(40.dp),
            )
        }
    }
}

@Preview
@Composable
private fun QuestionInputFieldPreview() {
    Surface(color = AiTutorColors.surface) {
        Box(Modifier.padding(16.dp)) {
            QuestionInputField(
                requiresUserQuestion = true,
                inputText = "",
                onInputTextChange = {},
            )
        }
    }
}

@Preview
@Composable
private fun QuestionInputFieldNoQuestionPreview() {
    Surface(color = AiTutorColors.surface) {
        Box(Modifier.padding(16.dp)) {
            QuestionInputField(
                requiresUserQuestion = false,
                inputText = "",
                onInputTextChange = {},
            )
        }
    }
}
