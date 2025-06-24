package org.catrobat.aitutor.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.catrobat.aitutor.ui.viewmodel.AiTutorViewModel
import org.koin.compose.koinInject

@Composable
fun AiTutorView(
    modifier: Modifier = Modifier,
    show: Boolean,
    onDismissRequest: () -> Unit,
    codeContext: String?,
) {
    val viewModel: AiTutorViewModel = koinInject()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(show) {
        viewModel.onCurrentStepChanged(if (show) TutorUiStep.AwaitingInput else TutorUiStep.Hidden)
        if (!show) {
            viewModel.onUserQuestionChanged("")
        }
    }

    if (uiState.currentStep == TutorUiStep.AwaitingInput) {
        InputView(
            modifier = modifier,
            inputText = uiState.userQuestion,
            isCodeContextIncluded = uiState.isCodeContextIncluded,
            onInputTextChange = viewModel::onUserQuestionChanged,
            onisCodeContextIncludedChange = viewModel::onToggleCodeContext,
            onDismissRequest = onDismissRequest,
            onSend = { question ->
                viewModel.onUserQuestionChanged(question)
                viewModel.onCurrentStepChanged(TutorUiStep.ChoosingApp)
            },
        )
    }

    if (uiState.currentStep == TutorUiStep.ChoosingApp) {
        AppChooserView(
            uiState = uiState,
            onDismissRequest = onDismissRequest,
            onAppSelected = { packageName ->
                viewModel.launchAiApp(packageName, codeContext)
                onDismissRequest()
            },
        )
    }
}
