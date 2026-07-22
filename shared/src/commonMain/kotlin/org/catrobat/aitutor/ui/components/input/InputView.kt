package org.catrobat.aitutor.ui.components.input

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import org.catrobat.aitutor.domain.prompt.PromptVersion
import org.catrobat.aitutor.ui.theme.AiTutorColors
import org.catrobat.aitutor.util.isDebug
import org.catrobat.shared.generated.resources.Res
import org.catrobat.shared.generated.resources.about
import org.catrobat.shared.generated.resources.copy
import org.catrobat.shared.generated.resources.help
import org.catrobat.shared.generated.resources.include_code_context
import org.catrobat.shared.generated.resources.include_code_output
import org.catrobat.shared.generated.resources.more
import org.catrobat.shared.generated.resources.paste
import org.jetbrains.compose.resources.stringResource

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
    onHelpRequest: () -> Unit,
    onAboutRequest: () -> Unit,
    onPasteAiAnswerRequest: (() -> Unit)? = null,
    onCopyPromptRequest: (() -> Unit)? = null,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Box(
            modifier = Modifier.fillMaxSize().imePadding().then(modifier),
            contentAlignment = Alignment.BottomCenter,
        ) {
            Column(
                modifier =
                    Modifier
                        .padding(horizontal = 16.dp, vertical = 24.dp)
                        .widthIn(max = 580.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                ContextSwitchCard(
                    text = stringResource(Res.string.include_code_context),
                    checked = isCodeContextIncluded,
                    onCheckedChange = onToggleCodeContext,
                )

                // Output Context (Conditionally present)
                if (isOutputContextIncluded != null) {
                    ContextSwitchCard(
                        text = stringResource(Res.string.include_code_output),
                        checked = isOutputContextIncluded,
                        onCheckedChange = onToggleOutputContext,
                    )
                }

                Surface(
                    color = AiTutorColors.surface.copy(alpha = 0.95f),
                    shape = RoundedCornerShape(24.dp),
                    shadowElevation = 8.dp,
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        InputActionRow(
                            hasClipboardFlow = onPasteAiAnswerRequest != null && onCopyPromptRequest != null,
                            moreLabel = stringResource(Res.string.more),
                            pasteLabel = stringResource(Res.string.paste),
                            copyLabel = stringResource(Res.string.copy),
                            helpLabel = stringResource(Res.string.help),
                            aboutLabel = stringResource(Res.string.about),
                            onPasteClick = { onPasteAiAnswerRequest?.invoke() },
                            onCopyClick = { onCopyPromptRequest?.invoke() },
                            onHelpClick = onHelpRequest,
                            onAboutClick = onAboutRequest,
                        )

                        if (isDebug) {
                            Spacer(Modifier.height(16.dp))
                            PromptVersionDropdown(
                                availablePromptVersions = availablePromptVersions,
                                selectedPromptVersion = selectedPromptVersion,
                                onPromptVersionChange = onPromptVersionChange,
                            )
                        }

                        Spacer(Modifier.height(16.dp))

                        QuestionInputField(
                            requiresUserQuestion = selectedPromptVersion.requiresUserQuestion,
                            inputText = inputText,
                            onInputTextChange = onInputTextChange,
                        )

                        Spacer(Modifier.height(16.dp))

                        InputBottomBar(
                            sendEnabled = !selectedPromptVersion.requiresUserQuestion || inputText.isNotBlank(),
                            onCancel = onDismissRequest,
                            onSend = { onSend(if (selectedPromptVersion.requiresUserQuestion) inputText else "") },
                        )
                    }
                }
            }
        }
    }
}
