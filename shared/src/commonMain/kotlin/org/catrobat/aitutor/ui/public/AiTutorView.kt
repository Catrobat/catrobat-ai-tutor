package org.catrobat.aitutor.ui.public

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import org.catrobat.aitutor.di.AiTutorKoin
import org.catrobat.aitutor.ui.TutorUiStep
import org.catrobat.aitutor.ui.components.AboutScreen
import org.catrobat.aitutor.ui.components.AppChooserView
import org.catrobat.aitutor.ui.components.ClipboardPasteView
import org.catrobat.aitutor.ui.components.InputView
import org.catrobat.aitutor.ui.components.TutorialView
import org.catrobat.aitutor.ui.viewmodel.AiTutorViewModel

private val aiTutorViewModelFactory =
    viewModelFactory {
        initializer<AiTutorViewModel> {
            AiTutorKoin.app.koin.get(AiTutorViewModel::class)
        }
    }

/**
 * The main entry point for displaying the AI Tutor user interface.
 *
 * This composable function manages the entire user interaction flow, from showing a first-run
 * tutorial, to collecting user input, and finally launching an external AI application. It is
 * designed to be overlaid on top of the host application's UI.
 *
 * The UI for including  output context is dynamic. It will only be displayed
 * if the corresponding parameter (`outputContext`) are provided with a
 * non-null value. This allows the host application to conditionally enable this features.
 *
 * Example of simple usage:
 * ```
 * AiTutorView(
 *      show = isTutorVisible,
 *      onDismissRequest = { isTutorVisible = false },
 *      codeContext = "val x = 5"
 * )
 * ```
 *
 * Example of advanced usage with output context, system context, and clipboard paste enabled:
 * ```
 * AiTutorView(
 *      show = isTutorVisible,
 *      onDismissRequest = { isTutorVisible = false },
 *      codeContext = "val x = 5",
 *      outputContext = "Error: Semicolon expected",
 *      systemContext = "Phaser, a Desktop and Mobile HTML5 game framework",
 *      onClipboardPaste = { pastedText ->
 *          // Host app receives the AI's answer from the clipboard,
 *          // e.g. validate and apply the returned XML or code snippet.
 *          applyAiResult(pastedText)
 *      }
 * )
 * ```
 *
 * @param modifier The [Modifier] to be applied to the component.
 * @param show A boolean that controls the visibility of the AI Tutor view. Set to `true` to
 * display it and `false` to hide it.
 * @param onDismissRequest A callback invoked when the user requests to dismiss the view,
 * for example, by tapping the cancel button or pressing back.
 * @param codeContext A string containing the user's code snippet. This is the primary
 * context for the AI's analysis.
 * @param outputContext An optional string containing the actual output produced by the code.
 * If this parameter is provided (i.e., not `null`), a UI option to
 * include the output will be displayed to the user.
 * @param systemContext An optional string for the integrator to provide additional,
 * non-user-visible context, such as the programming language, version, or framework being used.
 * @param onClipboardPaste An optional callback that enables the clipboard paste flow. When
 * provided, the view does **not** dismiss after the user selects an AI app. Instead, it shows a
 * "Paste AI Answer" dialog when the user returns to the host app. Tapping "Paste from clipboard"
 * reads the clipboard and delivers its text to this callback, then dismisses the view. Tapping
 * Cancel dismisses without invoking the callback. When `null` (the default), the view dismisses
 * immediately after the user selects an AI app, preserving the original behavior.
 */
@Composable
fun AiTutorView(
    modifier: Modifier = Modifier,
    show: Boolean,
    onDismissRequest: () -> Unit,
    codeContext: String?,
    outputContext: String? = null,
    systemContext: String? = null,
    onClipboardPaste: ((pastedText: String) -> Unit)? = null,
) {
    val viewModel: AiTutorViewModel = viewModel(factory = aiTutorViewModelFactory)
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(show, outputContext) {
        if (show) {
            viewModel.initializeContexts(
                initialIsOutputContextIncluded = if (outputContext == null) null else true,
            )
        }
    }

    LaunchedEffect(show) {
        viewModel.handleTutorVisibility(show)
    }

    when (uiState.currentStep) {
        TutorUiStep.ShowingTutorial -> {
            TutorialView(
                onDismissRequest = viewModel::dismissTutorial,
            )
        }

        TutorUiStep.AwaitingInput -> {
            InputView(
                modifier = modifier.fillMaxSize(),
                inputText = uiState.userQuestion,
                isCodeContextIncluded = uiState.isCodeContextIncluded,
                onToggleCodeContext = viewModel::onToggleCodeContext,
                isOutputContextIncluded = uiState.isOutputContextIncluded,
                onToggleOutputContext = viewModel::onToggleOutputContext,
                availablePromptVersions = uiState.availablePromptVersions,
                selectedPromptVersion = uiState.selectedPromptVersion,
                onPromptVersionChange = viewModel::onPromptVersionChanged,
                onInputTextChange = viewModel::onUserQuestionChanged,
                onDismissRequest = onDismissRequest,
                onSend = { question ->
                    viewModel.onUserQuestionChanged(question)
                    viewModel.onCurrentStepChanged(TutorUiStep.ChoosingApp)
                },
                onHelpRequest = viewModel::showTutorial,
                onAboutRequest = viewModel::showAboutScreen,
                onPasteAiAnswerRequest =
                    if (onClipboardPaste != null) {
                        { viewModel.onCurrentStepChanged(TutorUiStep.AwaitingPaste) }
                    } else {
                        null
                    },
            )
        }

        TutorUiStep.ChoosingApp -> {
            AppChooserView(
                uiState = uiState,
                onDismissRequest = onDismissRequest,
                onAppSelected = { packageName ->
                    viewModel.launchAiApp(
                        packageName = packageName,
                        codeContext = codeContext,
                        outputContext = outputContext,
                        systemContext = systemContext,
                    )
                    if (onClipboardPaste != null) {
                        viewModel.onCurrentStepChanged(TutorUiStep.AwaitingPaste)
                    } else {
                        onDismissRequest()
                    }
                },
            )
        }

        TutorUiStep.AwaitingPaste -> {
            ClipboardPasteView(
                onPasteResult = { text ->
                    onClipboardPaste?.invoke(text)
                    viewModel.resetPasteStep()
                    onDismissRequest()
                },
                onDismissRequest = {
                    viewModel.resetPasteStep()
                    onDismissRequest()
                },
            )
        }

        TutorUiStep.Hidden -> {}

        TutorUiStep.ShowingAbout -> {
            AboutScreen(
                onDismissRequest = viewModel::dismissAboutScreen,
            )
        }
    }
}
